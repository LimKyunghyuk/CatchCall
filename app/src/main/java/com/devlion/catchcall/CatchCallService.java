package com.devlion.catchcall;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CatchCallService extends Service  {

    public static final String EXTRA_CALL_NUMBER = "CALL_NUMBER";
    protected View rootView;

    TextView tvCallNumber;
    Button btnClose;
    String callNumber;

    WindowManager.LayoutParams params;
    private WindowManager windowManager;


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("CALL_TEST", "CallService - onCreate()");
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 90%


        params = new WindowManager.LayoutParams(
                width,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                PixelFormat.TRANSLUCENT);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        rootView = layoutInflater.inflate(R.layout.popup, null);

        tvCallNumber = (TextView) rootView.findViewById(R.id.tv_call_number);
        btnClose = (Button) rootView.findViewById(R.id.btn_close);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePopup();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        windowManager.addView(rootView, params);
        setExtra(intent);


        if (!TextUtils.isEmpty(callNumber)) {
            tvCallNumber.setText(callNumber);
        }


        return START_REDELIVER_INTENT;
    }


    private void setExtra(Intent intent) {

        if (intent == null) {
            removePopup();
            return;
        }

        callNumber = intent.getStringExtra(EXTRA_CALL_NUMBER);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        removePopup();
    }

    public void removePopup() {
        if (rootView != null && windowManager != null) windowManager.removeView(rootView);
    }
}