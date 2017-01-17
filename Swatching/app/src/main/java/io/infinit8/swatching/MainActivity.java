package io.infinit8.swatching;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import com.google.firebase.crash.FirebaseCrash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sp = this.getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        Set<String> watchedMovies = sp.getStringSet("watched_movies", new HashSet<String>());

        if(watchedMovies.size() != 0){
            //There are some movies, not the first use
            String location = sp.getString("home_position", "UNDEFINED");
            Intent in;
            if(location.equals("UNDEFINED")){
                //Home location not defined
                in = new Intent(this, SetHome.class);
            } else {
                in = new Intent(this, DisplayMovie.class);
            }

            startActivity(in);
        }



        ArrayList<Movie> emptyList = new ArrayList<Movie>();
        // Création des fichiers de cache.
        try {
            InternalStorage.writeObject(getApplicationContext(), "0", emptyList);
            InternalStorage.writeObject(getApplicationContext(), "1", emptyList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(this, CheckIfAtHomeService.class);
        alarmIntent = PendingIntent.getService(this, 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 20);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);

        FirebaseCrash.log("Application first use");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        WebView wv = (WebView)findViewById(R.id.iv_background);

        wv.loadDataWithBaseURL("file://android_res/drawable/", "<img src='file:///android_res/drawable/kitten.gif' style='min-height: 100%; min-width:100%; height:auto; width:auto; position:absolute; top:-100%; bottom:-100%; left:-100%; right:-100%; margin:auto; overflow:hidden;'/>", "text/html", "utf-8", null);
        wv.setHorizontalScrollBarEnabled(false);
        wv.setVerticalScrollBarEnabled(false);
        wv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return(event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        Button btnStart = (Button)findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chngAct();
            }
        });
    }

    public void chngAct(){
        Intent in = new Intent(this, SetHome.class);
        startActivity(in);
        finish();
    }
}
