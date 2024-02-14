package com.example.carevault.Alarms;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.carevault.MainFragment;
import com.example.carevault.R;
import com.google.android.gms.common.internal.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Notification extends BroadcastReceiver {
    private static final String default_notification_channel_id = "default_channel_id";
    private static final String NOTIFICATION_CHANNEL_ID = "your_channel_id";

    public static final int notificationID = 1;
    public static final String channelID = "channel1";
    public static final String titleExtra = "titleExtra";
    public static final String silentExtra = "silentExtra";
    public static final String messageExtra = "messageExtra";
    public static final String musicExtra="musicExtra";
    public static final String repeatExtra="repeatExtra";
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
           // Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
            Log.d("heeloo", datePick());
            Intent intent3 = new Intent(context, MainFragment.class); // Replace Activity3 with the actual name of your Activity
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int s = intent.getIntExtra(String.valueOf(notificationID), -1);
            String flag = intent.getStringExtra(silentExtra);
            String title = intent.getStringExtra(titleExtra);
            String message = intent.getStringExtra(messageExtra);
            String music = intent.getStringExtra(musicExtra).toLowerCase();
            String repeat = intent.getStringExtra(repeatExtra);
            intent3.putExtra(titleExtra, title);
            intent3.putExtra(messageExtra, message);
//            PendingIntent pi = PendingIntent.getActivity(context, s, intent,
//                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            if (flag.equals("false")) {
                Log.d("heeloo", datePick());
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ambulance__1_)
                                .setContentTitle("Reminder")
                                .setContentText(intent.getStringExtra(titleExtra));

                Intent notificationIntent = new Intent(context, MainFragment.class);

//                    PendingIntent contentIntent = PendingIntent.getActivity(context, s, notificationIntent,
//                            PendingIntent.FLAG_UPDATE_CURRENT);

                //builder.setContentIntent(contentIntent);

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                    Toast.makeText(context, "hello14", Toast.LENGTH_SHORT).show();
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
                }
                manager.notify(0, builder.build());
                // Save the start time
//                Toast.makeText(context, "" + music, Toast.LENGTH_SHORT).show();
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
//                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle(intent.getStringExtra(titleExtra))
//                        .setContentText(intent.getStringExtra(messageExtra))
//                        //.setContentIntent(pi)
//                        .setAutoCancel(true);
//
//                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//                if (manager != null) {
//                    manager.notify(notificationID, builder.build());
//                }
            } else {

                //Toast.makeText(context, "hello12", Toast.LENGTH_SHORT).show();
                try {
                                Uri customSoundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.correct);
            Uri sound ;

            if(music.equals("morningdew")){
                MediaPlayer player=MediaPlayer.create(context,R.raw.morningdew);
                player.start();
                solve(player);
            }else if(music.equals("forest")){
                MediaPlayer player=MediaPlayer.create(context,R.raw.forest);
                player.start();
                solve(player);
            }else if(music.equals("rain")){
                MediaPlayer player=MediaPlayer.create(context,R.raw.rain);
                player.start();
                solve(player);
            }else if(music.equals("fireflies")){
                MediaPlayer player=MediaPlayer.create(context,R.raw.fireflies);
                player.start();
                solve(player);
            }else {
                MediaPlayer player=MediaPlayer.create(context,R.raw.weather);
                player.start();
                solve(player);
            }
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.ambulance__1_)
                                    .setContentTitle("Reminder")
                                    .setContentText(intent.getStringExtra(titleExtra));

                    Intent notificationIntent = new Intent(context, MainFragment.class);

                    //Toast.makeText(context, "hello1", Toast.LENGTH_SHORT).show();
//                    PendingIntent contentIntent = PendingIntent.getActivity(context, s, notificationIntent,
//                            PendingIntent.FLAG_UPDATE_CURRENT);

                    //Toast.makeText(context, "hello2", Toast.LENGTH_SHORT).show();
                    //builder.setContentIntent(contentIntent);
                    //Toast.makeText(context, "hello3", Toast.LENGTH_SHORT).show();

                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    //Toast.makeText(context, "hello4", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                        Toast.makeText(context, "hello14", Toast.LENGTH_SHORT).show();
                        String channelId = "Your_channel_id";
                        NotificationChannel channel = new NotificationChannel(
                                channelId,
                                "Channel human readable title",
                                NotificationManager.IMPORTANCE_HIGH);
                        manager.createNotificationChannel(channel);
                        builder.setChannelId(channelId);
                    }
                    manager.notify(0, builder.build());
                    //Toast.makeText(context, "hello14", Toast.LENGTH_SHORT).show();
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
//                            .setSmallIcon(R.drawable.ic_launcher_foreground)
//                            .setContentTitle("hello")
//                            .setContentText("hii")
//                            .setContentIntent(pi)
////                        .setSound(null)
////                        .setSilent(true) // Add stop button
//                            .setAutoCancel(true);
//
//                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                    if (manager != null) {
//                        manager.notify(notificationID, builder.build());
//                    }
                }catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
    void solve(MediaPlayer player){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (player.isPlaying()) {
                    player.stop();
                }
                player.release(); // Release resources
            }
        }, 10000);
    }
    String datePick(){
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        }

        // Format the date to a string
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDate = currentDate.format(formatter);
        }

        return formattedDate;
    }
}
