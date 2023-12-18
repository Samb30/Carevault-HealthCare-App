package com.example.carevault;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Stopwatch extends AppCompatActivity {
//    private int seconds = 0;
//    private boolean running;
//    private boolean wasRunning;
//    Chronometer timeview;
//    ImageButton play,stop;
//
//    Button startbt,stopbt,resetbt;
//    Handler handler;
//    long tmilli,tstart,tBuff,tupdate=0L;
//    int sec,min,millisec;
TextView timer ;
    Button start, pause, reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        timer=findViewById(R.id.tvTimer);
        start = (Button)findViewById(R.id.btStart);
        pause = (Button)findViewById(R.id.btPause);
        reset = (Button)findViewById(R.id.btReset);

        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                timer.setText("00:00:00");

            }
        });
//        timeview=findViewById(R.id.time_view);
//        startbt=findViewById(R.id.start_button);
//        stopbt=findViewById(R.id.stop_button);
//        play=findViewById(R.id.play);
//        stop=findViewById(R.id.stop12);
//
//        handler=new Handler();
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!wasRunning){
//                    tstart=SystemClock.uptimeMillis();
//                    handler.postDelayed(runnable,60);
//                    timeview.start();
//                    wasRunning=true;
//                    stopbt.setVisibility(View.GONE);
//                    play.setImageDrawable(getResources().getDrawable(
//                            R.drawable.baseline_pause_24
//                    ));
//                }else{
//                    tBuff+=millisec;
//                    handler.removeCallbacks(runnable);
//                    timeview.stop();
//                    wasRunning=false;
//                    play.setImageDrawable(getResources().getDrawable(
//                            R.drawable.baseline_play_arrow_24
//                    ));
//                }
//            }
//        });
//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!wasRunning){
//                    play.setImageDrawable(getResources().getDrawable(
//                            R.drawable.baseline_play_arrow_24
//                    ));
//                    tmilli=0L;
//                    tstart=0L;
//                    tBuff=0L;
//                    sec=0;
//                    tupdate=0L;
//                    millisec=0;
//                    min=0;
//                    timeview.setText("00:00:00");
//                }
//            }
//        });
//        if (savedInstanceState != null) {
//            seconds = savedInstanceState.getInt("seconds");
//            running = savedInstanceState.getBoolean("running");
//            wasRunning = savedInstanceState.getBoolean("wasRunning");
//        }
//        runTimer();
    }
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText("" +String.format("%02d" ,Minutes) + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%02d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
//    public Runnable runnable=new Runnable() {
//        @Override
//        public void run() {
//            tmilli= SystemClock.uptimeMillis()-tstart;
//            tupdate=tBuff+tmilli;
//            sec=(int) (tupdate/1000);
//            min=sec/60;
//            sec=sec%60;
//            millisec=(int) (tupdate%100);
//            timeview.setText(String.format("%02d",min) + ":" + String.format("%02d",sec) +":"+String.format("%02d",millisec));
//            handler.postDelayed(this,60);
//        }
//    };
//    @Override
//    public void onSaveInstanceState(
//            Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState
//                .putInt("seconds", seconds);
//        savedInstanceState
//                .putBoolean("running", running);
//        savedInstanceState
//                .putBoolean("wasRunning", wasRunning);
//    }
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//        wasRunning = running;
//        running = false;
//    }
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        if (wasRunning) {
//            running = true;
//        }
//    }
//    public void onClickStart(View view)
//    {
//        running = true;
//    }
//    public void onClickStop(View view)
//    {
//        running = false;
//    }
//    public void onClickReset(View view)
//    {
//        running = false;
//        seconds = 0;
//    }
//    private void runTimer()
//    {
//        final TextView timeView = (TextView)findViewById(R.id.time_view);
//        final Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//
//            public void run()
//            {
//                int totalMilliseconds = seconds * 1000 + milliseconds; // Convert seconds to milliseconds and add current milliseconds
//                int hours = totalMilliseconds / 3600000;
//                int minutes = (totalMilliseconds % 3600000) / 60000;
//                int secs = (totalMilliseconds % 60000) / 1000;
//                int millis = totalMilliseconds % 1000;
//                String time = String.format(Locale.getDefault(),"%d:%02d:%02d.%03d", hours, minutes, secs,millis);
//                timeView.setText(time);
//                if (running) {
//                    seconds++;
//                }
//                handler.postDelayed(this, 1000);
//            }
//        });
//    }
}