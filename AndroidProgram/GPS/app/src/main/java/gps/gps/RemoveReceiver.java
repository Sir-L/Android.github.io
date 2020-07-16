package gps.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class RemoveReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
        if (isEnter) {
            Toast.makeText(context, "你已到达目的地附近", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "你已离开目的地附近", Toast.LENGTH_LONG).show();
        }
    }
}
