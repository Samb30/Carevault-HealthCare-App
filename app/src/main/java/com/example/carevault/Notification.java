package com.example.carevault;

import android.app.AlarmManager;
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
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Notification extends BroadcastReceiver {
    public static final int notificationID = 1;
    public static final String channelID = "channel1";
    public static final String titleExtra = "titleExtra";
    public static final String silentExtra = "silentExtra";
    public static final String messageExtra = "messageExtra";
    public static final String musicExtra="musicExtra";
    public static final String repeatExtra="repeatExtra";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
        Log.d("heeloo",datePick());
        Intent intent3 = new Intent(context, MainFragment.class); // Replace Activity3 with the actual name of your Activity
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int s=intent.getIntExtra(String.valueOf(notificationID), -1);
        String flag=intent.getStringExtra(silentExtra);
        String title = intent.getStringExtra(titleExtra);
        String message = intent.getStringExtra(messageExtra);
        String music=intent.getStringExtra(musicExtra).toLowerCase();
        String repeat=intent.getStringExtra(repeatExtra);
        intent3.putExtra(titleExtra, title);
        intent3.putExtra(messageExtra, message);
        PendingIntent pi = PendingIntent.getActivity(context, s, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        if(flag.equals("true")){
            Log.d("heeloo",datePick());
//            Uri customSoundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.correct);
//            Uri sound ;
//
//            if(music.equals("morningdew")){
//                MediaPlayer player=MediaPlayer.create(context,R.raw.morningdew);
//                player.start();
//                solve(player);
//            }else if(music.equals("forest")){
//                MediaPlayer player=MediaPlayer.create(context,R.raw.forest);
//                player.start();
//                solve(player);
//            }else if(music.equals("rain")){
//                MediaPlayer player=MediaPlayer.create(context,R.raw.rain);
//                player.start();
//                solve(player);
//            }else if(music.equals("fireflies")){
//                MediaPlayer player=MediaPlayer.create(context,R.raw.fireflies);
//                player.start();
//                solve(player);
//            }else {
//                MediaPlayer player=MediaPlayer.create(context,R.raw.weather);
//                player.start();
//                solve(player);
//            }
            // Save the start time
            Toast.makeText(context, ""+music, Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(intent.getStringExtra(titleExtra))
                    .setContentText(intent.getStringExtra(messageExtra))
                    .setContentIntent(pi)
                    .setAutoCancel(true);

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
                    .setSilent(true) // Add stop button
                    .setAutoCancel(true);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.notify(notificationID, builder.build());
            }
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
