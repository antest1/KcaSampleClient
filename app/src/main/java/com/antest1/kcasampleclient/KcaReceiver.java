package com.antest1.kcasampleclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

import static com.antest1.kcasampleclient.KcaConstants.BROADCAST_ACTION;
import static com.antest1.kcasampleclient.KcaConstants.CONTENT_URI;

public class KcaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) return;

        if (action.equals(BROADCAST_ACTION)) {
            JsonObject data = new JsonObject();
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null, null);
            if(cursor != null) {
                if (cursor.moveToFirst()) {
                    // Convert to JsonObject data
                    data.addProperty("url", cursor.getString(cursor.getColumnIndex("URL")));
                    data.addProperty("request", cursor.getString(cursor.getColumnIndex("REQUEST")));
                    data.addProperty("response", cursor.getString(cursor.getColumnIndex("RESPONSE")));
                    data.addProperty("timestamp", cursor.getLong(cursor.getColumnIndex("TIMESTAMP")));
                    long timestamp = data.get("timestamp").getAsLong();
                    String request = data.get("request").getAsString();
                    String response = data.get("response").getAsString();
                    Toast.makeText(context, String.format(Locale.US, "received %d %d %d",
                            timestamp, request.length(), response.length()), Toast.LENGTH_LONG).show();
                }
                cursor.close();
            } else {
                Toast.makeText(context, "cursor is null", Toast.LENGTH_LONG).show();
            }
        }
    }
}
