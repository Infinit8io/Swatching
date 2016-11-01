package io.infinit8.swatching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_movies);

        getSupportActionBar().hide();

        ArrayList<Movie> movies = Movie.getPopularMovies();

        ListView lv = (ListView)findViewById(R.id.listMovies);

        MovieListAdapter mlAdapter = new MovieListAdapter(this, R.layout.movie_list_item, movies);

        lv.setAdapter(mlAdapter);

        Button btnConfirm = (Button)findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAct();
            }
        });

    }

    public void nextAct(){
        Toast.makeText(this, "Here's where I'd put the next activity.\nIf I had one !", Toast.LENGTH_LONG).show();
    }
}
