package g4it.finalproject.mobilebanking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.telephony.IExtendedNetworkService;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;


public class CheckBalance extends AppCompatActivity {

    static Receiver receiver=null;
    static Boolean recieverActive=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Powered by: G4 IT students", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final Button btnCheck=(Button)findViewById(R.id.btnCheckBalance);
        btnCheck.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                  checkBalance(LoginActivity.account);
                        btnCheck.setText("Re-Check");
                    }
                }
        );
        ((Button)findViewById(R.id.btnBack)).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent=new Intent(CheckBalance.this,USSDMenu.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        receiver=new Receiver();
    }
    public void checkBalance(String account){
        final TextView view=(TextView)findViewById(R.id.labelCheckResult);
        HashMap data=new HashMap();
        data.put("account_no",account);
        data.put("request","balance");
        AsyncResponse response=new AsyncResponse() {
            @Override
            public void processFinish(String s) {
              if(s==null || s.isEmpty()){
                   Toast.makeText(CheckBalance.this,"Connection Failed",Toast.LENGTH_LONG).show();

                }
                else if(s.contains("error")){
                  Toast.makeText(CheckBalance.this,s,Toast.LENGTH_LONG).show();
              }
              else if(s.contains("balance")){
                  String[] result=s.split("=");
                  double amount=Double.parseDouble(result[1]);

                  view.setText("Your current balance is: "+amount);

              }
              else view.setText("Your balance couldn't be retrieved please check internet conectivity, \n"+s);
            }
        };
        PostResponseAsyncTask task=new PostResponseAsyncTask(this,data,response);
        task.execute(USSD.SERVER+"/account.php");
    }
    @Override
    public void onResume(){
        super.onResume();
        if(!recieverActive){
            registerReceiver(receiver,new IntentFilter("g4it.finalproject.mobilebanking.message"));
            recieverActive=true;
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if(recieverActive){
            unregisterReceiver(receiver);
            recieverActive=false;
        }
    }
    class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
             Log.d("MESSAGE","Recieved something");
            ((TextView)findViewById(R.id.labelCheckResult)).setText(intent.getExtras().getString("message"));

        }
    }

}
