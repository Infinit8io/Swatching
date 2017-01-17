package io.infinit8.swatching;

import android.*;
import android.Manifest;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by drksnw on 1/10/17.
 */

public class CheckIfAtHomeService extends IntentService {

    public CheckIfAtHomeService(){
        super("CheckIfAtHomeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sp = this.getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        String homePosition = sp.getString("home_position", "UNDEFINED");

        if(!homePosition.equals("UNDEFINED") && !homePosition.equals("UNAVAILABLE")) {
            //Home position already set.
            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            try {
                Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location homeLocation = new Location("Home");
                Log.d("home pos", homePosition);
                Log.d("act pos", lastKnownLocation.getLatitude()+","+lastKnownLocation.getLongitude());
                String[] latlon = homePosition.split(",");
                homeLocation.setLatitude(Double.parseDouble(latlon[0]));
                homeLocation.setLongitude(Double.parseDouble(latlon[1]));
                if(lastKnownLocation.distanceTo(homeLocation) < 50){
                    //User is at home
                    Set<String> willWatch = sp.getStringSet("will_watch", new HashSet<String>());
                    if(!willWatch.isEmpty()){
                        String[] movieIds = willWatch.toArray(new String[willWatch.size()]);
                        int movieIndex = new Random().nextInt(movieIds.length);
                        String selectedMovieId = movieIds[movieIndex];
                        String movieData = getMovieData(selectedMovieId);
                        JSONObject jmovie = new JSONObject(movieData);
                        String title = jmovie.getString("title");
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_theaters_black_24dp).setContentTitle("Nothing to do tonight ?").setContentText("Why not watch "+title+" ?");
                        Intent notificationOpen = new Intent(this, DisplayMovie.class);
                        notificationOpen.putExtra("movie_id", selectedMovieId);
                        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, notificationOpen, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(resultPendingIntent);
                        int notificationId = 123;
                        NotificationManager mNotifyMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                        mNotifyMgr.notify(notificationId, mBuilder.build());
                    }

                }

            } catch(SecurityException se){
                Log.e("Error", "Permission not granted !");
            } catch(JSONException ex){
                Log.e("Error", "Can't get JSON info");
            }
        }
    }

    public String getMovieData(String movieId){
        try{
            URL url = new URL("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+Config.TMDB_API_KEY+"&language=fr-FR");
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
        return null;
    }
}
