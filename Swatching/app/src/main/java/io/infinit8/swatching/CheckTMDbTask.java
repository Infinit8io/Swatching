package io.infinit8.swatching;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by drksnw on 11/1/16.
 */

public class CheckTMDbTask extends AsyncTask<String, Void, Object> {
    @Override
    protected Object doInBackground(String... strings) {
        if(strings[0] == "MOVIE_INFO"){
            try{
                URL url = new URL("https://api.themoviedb.org/3/movie/"+strings[1]+"?api_key="+Config.TMDB_API_KEY+"&language=fr-FR");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() != 200){
                    return null;
                }
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                return sb.toString();

            } catch(Exception e){
                e.printStackTrace();
            }
        } else if(strings[0] == "GET_PICTURE") {
            try {
                URL url = new URL(strings[1]);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(strings[0] == "GET_POPULAR"){
            try{
                URL url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key="+Config.TMDB_API_KEY+"&language=fr-FR&page="+strings[1]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                return sb.toString();
            } catch(Exception e){
                e.printStackTrace();
            }

        } else if(strings[0] == "GET_RANDOM_ID"){
            try{
                int pageNumber = (int)(Math.random()*100+1);
                URL url = new URL("https://api.themoviedb.org/3/discover/movie?api_key="+Config.TMDB_API_KEY+"&page="+pageNumber+"&include_adult=true");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                JSONObject jpage = new JSONObject(sb.toString());
                JSONArray jres = jpage.getJSONArray("results");
                int movieIndex = (int)(Math.random()*jres.length());
                return ((JSONObject)jres.get(movieIndex)).getInt("id");
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
