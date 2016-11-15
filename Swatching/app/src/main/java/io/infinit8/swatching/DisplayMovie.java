package io.infinit8.swatching;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DisplayMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.parallax_toolbar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);



        FrameLayout fl = (FrameLayout)findViewById(R.id.imgLayout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        //Not dynamic at all - To change !
        //Obvious comment is obvious.
        collapsingToolbar.setTitle("Batman Begins");
        collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.argb(0,0,0,0)));

        collapsingToolbar.getLayoutParams().height = size.y-getNavigationBarHeight();
        fl.getLayoutParams().height = collapsingToolbar.getLayoutParams().height;
        fl.requestLayout();

        ShakeListener sl = new ShakeListener(this);

        sl.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                String value = null;
                while(value == null){
                    //Put beautiful search algorithm here.

                    try {
                        int movieId = (int)new CheckTMDbTask().execute("GET_RANDOM_ID").get();
                        value = (String) new CheckTMDbTask().execute("MOVIE_INFO", Integer.toString(movieId)).get();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
                try {
                    JSONObject jmovie = new JSONObject(value);
                    String title = jmovie.getString("title");
                    float rating = (float)jmovie.getDouble("vote_average");
                    JSONArray jgenres = jmovie.getJSONArray("genres");
                    String[] genres = new String[jgenres.length()];
                    for(int i=0;i<jgenres.length(); i++){
                        genres[i] = ((JSONObject)jgenres.get(i)).getString("name");
                    }
                    String releaseDate = jmovie.getString("release_date");
                    int releaseYear = Integer.parseInt(releaseDate.split("-")[0]);
                    String synopsys = jmovie.getString("overview");
                    Bitmap poster = (Bitmap)new CheckTMDbTask().execute("GET_PICTURE", "http://image.tmdb.org/t/p/w154"+jmovie.getString("poster_path")).get();
                    Bitmap backdrop = (Bitmap)new CheckTMDbTask().execute("GET_PICTURE", "http://image.tmdb.org/t/p/w1280"+jmovie.getString("backdrop_path")).get();

                    updateInterface(title, genres, rating, releaseYear, synopsys, poster, backdrop);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    public void showSearchMovieToast(){
        Toast.makeText(this, "Looking for a movie...", Toast.LENGTH_LONG).show();
    }

    public int getNavigationBarHeight()
    {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && !hasMenuKey)
        {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public void updateInterface(String title, String[] genres, float rating, int releaseYear, String synopsys, Bitmap poster, Bitmap backdrop){
        TextView parTitle = (TextView)findViewById(R.id.txt_title_paralax);
        TextView parGenres = (TextView)findViewById(R.id.txt_genres_paralax);
        RatingBar parRating = (RatingBar)findViewById(R.id.rating_paralax);
        ImageView parBackdrop = (ImageView)findViewById(R.id.backdrop_paralax);

        ImageView descPoster = (ImageView)findViewById(R.id.cover_desc);
        TextView descTitle = (TextView)findViewById(R.id.title_desc);
        TextView descGenres = (TextView)findViewById(R.id.genres_desc);
        RatingBar descRating = (RatingBar)findViewById(R.id.rating_desc);
        TextView descSynopsys = (TextView)findViewById(R.id.synopsys_desc);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        parTitle.setText(title);
        String genresStr = "";
        for(String g : genres){
            genresStr += g+" / ";
        }
        genresStr = genresStr.substring(0, genresStr.length()-2);
        parGenres.setText(genresStr);
        parRating.setRating(rating);
        parBackdrop.setImageBitmap(backdrop);

        descPoster.setImageBitmap(poster);
        descTitle.setText(title+" ("+releaseYear+")");
        descGenres.setText(genresStr);
        descRating.setRating(rating);
        descSynopsys.setText(synopsys);
        collapsingToolbar.setTitle(title);
    }
}
