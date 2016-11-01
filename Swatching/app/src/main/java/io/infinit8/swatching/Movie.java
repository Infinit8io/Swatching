package io.infinit8.swatching;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by drksnw on 11/1/16.
 */

public class Movie {

    private int id;
    private String title;
    private int idGenre;
    private Bitmap poster;

    public Movie(int id, String title, int idGenre){
        this.id = id;
        this.title = title;
        this.idGenre = idGenre;
        requestPoster();
    }

    public static ArrayList<Movie> getPopularMovies(){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try {
            String res = (String) new CheckTMDbTask().execute("GET_POPULAR", "1").get();
            JSONObject resObj = new JSONObject(res);
            JSONArray results = resObj.getJSONArray("results");
            for(int i = 0; i < results.length(); i++){
                JSONObject mov = (JSONObject)results.get(i);
                movieArrayList.add(new Movie(mov.getInt("id"), mov.getString("title"), mov.getJSONArray("genre_ids").getInt(0)));
            }

            res = (String) new CheckTMDbTask().execute("GET_POPULAR", "2").get();
            resObj = new JSONObject(res);
            results = resObj.getJSONArray("results");
            for(int i = 0; i < results.length(); i++){
                JSONObject mov = (JSONObject)results.get(i);
                movieArrayList.add(new Movie(mov.getInt("id"), mov.getString("title"), mov.getJSONArray("genre_ids").getInt(0)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return movieArrayList;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public Bitmap getPoster(){
        return poster;
    }

    public void requestPoster(){
        String baseUrl = "http://image.tmdb.org/t/p/w154";
        try {
            String res = (String)new CheckTMDbTask().execute("MOVIE_INFO", Integer.toString(id)).get();
            JSONObject jsonObject = new JSONObject(res);
            baseUrl += jsonObject.getString("poster_path");
            this.poster = (Bitmap)new CheckTMDbTask().execute("GET_POSTER", baseUrl).get();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
