package io.infinit8.swatching;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.provider.ContactsContract;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.bowyer.app.fabtoolbar.FabToolbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import butterknife.ButterKnife;

public class DisplayMovie extends AppCompatActivity {

    SharedPreferences sharedPref;
    Set<String> willWatch;
    Set<String> likedMovies;
    Set<String> skippedMovies;
    int actMovId;
    private FirebaseAnalytics mFirebaseAnalytics;

    FabToolbar fabToolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.parallax_toolbar);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseCrash.log("Application launched");

        Context context = this;

        sharedPref = context.getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

        willWatch = sharedPref.getStringSet("will_watch", new HashSet<String>());
        likedMovies = sharedPref.getStringSet("liked_movies", new HashSet<String>());
        skippedMovies = sharedPref.getStringSet("skipped_movies", new HashSet<String>());

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabToolbar = (FabToolbar) findViewById(R.id.fabtoolbar);

        ButterKnife.bind(this);

        fabToolbar.setFab(fab);

        //fabToolbar.expandFab();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabToolbar.expandFab();
            }
        });

        RelativeLayout willwatch = (RelativeLayout)findViewById(R.id.willwatch);
        willwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TSnackbar snackbar = TSnackbar.make(view, "You'll watch that movie.", Snackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();


                Bundle params = new Bundle();
                params.putString("movie_title", ((TextView)findViewById(R.id.txt_title_paralax)).getText().toString());
                params.putInt("movie_id", actMovId);
                mFirebaseAnalytics.logEvent("user_will_watch", params);

                willWatch.add(Integer.toString(actMovId));
                sharedPref.edit().putStringSet("will_watch", willWatch).apply();
                getNewMovie();
            }
        });

        RelativeLayout likedit = (RelativeLayout)findViewById(R.id.likedit);
        likedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TSnackbar snackbar = TSnackbar.make(view, "You liked that movie.", TSnackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();

                Bundle params = new Bundle();
                params.putString("movie_title", ((TextView)findViewById(R.id.txt_title_paralax)).getText().toString());
                params.putInt("movie_id", actMovId);
                mFirebaseAnalytics.logEvent("user_liked_it", params);

                likedMovies.add(Integer.toString(actMovId));
                sharedPref.edit().putStringSet("liked_movies", likedMovies).apply();
                getNewMovie();
            }
        });

        RelativeLayout skip = (RelativeLayout)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TSnackbar snackbar = TSnackbar.make(view, "You skipped that movie.", TSnackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();

                Bundle params = new Bundle();
                params.putString("movie_title", ((TextView)findViewById(R.id.txt_title_paralax)).getText().toString());
                params.putInt("movie_id", actMovId);
                mFirebaseAnalytics.logEvent("user_disliked_it", params);

                skippedMovies.add(Integer.toString(actMovId));
                sharedPref.edit().putStringSet("skipped_movies", skippedMovies).apply();
                getNewMovie();
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        AppBarLayout abl = (AppBarLayout)findViewById(R.id.appbar);
        abl.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state == State.EXPANDED){
                    if(!fabToolbar.isFabExpanded())
                        fabToolbar.expandFab();
                } else {
                    fabToolbar.contractFab();
                }
            }
        });


        FrameLayout fl = (FrameLayout)findViewById(R.id.imgLayout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        getNewMovie();

        collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.argb(0,0,0,0)));

        collapsingToolbar.getLayoutParams().height = size.y-getNavigationBarHeight();
        fl.getLayoutParams().height = collapsingToolbar.getLayoutParams().height;
        fl.requestLayout();

        ShakeListener sl = new ShakeListener(this);


        sl.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                getNewMovie();
            }
        });


    }

    public void getNewMovie(){
        String value = null;
        int movieId = 0;
        while(value == null){
            //Put beautiful search algorithm here.

            try {
                movieId = (int)new CheckTMDbTask().execute("GET_RANDOM_ID").get();
                if(willWatch.contains(Integer.toString(movieId)) || likedMovies.contains(Integer.toString(movieId)))
                    continue; //Here is the flying spaghetti monster
                value = (String) new CheckTMDbTask().execute("MOVIE_INFO", Integer.toString(movieId)).get();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        try {
            actMovId = movieId;
            JSONObject jmovie = new JSONObject(value);
            String title = jmovie.getString("title");
            FirebaseCrash.log("Got movie info for "+title);
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
        genresStr = genresStr.substring(0, genresStr.length()-2); // La belle astuce !
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
