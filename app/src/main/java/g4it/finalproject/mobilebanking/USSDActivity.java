package g4it.finalproject.mobilebanking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.net.Uri;
import android.content.Intent;
public class USSDActivity extends AppCompatActivity {

    EditText etUSSD;
    Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ussd);
        etUSSD=(EditText)findViewById(R.id.etUSSD);
        btnProceed=(Button)findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ussd=etUSSD.getText().toString();
                        callUSSD(ussd);
                    }
                }
        );
    }
    public void callUSSD(String number){
        Uri uri=USSD.ussdToCallableUri(number);
        Intent intent=new Intent(Intent.ACTION_CALL,uri);
        startActivity(intent);

    }
}
