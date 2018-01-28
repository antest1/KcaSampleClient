package com.antest1.kcasampleclient;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

import static com.antest1.kcasampleclient.KcaConstants.CONTENT_URI;
import static com.antest1.kcasampleclient.KcaUtils.getKcIntent;

public class MainActivity extends AppCompatActivity {
    ToggleButton btn_receiver;
    Button btn_launch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.content_response)).setMovementMethod(new ScrollingMovementMethod());

        btn_receiver = (ToggleButton) findViewById(R.id.btn_receiver);
        btn_receiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent service_intent = new Intent(getApplicationContext(), KcaClientService.class);
                if (b) {
                    startService(service_intent);
                } else {
                    stopService(service_intent);
                }
            }
        });

        btn_launch = (Button) findViewById(R.id.btn_launch);
        btn_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kcIntent = getKcIntent(getApplicationContext());
                if (kcIntent != null) {
                    startActivity(kcIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Kancolle not installed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContext();
    }

    public void setContext() {
        JsonObject data = new JsonObject();
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null, null);
        if(cursor != null) {
            // Toast.makeText(getApplicationContext(), String.format(Locale.US, "%d %d", cursor.getColumnCount(), cursor.getCount()), Toast.LENGTH_LONG).show();
            if (cursor.moveToFirst()) {
                // Convert to JsonObject data
                data.addProperty("url", cursor.getString(cursor.getColumnIndex("URL")));
                data.addProperty("request", cursor.getString(cursor.getColumnIndex("REQUEST")));
                data.addProperty("response", cursor.getString(cursor.getColumnIndex("RESPONSE")));
                data.addProperty("timestamp", cursor.getLong(cursor.getColumnIndex("TIMESTAMP")));

                // Display
                ((TextView) findViewById(R.id.content_ts)).setText(data.get("timestamp").getAsString());
                ((TextView) findViewById(R.id.content_url)).setText(data.get("url").getAsString());
                String request = data.get("request").getAsString();
                try {
                    request = URLDecoder.decode(request, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ((TextView) findViewById(R.id.content_request)).setText(request);
                String response = data.get("response").getAsString();
                ((TextView) findViewById(R.id.label_response)).setText(String.format(Locale.US, "Response (%d)", response.length()));
                ((TextView) findViewById(R.id.content_response)).setText(response);
            }
            cursor.close();
        } else {
            ((TextView) findViewById(R.id.content_ts)).setText("");
            ((TextView) findViewById(R.id.content_url)).setText("No Data");
            ((TextView) findViewById(R.id.content_request)).setText("");
            ((TextView) findViewById(R.id.label_response)).setText("Response");
            ((TextView) findViewById(R.id.content_response)).setText("");
        }
    }
}
