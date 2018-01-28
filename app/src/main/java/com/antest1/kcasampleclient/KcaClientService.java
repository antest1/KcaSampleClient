package com.antest1.kcasampleclient;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import static com.antest1.kcasampleclient.KcaConstants.BROADCAST_ACTION;

public class KcaClientService extends Service {
    private static BroadcastReceiver receiver;

    @Override
    public void onCreate() {
        registerReceiver();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        receiver = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerReceiver() {
        receiver = new KcaReceiver();
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, filter);
    }
}
