package g4it.finalproject.mobilebanking;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class ChangePin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
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
        final Button btnChanage=(Button)findViewById(R.id.btnChanage);
        final EditText txtOldPin=(EditText)findViewById(R.id.txtCurrentPin);
        final EditText txtNewPin=(EditText)findViewById(R.id.txtNewPin);
        final EditText txtConfirm=(EditText)findViewById(R.id.txtConfirmPin);
        btnChanage.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        String oldPin=txtOldPin.getText().toString();
                        String newPin=txtNewPin.getText().toString();
                        String confirmedPin=txtConfirm.getText().toString();
                        if(!confirmedPin.equals(newPin)){
                            Toast.makeText(ChangePin.this,"New Pin & Confirm Pin do not match!", Toast.LENGTH_LONG).show();

                        }
                        else {
                            changePassword(LoginActivity.account,oldPin,newPin);
                        }
                    }
                }
        );
        Button btnCancel=(Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent=new Intent(ChangePin.this,USSDMenu.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
 public void changePassword(String account,String password,String newPassword){
     HashMap map=new HashMap();
     map.put("account", LoginActivity.account);
     map.put("password",password);
     map.put("newpassword",newPassword);
     map.put("request","changepassword");
     //You can customize and change your notification from here

     NotificationManager manage=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
     //this notification manager is used to show & close notifications based on their id
     final NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
     //builder is the actual notification
     AsyncResponse response=new AsyncResponse() {
         @Override
         public void processFinish(String s) {
             if(s==null || s.isEmpty()){
                 AlertDialog.Builder dialog=new AlertDialog.Builder(ChangePin.this);
                 dialog.setTitle("Error");
                 dialog.setMessage("Error: Failed to change password, make sure that you're connected to internet");
                 dialog.show();
             }
             else if(s.contains("error")){
                 Toast.makeText(ChangePin.this,"Error: "+s,Toast.LENGTH_LONG).show();
             }
             else if(s.contains("false")){
                 Toast.makeText(ChangePin.this,"Wrong old Password",Toast.LENGTH_LONG).show();
             }
             else if(s.contains("true")){
                 AlertDialog.Builder dialog=new AlertDialog.Builder(ChangePin.this);
                 dialog.setTitle("Success");
                 dialog.setMessage("You have sucsesfully changed your password");
                 dialog.show();
                 builder.setContentTitle("Password Changed");
                 builder.setContentText("Your password have been changed moments ago");
                 builder.setSmallIcon(R.mipmap.ic_launcher);
             }
         }
     };
     PostResponseAsyncTask task=new PostResponseAsyncTask(this,map,response);
     task.execute(USSD.SERVER+"/account.php");
     manage.notify(101,builder.build());
     //End of notification builder class
 }

}
