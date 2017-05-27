package g4it.finalproject.mobilebanking;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;


public class FundTransfer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);
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
        Button btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        Intent intent=new Intent(FundTransfer.this,USSDMenu.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        Button btnTransfer=(Button)findViewById(R.id.btnTransfer);
        final EditText etToAccount=(EditText)findViewById(R.id.txtToAccount);
        final EditText etAmount=(EditText)findViewById(R.id.txtAmount);
        btnTransfer.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        AlertDialog.Builder dialog=new AlertDialog.Builder(FundTransfer.this);
                        dialog.setTitle("Confirm Transfer");

                       final String account=etToAccount.getText().toString();
                       final String amount=etAmount.getText().toString();
                        dialog.setMessage("Birr "+amount+" will be transfered to account "+account+"\nAre you sure to continue?");
                        dialog.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        transfer(LoginActivity.account,account,amount);
                                    }
                                }
                        );
                        dialog.setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(FundTransfer.this,"Transfer Canceled by the user",Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                        dialog.show();
                    }
                }
        );
    }
    public void transfer(String fromAccount, final String toAccount, final String amount){
        final NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentTitle("Balance Transfer Notification");
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        //long[] pattern={1,0};
        //builder.setVibrate(pattern);
        final NotificationManager manager=(NotificationManager)getSystemService(this.NOTIFICATION_SERVICE);
        HashMap map=new HashMap();
        map.put("request","transfer");
        map.put("fromAccount",fromAccount);
        map.put("toAccount",toAccount);
        map.put("amount",amount);
        AsyncResponse response=new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if(s==null || s.isEmpty()){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(FundTransfer.this);
                    dialog.setTitle("Transfer Failed");
                    dialog.setMessage("Transfer failed, check your internet connectivity");
                    dialog.show();
                }
                else if(s.contains("error")){
                    Log.d("APP","Returned: "+s);
                    Toast.makeText(FundTransfer.this,s,Toast.LENGTH_LONG).show();
                }
                else if(s.contains("true")){
                    String[] main=s.split(" ");
                    String[] detail=main[1].split(",");

                    AlertDialog.Builder build=new AlertDialog.Builder(FundTransfer.this);
                    build.setTitle("Transfer Succesfull");
                    build.setMessage("You have succesfully transferred birr "+amount+" to "+detail[0]+" "+detail[1]);
                    build.show();
                    builder.setContentText("You have tranfered birr "+amount+" to "+detail[0]+" "+detail[1]+" "+detail[2]);

                }
                else if(s.contains("false")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(FundTransfer.this);
                    dialog.setTitle("Transfer Failed");
                    dialog.setMessage("Transfer failed, you don't have enough balance to transfer birr "+amount+"\nPlease Recharge your account");

                    dialog.show();
                    builder.setContentText("Transfer failed, you don't have enough balance to transfer birr "+amount+"\nPlease Recharge your account");
                    Toast.makeText(FundTransfer.this,s,Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(FundTransfer.this,s,Toast.LENGTH_LONG).show();
                manager.notify(101,builder.build());
            }
        };
        PostResponseAsyncTask task=new PostResponseAsyncTask(this,map,response);
        task.execute(USSD.SERVER+"/account.php");


    }
}
