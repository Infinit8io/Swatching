package io.infinit8.swatching;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Profile extends AppCompatActivity {



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public ListView lv;
    public ArrayList<Movie> cachedMoviesWillWatch;
    public ArrayList<Movie> cachedMoviesLiked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try{
            cachedMoviesWillWatch = (ArrayList<Movie>) InternalStorage.readObject(getApplicationContext(), "0");
            cachedMoviesLiked = (ArrayList<Movie>) InternalStorage.readObject(getApplicationContext(), "1");

        } catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment
         */
        //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
        ArrayAdapter<String> adapter;


        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, HashSet<String> watchedMovies) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putStringArrayList("watchedMovies", new ArrayList<String>(watchedMovies));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // La vue d'un onglet (tous utilisent la même).
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


            final Profile activity = (Profile)getActivity();
            activity.lv = (ListView) rootView.findViewById(R.id.listMovies);


            int listCase = getArguments().getInt(ARG_SECTION_NUMBER);

            Log.d("WHATA", "" + listCase);

            if(listCase == 1){
                Log.d("WHATA", "entre dans la section 1");


                    ArrayList<String> stringMoviesWillWatch = new ArrayList<String>();

                    // Création de la liste de strings
                    for(int i=0;i<activity.cachedMoviesWillWatch.size(); i++){
                        stringMoviesWillWatch.add(activity.cachedMoviesWillWatch.get(i).getTitle());
                    }
                    adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, stringMoviesWillWatch);
                    activity.lv.setAdapter(adapter);
                    activity.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            activity.goToMoviePage(position, 0);
                        }
                    });


            }else if(listCase == 2){
                Log.d("WHATA", "entre dans la section 2");
                    ArrayList<String> stringMoviesWillWatch = new ArrayList<String>();

                    // Création de la liste de strings
                    for(int i=0;i<activity.cachedMoviesLiked.size(); i++){
                        stringMoviesWillWatch.add(activity.cachedMoviesLiked.get(i).getTitle());
                    }
                    adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, stringMoviesWillWatch);
                    activity.lv.setAdapter(adapter);
                    activity.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            activity.goToMoviePage(position, 1);
                        }
                    });
            }





            return rootView;
        }

    }

    public void goToMoviePage(int id, int list){
        if(list == 0){
            ArrayList<String> stringMoviesWillWatch = new ArrayList<String>();

            // Création de la liste de strings
            for(int i=0;i<cachedMoviesWillWatch.size(); i++){
                stringMoviesWillWatch.add(cachedMoviesWillWatch.get(i).getTitle());
            }

            lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringMoviesWillWatch));
        } else {
            ArrayList<String> stringMoviesWillWatch = new ArrayList<String>();

            // Création de la liste de strings
            for(int i=0;i<cachedMoviesLiked.size(); i++){
                stringMoviesWillWatch.add(cachedMoviesLiked.get(i).getTitle());
            }

            lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringMoviesWillWatch));
        }
        String title = (String)lv.getItemAtPosition(id);
        Intent in = new Intent(this, DisplayMovie.class);
        for(Movie m : cachedMoviesWillWatch){
            if(m.getTitle().equals(title)){
                in.putExtra("movie_id", Integer.toString(m.getId()));
                startActivity(in);
                finish();
            }
        }
        for(Movie m : cachedMoviesLiked){
            if(m.getTitle().equals(title)){
                in.putExtra("movie_id", Integer.toString(m.getId()));
                startActivity(in);
                finish();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Set<String> mvl;

            mvl = getBaseContext().getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getStringSet("watched_movies", new HashSet<String>());

            return PlaceholderFragment.newInstance(position + 1, new HashSet<String>(mvl));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "À voir";
                case 1:
                    return "Vus aimés";
                case 2:
                    return "Vus nuls";
            }
            return null;
        }
    }
}
