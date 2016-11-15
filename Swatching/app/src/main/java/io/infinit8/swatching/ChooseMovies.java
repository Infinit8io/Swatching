package io.infinit8.swatching;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChooseMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_movies);

        ArrayList<Movie> movies = getMoviesFromJson();

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
        Intent in = new Intent(this, DisplayMovie.class);
        startActivity(in);
    }

    public ArrayList<Movie> getMoviesFromJson(){
        ArrayList<Movie> movies = new ArrayList<>();

        String jsonFile = getStringFromRawFile(R.raw.movies);
        try {
            JSONArray jarray = new JSONArray(jsonFile);
            for(int i = 0; i<jarray.length(); i++){
                JSONObject jmovie = jarray.getJSONObject(i);
                byte[] decodedString = Base64.decode(jmovie.getString("poster_b64"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                movies.add(new Movie(jmovie.getInt("id"), jmovie.getString("title"), jmovie.getJSONArray("genres").getJSONObject(0).getInt("id"), decodedByte));
            }
        } catch (JSONException ex){
            ex.printStackTrace();
        }

        return movies;
    }

    public String getStringFromRawFile(int resId){
        InputStream is = getResources().openRawResource(resId);
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
