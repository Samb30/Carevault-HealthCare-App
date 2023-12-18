package com.example.carevault;

import static com.example.carevault.Notification.channelID;
import static com.example.carevault.Notification.messageExtra;
import static com.example.carevault.Notification.notificationID;
import static com.example.carevault.Notification.titleExtra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivitySar2 extends AppCompatActivity {
    Button submitButton;
    ImageButton menu;
    DatePicker datePicker;
    TimePicker timePicker;
    TextView pageTitle;
    CheckBox ch4, ch1, ch2, ch3,ch5,ch6;
    EditText titleET,messageET;
    String title,content,docId,timestamp;
    private Map<Integer,String> map=new HashMap<>();
    boolean isEdit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsar2);
//        db = FirebaseFirestore.getInstance();
        createNotificationChannel();
        pageTitle=findViewById(R.id.hello);
        ch2=(CheckBox)findViewById(R.id.checkBox2);
        ch1=(CheckBox)findViewById(R.id.checkBox1);
        ch3=(CheckBox)findViewById(R.id.checkBox3);
        ch4=(CheckBox)findViewById(R.id.checkBox4);
        ch5=(CheckBox)findViewById(R.id.checkBox5);
        ch6=(CheckBox)findViewById(R.id.checkBox6);
        submitButton=findViewById(R.id.submitButton);
        datePicker=findViewById(R.id.datePicker);
        timePicker=findViewById(R.id.timePicker);
        titleET=findViewById(R.id.titleET);
        Switch sw = (Switch) findViewById(R.id.switch1);
        menu=findViewById(R.id.menu1);
        messageET=findViewById(R.id.messageET);
        //recieve
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        docId=getIntent().getStringExtra("docId");
        timestamp=getIntent().getStringExtra("date");
        Button cancelButton = findViewById(R.id.cancelButton);
        if(docId!=null && !docId.isEmpty()){
            isEdit=true;
        }
        titleET.setText(title);
        messageET.setText(content);

        if(isEdit){
            ArrayList<Integer> checkboxList=getIntent().getIntegerArrayListExtra("checkbox");
            int[] a = new int[checkboxList.size()];
            for (int i = 0; i < checkboxList.size(); i++) {
                a[i] = checkboxList.get(i);
            }
            if(a[0]==1){
                ch1.setChecked(true);
            }
            if(a[1]==1){
                ch2.setChecked(true);
            }
            if(a[2]==1){
                ch3.setChecked(true);
            }
            if(a[3]==1){
                ch4.setChecked(true);
            }
            if(a[4]==1){
                ch5.setChecked(true);
            }
            if(a[5]==1){
                ch6.setChecked(true);
            }
            pageTitle.setText("Edit your note");
            cancelButton.setVisibility(View.VISIBLE);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelScheduledAlarms();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification();
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent i=new Intent(getApplicationContext(),Notification.class);
                    i.putExtra("check",true);
                } else {
                    // The toggle is disabled
                    Intent i=new Intent(getApplicationContext(),Notification.class);
                    i.putExtra("check",false);
                }
            }
        });
    }
    private void scheduleNotification() {
        Intent intent = new Intent(getApplicationContext(), Notification.class);
        String title = titleET.getText().toString();
        String message = messageET.getText().toString();
        //String id=
        long time = getTime();

        Note note = new Note();
        note.setTitle(title);
        Date date = new Date(time);
        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(getApplicationContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        note.setContent(timeFormat.format(date));
        note.setTimestamp(dateFormat.format(time));
        intent.putExtra(titleExtra, title);
        intent.putExtra(messageExtra, message);

        ArrayList<Integer> selectedDays = new ArrayList<>();
        int []a=new int[7];
        for(int i:a){
            a[i]=0;
        }
        if (ch1.isChecked()) {
            a[0]=1;
            selectedDays.add(Calendar.MONDAY);
        }
        if (ch2.isChecked()) {
            a[1]=1;
            selectedDays.add(Calendar.TUESDAY);
        }
        if (ch3.isChecked()) {
            a[2]=1;
            selectedDays.add(Calendar.WEDNESDAY);
        }
        if (ch4.isChecked()) {
            a[3]=1;
            selectedDays.add(Calendar.THURSDAY);
        }
        if (ch5.isChecked()) {
            a[4]=1;
            selectedDays.add(Calendar.FRIDAY);
        }
        if (ch6.isChecked()) {
            a[5]=1;
            selectedDays.add(Calendar.SATURDAY);
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i:a){
            arrayList.add(i);
        }
        note.setArr(arrayList);
        if (!selectedDays.isEmpty()) {
            int uniqueNotificationID = (int) System.currentTimeMillis(); // Unique ID for each notification
            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(time);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            for (int selectedDay : selectedDays) {
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), uniqueNotificationID, intent,
                        PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
                );
                note.setId(String.valueOf(uniqueNotificationID));
                uniqueNotificationID++;
                Log.d("AlarmDebug", "Scheduling alarm for day: " + selectedDay);
                int daysToAdd = 0;
                if (selectedDay != dayOfWeek) {
                    daysToAdd = (selectedDay + 7 - dayOfWeek) % 7;
                }
                else if (selectedDay == dayOfWeek) {
                    daysToAdd = 7; // Set to 7 to schedule for the same day next week
                }
                Calendar nextSelectedDay = (Calendar) calendar.clone();
                nextSelectedDay.add(Calendar.DAY_OF_YEAR, daysToAdd);
                nextSelectedDay.set(Calendar.HOUR_OF_DAY, 0);
                nextSelectedDay.set(Calendar.MINUTE, 0);
                long nextSelectedDayTime = nextSelectedDay.getTimeInMillis();
                Log.d("AlarmDebug", "Alarm time for day " + selectedDay + ": " + map);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                if (dayOfWeek == selectedDay && nextSelectedDayTime > System.currentTimeMillis()) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
//                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                            time,
//                            AlarmManager.INTERVAL_DAY * 7, // Repeat every week
//                            pendingIntent);
                }else {
//                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                                nextSelectedDayTime,
//                                AlarmManager.INTERVAL_DAY * 7, // Repeat every week
//                                pendingIntent);

                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            nextSelectedDayTime, // Repeat every week
                            pendingIntent);
                }
            }
        } else {
            int uniqueNotificationID = (int) System.currentTimeMillis();
            note.setId(String.valueOf(uniqueNotificationID));
            intent.putExtra(String.valueOf(notificationID),uniqueNotificationID);
            Log.d("string" , "Here the id" + note.getId());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(), uniqueNotificationID, intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
        savenotetoFirebase(note);

        //showAlert(time, title, message);
    }

    private void savenotetoFirebase(Note note) {

        DocumentReference documentReference;
        if(isEdit){
            documentReference=Utility.getCollectionReferenceForNotes().document(docId);
        }
        else{
            documentReference=Utility.getCollectionReferenceForNotes().document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivitySar2.this, "Note added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(MainActivitySar2.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        CollectionReference dbCourses = db.collection("Courses");
//        dbCourses.add(note).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(MainActivity2.this, "Note added", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                else{
//                    Toast.makeText(MainActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
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
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void cancelScheduledAlarms() {
        DocumentReference documentReference;
        documentReference=Utility.getCollectionReferenceForNotes().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivitySar2.this, "Note deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(MainActivitySar2.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = new Intent(getApplicationContext(), Notification.class);
        String s=getIntent().getStringExtra("id");
        int res=Integer.valueOf(s);
        Log.d("AlarmDebug", "Alarm time  "  + ": " + res + " "+ s);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), res, intent, PendingIntent.FLAG_IMMUTABLE);

        // Use the same requestCode as used when setting the alarms

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarms canceled", Toast.LENGTH_SHORT).show();
    }
    private String generateUniqueId() {
        // Generate a unique identifier using timestamp and random UUID
        long timestamp = System.currentTimeMillis();
        UUID uuid = UUID.randomUUID();
        String uniqueId = timestamp + "_" + uuid.toString();
        return uniqueId;
    }
}