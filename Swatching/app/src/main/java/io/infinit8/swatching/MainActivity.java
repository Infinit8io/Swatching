package io.infinit8.swatching;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

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
        Intent in = new Intent(this, ChooseMovies.class);
        startActivity(in);
    }
}
