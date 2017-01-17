package io.infinit8.swatching;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SetHome extends AppCompatActivity {

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnAtHome = (Button)findViewById(R.id.button4);
        Button btnNotNow = (Button)findViewById(R.id.button3);

        final SetHome thisActivity = this;

        sharedPref = this.getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

        btnAtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2348);
                }
            }
        });

        btnNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chngAct();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            try {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                String homeLocation = lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude();
                sharedPref.edit().putString("home_position", homeLocation).apply();
            } catch(SecurityException se){
                se.printStackTrace();
            }
        } else {
            String homeLocation = "UNAVAILABLE";
            sharedPref.edit().putString("home_position", homeLocation).apply();
        }

        chngAct();
    }

    public void chngAct(){
        Intent in = new Intent(this, ChooseMovies.class);
        startActivity(in);
        finish();
    }
}
