package g4it.finalproject.mobilebanking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class USSDMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ussdmenu);
        Button btnNext=(Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        RadioGroup gr=(RadioGroup)findViewById(R.id.mainMenu);
                        int id=gr.getCheckedRadioButtonId();
                        if(id==R.id.optionCheckBalance){
                            Intent intent=new Intent(USSDMenu.this,CheckBalance.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(id==R.id.optionDeposite){
                            Intent intent=new Intent(USSDMenu.this,FundDeposite.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(id==R.id.optionWithdraw){
                            Intent intent=new Intent(USSDMenu.this,FundWithdraw.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(id==R.id.optionTransfer){
                            Intent intent=new Intent(USSDMenu.this,FundTransfer.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(id==R.id.optionChangePin){
                            Intent intent=new Intent(USSDMenu.this,ChangePin.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(USSDMenu.this,"Select an Option first",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,MenuActivity.class));
        finish();
    }
}
