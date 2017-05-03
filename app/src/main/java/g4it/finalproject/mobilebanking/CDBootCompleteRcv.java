package g4it.finalproject.mobilebanking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CDBootCompleteRcv extends BroadcastReceiver {
    private String TAG = CDBootCompleteRcv.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "rcvd boot event, launching service");
        Intent srvIntent = new Intent(context, USSDNetworkService.class);
        context.startService(srvIntent);
    }
}