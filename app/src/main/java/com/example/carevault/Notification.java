package com.example.carevault;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class Notification extends BroadcastReceiver {
    public static final int notificationID = 1;
    public static final String channelID = "channel1";
    public static final String titleExtra = "titleExtra";
    public static final String messageExtra = "messageExtra";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent3 = new Intent(context, content.class); // Replace Activity3 with the actual name of your Activity
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int s=intent.getIntExtra(String.valueOf(notificationID), -1);
        boolean flag=intent.getBooleanExtra("check",true);
        String title = intent.getStringExtra(titleExtra);
        String message = intent.getStringExtra(messageExtra);
        intent3.putExtra(titleExtra, title);
        intent3.putExtra(messageExtra, message);
        PendingIntent pi = PendingIntent.getActivity(context, s, intent3, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        if(flag==true){
            Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(intent.getStringExtra(titleExtra))
                    .setContentText(intent.getStringExtra(messageExtra))
                    .setContentIntent(pi)
                    .setSound(null)
                    .setAutoCancel(true)
                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.notify(notificationID, builder.build());
            }
        }else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(intent.getStringExtra(titleExtra))
                    .setContentText(intent.getStringExtra(messageExtra))
                    .setContentIntent(pi)
                    .setSound(null)
                    .setAutoCancel(true);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.notify(notificationID, builder.build());
            }
        }
    }
}
