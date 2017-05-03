package g4it.finalproject.mobilebanking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class FundWithdraw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_withdraw);
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
                    public void onClick(View v){
                        Intent intent=new Intent(FundWithdraw.this,USSDMenu.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

}
