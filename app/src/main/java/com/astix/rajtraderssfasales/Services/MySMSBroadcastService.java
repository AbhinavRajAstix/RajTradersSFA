package com.astix.rajtraderssfasales.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;


public class MySMSBroadcastService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String sms = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String phoneNumber = smsMessage.getDisplayOriginatingAddress();
                String mesg =smsMessage.getMessageBody();

                if (phoneNumber.contains("AXTSFA"))  //REDIWA //PEPSIC
                {

                    sms = mesg.toString();

                    Log.i("SmsReceiver", "sendernum" + phoneNumber + "message" + mesg);

                    Intent smsIntent = new Intent("otp");
                    smsIntent.putExtra("message", sms);

                    LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
                }
            }
        } else {
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String phoneNumber = messages[i].getDisplayOriginatingAddress();
                    String mesg = messages[i].getDisplayMessageBody();

                    if (phoneNumber.contains("AXTSFA"))  //REDIWA //PEPSIC
                    {

                        sms = mesg.toString();

                        Log.i("SmsReceiver", "sendernum" + phoneNumber + "message" + mesg);

                        Intent smsIntent = new Intent("otp");
                        smsIntent.putExtra("message", sms);

                        LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
                    }

                }
            }
        }
    }
}
