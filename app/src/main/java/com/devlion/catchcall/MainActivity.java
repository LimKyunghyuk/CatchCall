package com.devlion.catchcall;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_stop = (Button)findViewById(R.id.bt);

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stopService(new Intent(MainActivity.this, MyService.class));
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                checkPermission();
            }
        });
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {              // 체크
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            } else {
//                startService(new Intent(MainActivity.this, MyService.class));
                Log.d("CALL_TEST", "#1");
            }
        } else {
            Log.d("CALL_TEST", "#2");
//            startService(new Intent(MainActivity.this, MyService.class));
        }
    }
}