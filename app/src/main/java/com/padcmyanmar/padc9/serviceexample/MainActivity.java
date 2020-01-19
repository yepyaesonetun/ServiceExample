package com.padcmyanmar.padc9.serviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);

                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this, "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download Done");
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download failed");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                DownloadService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void onClick(View view) {

        Intent intent = new Intent(this, DownloadService.class);

        intent.putExtra(DownloadService.FILENAME, "index.html");
        intent.putExtra(DownloadService.URL,
                "https://lh3.googleusercontent.com/_-qnPlgSjXe3svBP22bA6Rqb2kh6ZlrN_BH44HkZqJdSqmXRlLQfJ2kPGSwVuMKG7lwUEN30DQheykT2KvXmMsVLPIbieidWZ6PmQqw=rw-w600-v1");
        startService(intent);
        textView.setText("Service started");
    }
}
