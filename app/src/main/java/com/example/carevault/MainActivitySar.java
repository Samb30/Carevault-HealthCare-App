package com.example.carevault;

import static com.example.carevault.Notification.channelID;
import static com.example.carevault.Notification.messageExtra;
import static com.example.carevault.Notification.notificationID;
import static com.example.carevault.Notification.titleExtra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivitySar extends AppCompatActivity {
    Button submitButton;
    DatePicker datePicker;
    TimePicker timePicker;

    private FirebaseFirestore db;
    TextInputEditText titleET,messageET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsar);
        createNotificationChannel();
        db = FirebaseFirestore.getInstance();
        submitButton=findViewById(R.id.submitButton);
        datePicker=findViewById(R.id.datePicker);
        timePicker=findViewById(R.id.timePicker);
        titleET=findViewById(R.id.titleET);
        messageET=findViewById(R.id.messageET);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification();
            }
        });
    }
    private void scheduleNotification() {
        Intent intent = new Intent(getApplicationContext(), Notification.class);
        String title =titleET.getText().toString();
        String message =messageET.getText().toString();
        long time = getTime();
        Note note=new Note();
        note.setTitle(title);
        note.setContent(Long.toString(time));
        //savenotetoFirebase(note);
        intent.putExtra(titleExtra, title);
        intent.putExtra(messageExtra, message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        //savenotetoFirebase(note);
        showAlert(time,title,message);
    }
    private void savenotetoFirebase(Note note) {
        CollectionReference dbCourses = db.collection("Courses");
        dbCourses.add(note).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivitySar.this, "Note added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivitySar.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showAlert(long time, String title, String message) {
        Date date = new Date(time);
        DateFormat dateFormat =
                android.text.format.DateFormat.getLongDateFormat(getApplicationContext());
        DateFormat timeFormat =
                android.text.format.DateFormat.getTimeFormat(getApplicationContext());

        new AlertDialog.Builder(this)
                .setTitle("Notification Scheduled")
                .setMessage(
                        "Title: " + title +
                                "\nMessage: " + message +
                                "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    private long getTime() {
        int minute = timePicker.getMinute();
        int hour = timePicker.getHour();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis();
    }
    private void createNotificationChannel() {
        String name = "Notif Channel";
        String desc = "A Description of the Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelID, name, importance);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setDescription(desc);
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}