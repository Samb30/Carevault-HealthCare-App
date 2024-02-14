package com.example.carevault.Booking;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.carevault.Adapters.ModelDoc;
import com.example.carevault.R;
import com.example.carevault.Adapters.SeatAdapter;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateActivity extends AppCompatActivity {
    private GridView gridView;
    TextView temp,temp1,docName,textViewName;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch2;
    String te="";
    private ArrayList<String> availableSeats;
    ImageButton back;
    CalendarView calendarView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
//        Button buttonSelectDate = findViewById(R.id.btndatettime);
        temp1=findViewById(R.id.time);
        docName=findViewById(R.id.docName);
        calendarView=findViewById(R.id.calender);
        Calendar calendar1 = Calendar.getInstance();
        int year = calendar1.get(Calendar.YEAR);
        int month = calendar1.get(Calendar.MONTH) + 1; // Months are zero-based
        int dayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
        String currentDate = year + "-" + month + "-" + dayOfMonth;
        calendarView.setMinDate(calendar1.getTimeInMillis());
        long now1 = System.currentTimeMillis() - 1000;
        calendarView.setMaxDate(now1+(1000*60*60*24*9));
        textViewName=findViewById(R.id.textViewName);
        switch2=findViewById(R.id.switch2);
        back=findViewById(R.id.back);
        gridView = findViewById(R.id.grid_view);
        String temp=getIntent().getStringExtra("docId");
        String dname=getIntent().getStringExtra("dname");
        String category=getIntent().getStringExtra("category") + " |";
        String location=getIntent().getStringExtra("location");
        String hospital=getIntent().getStringExtra("hospital");
        setupRecyclerView(currentDate,temp);
        temp1.setText("Available time slots for: " + currentDate);
        docName.setText(dname);
        textViewName.setText(category);
        TextView location1 =findViewById(R.id.location);
        TextView hosp=findViewById(R.id.hospital);
        hosp.setText(hospital);
        location1.setText(location);
//        buttonSelectDate.setOnClickListener(v -> {
//            // Get current date
//            Calendar calendar = Calendar.getInstance();
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//
//            // Create DatePickerDialog
//            DatePickerDialog datePickerDialog = new DatePickerDialog(DateActivity.this,
//                    (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
//                        Calendar currentDate = Calendar.getInstance();
//                        int currentYear = currentDate.get(Calendar.YEAR);
//                        int currentMonth = currentDate.get(Calendar.MONTH);
//                        int currentDayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH);
//                        if (selectedYear < currentYear ||
//                                (selectedYear == currentYear && selectedMonth < currentMonth) ||
//                                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth < currentDayOfMonth)){
//                            temp1.setText("Please select correct date");
//                            availableSeats=new ArrayList<>();
//                            SeatAdapter adapter1 = new SeatAdapter(this, availableSeats);
//                            gridView.setAdapter(adapter1);
//                            Toast.makeText(DateActivity.this, "Please select a date after the current date", Toast.LENGTH_SHORT).show();
//                        }else{
//                            currentDate.add(Calendar.DAY_OF_MONTH, 10);
//                            Calendar selectedDate = Calendar.getInstance();
//                            selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);
//                            if (selectedDate.after(currentDate)) {
//                                temp1.setText("Please select correct date");
//                                availableSeats=new ArrayList<>();
//                                SeatAdapter adapter1 = new SeatAdapter(this, availableSeats);
//                                gridView.setAdapter(adapter1);
//                                Toast.makeText(DateActivity.this, "Booking is not allowed more than 10 days in advance", Toast.LENGTH_SHORT).show();
//                            }else{
//                                String selectedDate1 = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
//                                te=selectedDate1;
//                                String selectedDateStr = String.format(Locale.getDefault(), "%02d %s, %d",-
//                                        selectedDayOfMonth,
//                                        new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date(selectedYear - 1900, selectedMonth, selectedDayOfMonth)),
//                                        selectedYear);
//                                setupRecyclerView(selectedDate1,temp);
//                                temp1.setText("Available time slots for: "+selectedDateStr);
//                            }
//                        }
//                    }, year, month, dayOfMonth);
//            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
//            long now = System.currentTimeMillis() - 1000;
//            datePickerDialog.getDatePicker().setMaxDate(now+(1000*60*60*24*10));
//            datePickerDialog.show();
//        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                // Month is zero-based, so add 1 to get the actual month
                String selectedDate2 = selectedYear + "-" + selectedMonth + "-" + selectedDayOfMonth;
                Calendar currentDate = Calendar.getInstance();
                {
                    currentDate.add(Calendar.DAY_OF_MONTH, 10);
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);
                    {
                        String selectedDate1 = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                        te = selectedDate1;
                        String selectedDateStr = String.format(Locale.getDefault(), "%02d %s, %d",
                                        selectedDayOfMonth,
                                new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date(selectedYear - 1900, selectedMonth, selectedDayOfMonth)),
                                selectedYear);
                        setupRecyclerView(selectedDate1, temp);
                        temp1.setText("Available time slots for: " + selectedDateStr);
                    }
                }
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Open new activity or perform action on grid item click
                String selectedSeat = availableSeats.get(position);
                //func(selectedSeat);
                Intent intent = new Intent(DateActivity.this, PatientDetails.class);
                intent.putExtra("date",te);
                intent.putExtra("slot",selectedSeat);
                intent.putExtra("dname",dname);
                intent.putExtra("category",category);
                intent.putExtra("docId",temp);
                startActivity(intent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    void setupRecyclerView(String s,String temp) {
        Query query= Utility.getDoctorDetails().document(temp).collection("Date").whereEqualTo("date",s);
        FirestoreRecyclerOptions<ModelDoc> options=new FirestoreRecyclerOptions.Builder<ModelDoc>()
                .setQuery(query,ModelDoc.class).build();
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<String> keyList = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String imageUrl = document.getString("category");
                String curr = document.getString("date");
                HashMap<String,Boolean> map= (HashMap<String, Boolean>) document.get("times");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());
                for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                    if(entry.getValue()==true) {
                        if(curr.equals(currentDate)) {
                            Calendar calendar = Calendar.getInstance();
                            int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // Get current hour (0-23)
                            String temp1 = currentHour + ":00";   // Get current minute (0-59)
                            if (currentHour < 10)
                                temp1 = "0" + temp1;
                            if (entry.getKey().compareTo(temp1) > 0)
                                keyList.add(entry.getKey());
                        }else keyList.add(entry.getKey());
                    }
                }
            }
            availableSeats=new ArrayList<>(keyList);
            Collections.sort(availableSeats);
            SeatAdapter adapter1 = new SeatAdapter(this, availableSeats);
            gridView.setAdapter(adapter1);
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error getting documents.", e);
        });
    }
    @Nullable
    @Contract(pure = true)
    private String getTimeForPosition(int position) {
        // Implement this method to get the time based on grid item position
        return null;
    }
    @Override
    public void onBackPressed() {
        // Call super.onBackPressed() to execute the default back button behavior
        super.onBackPressed();
    }
}