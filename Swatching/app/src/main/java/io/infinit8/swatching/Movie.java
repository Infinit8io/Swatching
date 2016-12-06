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
    private boolean checked;

    public Movie(int id, String title, int idGenre, Bitmap poster){
        this.id = id;
        this.title = title;
        this.idGenre = idGenre;
        this.poster = poster;
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

    public boolean getChecked(){ return checked; }
    public void setChecked(boolean isChecked){ this.checked = isChecked; }


}
