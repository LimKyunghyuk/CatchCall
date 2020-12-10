package com.devlion.catchcall;

import android.content.ContentValues;
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

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_layer = (Button)findViewById(R.id.bt_layer);

        bt_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stopService(new Intent(MainActivity.this, MyService.class));
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                checkPermission();
            }
        });

        Button bt_sqlite_save = (Button)findViewById(R.id.bt_sqlite_save);

        bt_sqlite_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stopService(new Intent(MainActivity.this, MyService.class));
//                Toast.makeText(MainActivity.this, "SAVE", Toast.LENGTH_SHORT).show();
                Log.d("CALL_TEST", "SAVE!");
                DbManager db = DbManager.getInstance(getApplicationContext());

                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME", "홍길동");
                contentValues.put("PHONE", "1234");

                db.insert(contentValues);
            }
        });

        Button bt_sqlite_load = (Button)findViewById(R.id.bt_sqlite_load);

        bt_sqlite_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CALL_TEST", "LOAD");
//                stopService(new Intent(MainActivity.this, MyService.class));
                DbManager db = DbManager.getInstance(getApplicationContext());

                List<Map<String, String>> mapList = db.select();

                for(Map<String, String> map : mapList){
                    Log.d("CALL_TEST", map.toString());
                }
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


    public void sqliteTest() {

        Toast.makeText(MainActivity.this, "START", Toast.LENGTH_SHORT).show();

        long maxCnt = 200000;
        for(int i = 0 ; i < maxCnt ; i++ ){
            Log.d("CALL_TEST", ".");
        }


        Toast.makeText(MainActivity.this, "FINISH", Toast.LENGTH_SHORT).show();

    }

}