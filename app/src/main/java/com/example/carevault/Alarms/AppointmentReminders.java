package com.example.carevault.Alarms;

import static com.example.carevault.Alarms.Notification.channelID;
import static com.example.carevault.Alarms.Notification.messageExtra;
import static com.example.carevault.Alarms.Notification.musicExtra;
import static com.example.carevault.Alarms.Notification.notificationID;
import static com.example.carevault.Alarms.Notification.repeatExtra;
import static com.example.carevault.Alarms.Notification.silentExtra;
import static com.example.carevault.Alarms.Notification.titleExtra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carevault.Adapters.Note1;
import com.example.carevault.MainFragment;
import com.example.carevault.R;
import com.example.carevault.Utility;
import com.example.carevault.fragments.CalenderFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppointmentReminders extends AppCompatActivity {
    RelativeLayout textView;
    private boolean isYellow = true;
    Button submitButton;
    ImageButton menu,back;
    private static int MAX_REPEATS = 4;
    DatePicker datePicker;
    TimePicker timePicker;
    TextView pageTitle, textView1,temp;
    CheckBox ch4, ch1, ch2, ch3,ch5,ch6;
    EditText titleET,messageET;
    String title,content,docId,timestamp;
    String silent="true";
    Spinner spinner1,spinner2;
    TextView temp7,temp2,temp3,temp4,temp5,temp6;
    private Map<Integer,String> map=new HashMap();
    boolean isEdit=false;
    String music="default";
    int numberOfRepeats=0;
    boolean isAlarmCanceled = false;
    boolean repeat=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_reminders);
        pageTitle=findViewById(R.id.hello);
        ch2=(CheckBox)findViewById(R.id.checkBox2);
        ch1=(CheckBox)findViewById(R.id.checkBox1);
        ch3=(CheckBox)findViewById(R.id.checkBox3);
        ch4=(CheckBox)findViewById(R.id.checkBox4);
        ch5=(CheckBox)findViewById(R.id.checkBox5);
        ch6=(CheckBox)findViewById(R.id.checkBox6);
        datePicker=findViewById(R.id.datePicker);
        timePicker=findViewById(R.id.timePicker);
        textView = findViewById(R.id.text3);
        textView1=findViewById(R.id.text4);
        titleET=findViewById(R.id.titleET);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        temp=findViewById(R.id.temp);
        temp2 = findViewById(R.id.temp2);
        temp3=findViewById(R.id.temp3);
        temp4=findViewById(R.id.temp4);
        temp5=findViewById(R.id.temp5);
        temp6=findViewById(R.id.temp6);
        temp7=findViewById(R.id.temp7);
        back=findViewById(R.id.back);
        // Create a ClickableSpan to handle the click event
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                isYellow = !isYellow;
                updateBackgroundColor(temp);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Disable underline for the ClickableSpan
                ds.setUnderlineText(false);
            }
        };
        SpannableString spannableString = new SpannableString("M");
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        temp.setText(spannableString);

        temp.setMovementMethod(LinkMovementMethod.getInstance());
        setTextViewClickListener(temp2);
        setTextViewClickListener(temp3);
        setTextViewClickListener(temp4);
        setTextViewClickListener(temp5);
        setTextViewClickListener(temp6);
        setTextViewClickListener(temp7);
        String[] items = new String[]{"None","30 minute", "1 hour", "1.30 hour","2 hour",
                "2.30 hour", "3 hour","3.30 hour", "4 hour", "4.30 hour","5 hour","5.30 hour","6 hour","6.30 hour",
                "7 hour","7.30 hour","8 hour"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner1.setAdapter(adapter);
        String[] items1 = new String[]{"None","1", "2", "3", "4","5","6","7","8"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        spinner2.setAdapter(adapter1);
        Switch sw = (Switch) findViewById(R.id.switch1);
        Switch s1 = (Switch) findViewById(R.id.switch2);
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
            //ArrayList<String> days=getIntent().getStringArrayListExtra("days");
            int[] a = new int[checkboxList.size()];
            for (int i = 0; i < checkboxList.size(); i++) {
                a[i] = checkboxList.get(i);
            }
            if(a[0]==1){
                temp.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                ch1.setChecked(true);
            }
            if(a[1]==1){
                temp2.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                ch2.setChecked(true);
            }
            if(a[2]==1){
                temp3.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                ch3.setChecked(true);
            }
            if(a[3]==1){
                temp4.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                ch4.setChecked(true);
            }
            if(a[4]==1){
                temp5.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                ch5.setChecked(true);
            }
            if(a[5]==1){
                temp6.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                ch6.setChecked(true);
            }
            if(a[6]==1){
                temp7.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                ch6.setChecked(true);
            }
            String sessionId = getIntent().getStringExtra("text");
            //boolean te=getIntent().getBooleanExtra("repeat",true);

            music=sessionId;
            textView1.setText(sessionId);
            pageTitle.setText("Edit your note");
            cancelButton.setVisibility(View.VISIBLE);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(AppointmentReminders.this, textView);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.menu_appoint, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        textView1.setText(menuItem.getTitle());
                        music=menuItem.getTitle().toString();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAX_REPEATS=0;
                isAlarmCanceled=true;
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
                    Intent i=new Intent(getApplicationContext(), Notification.class);
                    i.putExtra("check",true);
                } else {
                    // The toggle is disabled
                    silent="false";
                    Intent i=new Intent(getApplicationContext(), Notification.class);
                    i.putExtra("check",false);
                }
            }
        });
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                    repeat=false;
                }
            }
        });
        back.setOnClickListener(view -> {
            Intent i=new Intent(AppointmentReminders.this, MainFragment.class);
            startActivity(i);
            finish();
        });
    }
    private void setTextViewClickListener(final TextView textView) {
        // Set the initial background color

        // Create a ClickableSpan to handle the click event
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Toggle the background color on each click
                isYellow = !isYellow;
                updateBackgroundColor(textView);

                // Handle the click event here
                // For example, you can perform some action or navigate to another screen
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Disable underline for the ClickableSpan
                ds.setUnderlineText(false);
            }
        };
        SpannableString spannableString = new SpannableString(textView.getText());
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the SpannableString to the TextView
        textView.setText(spannableString);

        // Make the TextView clickable
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void updateBackgroundColor(TextView textView) {
        // Change the background color based on the current state
        int newColor = isYellow ? Color.YELLOW : Color.WHITE;
        textView.setBackgroundTintList(ColorStateList.valueOf(newColor));
    }
    private int getFinalColor(TextView textView) {
        // Return the final background color of the TextView
        ColorStateList colorStateList = textView.getBackgroundTintList();
        if (colorStateList != null) {
            // Return the final background color of the TextView
            return colorStateList.getDefaultColor();
        } else {
            // Handle the case where the colorStateList is null
            // You can return a default color or handle it as appropriate for your application
            return Color.TRANSPARENT; // For example, return TRANSPARENT if no color is set
        }
    }
    private void scheduleNotification() {
        Intent intent = new Intent(getApplicationContext(), Notification.class);
        String title = titleET.getText().toString();
        String message = messageET.getText().toString();
        if(title.isEmpty() || title==null){
            titleET.setError("Please Enter description :)");
            return;
        }
        //String id=
        long time = getTime();
        Note1 note = new Note1();
        note.setText1(music);
        note.setSilent(silent);
        note.setRepeat(repeat);
        note.setTimes(String.valueOf(spinner2.getSelectedItemPosition()));
        note.setTimer(String.valueOf(spinner1.getSelectedItemPosition()));
        note.setTitle(title);
        Date date = new Date(time);
        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(getApplicationContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        note.setContent(timeFormat.format(date));
        note.setTimestamp(dateFormat.format(time));
        intent.putExtra(silentExtra,silent);
        intent.putExtra(titleExtra, title);
        intent.putExtra(messageExtra, message);
        intent.putExtra(musicExtra,music);
        if(repeat==true)
            intent.putExtra(repeatExtra,"true");
        else
            intent.putExtra(repeatExtra,"false");
        ArrayList<Integer> selectedDays = new ArrayList<>();
        int []a=new int[7];
        for(int i:a){
            a[i]=0;
        }
        if (getFinalColor(temp)==Color.YELLOW) {
            a[0]=1;
            selectedDays.add(Calendar.MONDAY);
        }
        if (getFinalColor(temp2)==Color.YELLOW) {
            a[1]=1;
            selectedDays.add(Calendar.TUESDAY);
        }
        if (getFinalColor(temp3)==Color.YELLOW) {
            a[2]=1;
            selectedDays.add(Calendar.WEDNESDAY);
        }
        if (getFinalColor(temp4)==Color.YELLOW) {
            a[3]=1;
            selectedDays.add(Calendar.THURSDAY);
        }
        if (getFinalColor(temp5)==Color.YELLOW) {
            a[4]=1;
            selectedDays.add(Calendar.FRIDAY);
        }
        if (getFinalColor(temp6)==Color.YELLOW) {
            a[5]=1;
            selectedDays.add(Calendar.SATURDAY);
        }
        if (getFinalColor(temp7)==Color.YELLOW) {
            a[6]=1;
            selectedDays.add(Calendar.SUNDAY);
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
            ArrayList<String> getDay=new ArrayList<>();
            ArrayList<String> ids2=new ArrayList<>();
            for (int selectedDay : selectedDays) {
                getDay.add(String.valueOf(uniqueNotificationID));
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
                Toast.makeText(AppointmentReminders.this,"1 "+selectedDay,Toast.LENGTH_SHORT).show();
                Calendar nextSelectedDay = (Calendar) calendar.clone();
                nextSelectedDay.add(Calendar.DAY_OF_YEAR, daysToAdd);
                nextSelectedDay.set(Calendar.HOUR_OF_DAY, 0);
                nextSelectedDay.set(Calendar.MINUTE, 0);
                long nextSelectedDayTime = nextSelectedDay.getTimeInMillis();
                Log.d("AlarmDebug", "Alarm time for day " + selectedDay + ": " + map);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (dayOfWeek == selectedDay && nextSelectedDayTime > System.currentTimeMillis()) {
                    if(repeat){
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                time, // Repeat every week
                                pendingIntent);
                        ids2.add(String.valueOf((int)(uniqueNotificationID+7*3000)));
                        Toast.makeText(AppointmentReminders.this,"1 ",Toast.LENGTH_SHORT).show();
                    }else {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                time, // Repeat every week
                                pendingIntent);
                        ids2.add(String.valueOf((int)(uniqueNotificationID)));
                        Toast.makeText(AppointmentReminders.this,"2 ",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    if(repeat){
//                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                                nextSelectedDayTime+24*60*60*1000*(selectedDay-2),
//                                AlarmManager.INTERVAL_DAY * 7,// Repeat every week
//                                pendingIntent);
//                        uniqueNotificationID+=10;
//                        pendingIntent=PendingIntent.getBroadcast(
//                                getApplicationContext(), (int)(uniqueNotificationID), intent,
//                                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
//                        );
//                        ids2.add(String.valueOf((int)(uniqueNotificationID)));
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                nextSelectedDayTime,
                                pendingIntent);
                        uniqueNotificationID+=10;
                        Toast.makeText(AppointmentReminders.this,"3 ",Toast.LENGTH_SHORT).show();
                    }else {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                nextSelectedDayTime, // Repeat every week
                                pendingIntent);
                        ids2.add(String.valueOf((int)(uniqueNotificationID)));
                        Toast.makeText(AppointmentReminders.this,"4 ",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            ArrayList<String> ids=new ArrayList<>();
            ids.add(String.valueOf(uniqueNotificationID));
            note.setDays(getDay);
            note.setMids(ids);
            note.setIds2(ids2);
        }else{
            int uniqueNotificationID = (int) System.currentTimeMillis();
            note.setId(String.valueOf(uniqueNotificationID));
            intent.putExtra(String.valueOf(notificationID),uniqueNotificationID);
            Log.d("string" , "Here the id" + note.getId());
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                    getApplicationContext(), uniqueNotificationID, intent,
//                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
//            );
            ArrayList<String> ids=new ArrayList<>();
            ArrayList<String> ids2=new ArrayList<>();
            ArrayList<String> getDay=new ArrayList<>();
            int temp1=spinner1.getSelectedItemPosition();
            int temp2=spinner2.getSelectedItemPosition();
            if((temp1!=0 && temp2==0) || (temp1==0)){
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), uniqueNotificationID, intent,
                        PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
                );
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        time, // Repeat every week
                        pendingIntent);
                Toast.makeText(AppointmentReminders.this,temp1 +"helloo "+ temp2,Toast.LENGTH_SHORT).show();
                Toast.makeText(AppointmentReminders.this,temp1 +" "+ temp2,Toast.LENGTH_SHORT).show();
                ids.add(String.valueOf(uniqueNotificationID));
                ids2.add(String.valueOf(uniqueNotificationID));
                getDay.add(String.valueOf(uniqueNotificationID));
            }else{
                Toast.makeText(AppointmentReminders.this,temp1 +" "+ temp2,Toast.LENGTH_SHORT).show();
                for (int i=0;i<temp2;i++) {
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), uniqueNotificationID, intent,
                            PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    uniqueNotificationID++;
                    ids.add(String.valueOf(uniqueNotificationID));
                    ids2.add(String.valueOf(uniqueNotificationID));
                    getDay.add(String.valueOf(uniqueNotificationID));
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            time, // Repeat every week
                            pendingIntent);
                    Toast.makeText(AppointmentReminders.this,time+" ",Toast.LENGTH_SHORT).show();
                    Toast.makeText(AppointmentReminders.this,i+" ",Toast.LENGTH_SHORT).show();
                    time+=30*60*10000*temp1;
                }
            }
            //AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            note.setMids(ids);
            note.setIds2(ids2);
            note.setDays(getDay);
            //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            //scheduleRepeatingAlarmInternal(alarmManager,pendingIntent);
        }
        savenotetoFirebase(note);
        //showAlert(time, title, message);
    }
    private void savenotetoFirebase(Note1 note) {
        DocumentReference documentReference;
        if(isEdit){
            cancelScheduledAlarms();
            documentReference= Utility.getCollectionReferenceForNotes().document();
        }
        else{
            documentReference=Utility.getCollectionReferenceForNotes().document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AppointmentReminders.this, "Note added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(AppointmentReminders.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    Toast.makeText(AppointmentReminders.this, "Alarm deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(AppointmentReminders.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = new Intent(getApplicationContext(),Notification.class);
        String s=getIntent().getStringExtra("id");
        ArrayList<String> al=new ArrayList<>(getIntent().getStringArrayListExtra("Mids"));
        ArrayList<String> al1=new ArrayList<>(getIntent().getStringArrayListExtra("days"));
        ArrayList<String> al2=new ArrayList<>(getIntent().getStringArrayListExtra("ids2"));
        if(al!=null) {
            for (int i = 0; i < al.size(); i++) {
                int temp = Integer.valueOf(al.get(i));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), temp, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        }
        if(al1!=null) {
            for (int i = 0; i < al1.size(); i++) {
                int temp = Integer.valueOf(al1.get(i));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), temp, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Alarms"+i, Toast.LENGTH_SHORT).show();
            }
        }
        if(al2!=null) {
            for (int i = 0; i < al2.size(); i++) {
                int temp = Integer.valueOf(al2.get(i));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), temp, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Alarms"+i, Toast.LENGTH_SHORT).show();
            }
        }
        int res=Integer.valueOf(s);
        Log.d("AlarmDebug", "Alarm time  "  + ": " + res + " "+ s);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), res, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarms canceled", Toast.LENGTH_SHORT).show();
    }

}