package com.astix.rajtraderssfasales.FirebaseNotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;


import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.IntentConstants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirebaseMessageReceiver
        extends FirebaseMessagingService {
    private static final String TAG ="Firebase Token Number" ;
    AppDataSource helperDb;

    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        token= FirebaseInstanceId.getInstance().getToken();
        Intent intent = new Intent(this, NewTokenService.class);
        intent.putExtra(IntentConstants.FCM_TOKEN, token);
        startService(intent);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        helperDb= AppDataSource.getInstance(this);
        if (remoteMessage.getData() != null) {
            // Since the notification is received directly from
            // FCM, the title and the body can be fetched
            // directly as below.
            if (remoteMessage.getData().containsKey("message") && remoteMessage.getData().containsKey("SendTime") && remoteMessage.getData().containsKey("SenderFrom") && remoteMessage.getData().containsKey("MsgID")) {
                showNotification("Surya SFA",remoteMessage.getData().get("message"));

                String str= TextUtils.htmlEncode(remoteMessage.getData().get("message"));
                String strMsgSendTime=remoteMessage.getData().get("SendTime").toString();
                String  imei = AppUtils.getToken(this);
                long syncTIMESTAMP = System.currentTimeMillis();
                Date dateobj = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat(
                        "dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                String Noti_ReadDateTime = df.format(dateobj);
                String MsgSendingTime=strMsgSendTime;

                String MsgFrom=remoteMessage.getData().get("SenderFrom").toString();
                String NotificationMessage=TextUtils.htmlEncode(str);

                int MsgServerID=Integer.parseInt(remoteMessage.getData().get("MsgID").toString());


                int SerialNo=helperDb.countNoRowIntblNotificationMstr();
                if(SerialNo>=10)
                {
                    helperDb.deletetblNotificationMstrOneRow(1);
                }
                else
                {
                    SerialNo=SerialNo+1;
                }

                int DuplicateMsgServerID=helperDb.checkMessageIDExistOrNotForNotification(MsgServerID);

                if(DuplicateMsgServerID==0)
                {
                    helperDb.inserttblNotificationMstr(SerialNo,imei,NotificationMessage,MsgSendingTime,1,1,
                            Noti_ReadDateTime,0,MsgServerID);
                }
            }
        }
    }

    // Method to get the custom Design for the display of
    // notification.
    private RemoteViews getCustomDesign(String title,
                                        String message) {
        RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon,
                R.drawable.ic_launcher);
        return remoteViews;
    }

    // Method to display the notifications
    public void showNotification(String title,
                                 String message) {
        // Pass the intent to switch to the MainActivity
        Intent intent
                = new Intent(this, NotificationActivity.class);/**/

        // PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,new Intent(), PendingIntent.FLAG_ONE_SHOT);
        // Assign channel ID
        String channel_id = "notification_channel";
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentText("Surya Notification Received")
                .setContentIntent(pendingIntent);

        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        builder.setDefaults(defaults);
        builder.setGroupSummary(true);
        // A customized design for the notification can be
        // set only for Android versions 4.1 and above. Thus
        // condition for the same is checked here.
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.JELLY_BEAN) {
            builder = builder.setContent(
                    getCustomDesign(title, message));
        } // If Android Version is lower than Jelly Beans,
        // customized layout cannot be used and thus the
        // layout is set as follows
        else {
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher);
        }
        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(message);
        builder.setStyle(bigTextStyle);
        notificationManager.notify(0, builder.build());
    }
}