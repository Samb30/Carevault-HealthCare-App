package com.example.carevault.Booking;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carevault.Adapters.DoctorAdapter;
import com.example.carevault.Adapters.ModelCategory;
import com.example.carevault.MainFragment;
import com.example.carevault.Adapters.ModelDoc;
import com.example.carevault.R;
import com.example.carevault.Adapters.SeatAdapter;
import com.example.carevault.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class temp extends AppCompatActivity {
    TextView temp,temp1;
    RecyclerView mRecyclerView;
    ImageButton menu;
    private GridView gridView;
    String te="";
    private ArrayList<String> availableSeats;
    DoctorAdapter noteAdapter;
    private String[] mNames = {"Name 1", "Name 2", "Name 3", "Name 4"}; // Example names
    private int[] mImages = {R.drawable.dctr1, R.drawable.dctr1, R.drawable.dctr1, R.drawable.dctr1}; // Example images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        temp=findViewById(R.id.temp);
        gridView = findViewById(R.id.grid_view);
        temp1=findViewById(R.id.temp1);
        mRecyclerView=findViewById(R.id.recyclerview);
        setupRecyclerView1();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new MyAdapter(this, mNames, mImages);
//        mRecyclerView.setAdapter(mAdapter);
        // Initialize and set adapter for grid view

        Button buttonSelectDate = findViewById(R.id.button_select_date);
        buttonSelectDate.setOnClickListener(v -> {
            // Get current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Create DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(temp.this,
                    (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                        // Formulate selected date
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                        te=selectedDate;
                        // Fetch and update seats for the selected date
                        setupRecyclerView(selectedDate);

                        // Optionally, you can show a message indicating the selected date
                        Toast.makeText(temp.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                    }, year, month, dayOfMonth);
            // Show DatePickerDialog
            datePickerDialog.show();
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Open new activity or perform action on grid item click
                String selectedSeat = availableSeats.get(position);
                Toast.makeText(temp.this, "="+selectedSeat, Toast.LENGTH_SHORT).show();
                //func(selectedSeat);
                Intent intent = new Intent(temp.this, PatientDetails.class);
                intent.putExtra("date",te);
                intent.putExtra("slot",selectedSeat);
                startActivity(intent);

            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(temp.this, Categories.class);
                startActivity(intent);
            }
        });
        temp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(temp.this, MainFragment.class);
                startActivity(intent);
            }
        });
    }
    private  void func(String s){
        Utility.getDateDetails()
                .whereEqualTo("date", te) // Replace "field" with your field name and value with the value you're looking for
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Perform your operation with the document here
                                // For example, you can update the document field as described before
                                String documentId = document.getId();
                                updateDocumentField(documentId,s); // position is the position of grid item clicked
                            }
                        } else {
                            // Handle errors
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void updateDocumentField(String docId,String selectedSeat) {
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = Utility.getDateDetails().document(docId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data = new HashMap<>();
                        Map<String, Boolean> times = (Map<String, Boolean>) document.get("times");
                        if (times != null) {
                            String selectedTime = "17:00"; // Implement this method
                            //Toast.makeText(temp.this, "="+selectedTime, Toast.LENGTH_SHORT).show();
                            times.put(selectedSeat, false);
                            data.put("times", times);

                            // Update Firestore document
                            docRef.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Document field updated successfully
                                        Toast.makeText(temp.this, "Time updated successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle errors
                                        Toast.makeText(temp.this, "Failed to update time", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        // Document does not exist
                        Toast.makeText(temp.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Task failed with an exception
                    Toast.makeText(temp.this, "Failed to fetch document", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void setupRecyclerView(String s) {
        Query query=Utility.getDateDetails().whereEqualTo("date",s);
        FirestoreRecyclerOptions<ModelDoc> options=new FirestoreRecyclerOptions.Builder<ModelDoc>()
                .setQuery(query,ModelDoc.class).build();
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String imageUrl = document.getString("category");
                HashMap<String,Boolean> map= (HashMap<String, Boolean>) document.get("times");
                ArrayList<String> keyList = new ArrayList<>();

                // Iterating through the keys of the map and adding them to the ArrayList
                for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                    if(entry.getValue()==true)
                        keyList.add(entry.getKey());
                }
                availableSeats=new ArrayList<>(keyList);
                SeatAdapter adapter1 = new SeatAdapter(this, availableSeats);
                gridView.setAdapter(adapter1);
                Toast.makeText(this, "12"+imageUrl, Toast.LENGTH_SHORT).show();
            }
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
    void setupRecyclerView1(){
        Query query=Utility.getDoctorDetails();
        FirestoreRecyclerOptions<ModelCategory> options=new FirestoreRecyclerOptions.Builder<ModelCategory>()
                .setQuery(query,ModelCategory.class).build();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter =new DoctorAdapter(options,this);
        mRecyclerView.setAdapter(noteAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }
    @Override
    public void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}