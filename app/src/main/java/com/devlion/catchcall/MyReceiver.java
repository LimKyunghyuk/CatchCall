package com.devlion.catchcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private static String mLastState;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(mLastState)) {
            return;
        } else {
            mLastState = state;
        }

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            Log.d("CALL_TEST", "incomingNumber>" + incomingNumber);

            final String phone_number = PhoneNumberUtils.formatNumber(incomingNumber);

            Log.d("CALL_TEST", "phone_number>" + incomingNumber);
            Intent serviceIntent = new Intent(context, CatchCallService.class);
            serviceIntent.putExtra(CatchCallService.EXTRA_CALL_NUMBER, phone_number);
            context.startService(serviceIntent);
        }
    }
}