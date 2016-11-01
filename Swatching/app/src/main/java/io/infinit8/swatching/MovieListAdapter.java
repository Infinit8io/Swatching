package io.infinit8.swatching;

import android.content.ClipData;
import android.content.Context;
import android.media.ImageWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by drksnw on 11/1/16.
 */

public class MovieListAdapter extends ArrayAdapter<Movie> {

    public MovieListAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    public MovieListAdapter(Context context, int resource, List<Movie> items){
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.movie_list_item, null);
        }

        Movie m = getItem(position);

        if(m != null){
            TextView txtMovieTitle = (TextView)v.findViewById(R.id.movieTitle);
            ImageView iv = (ImageView)v.findViewById(R.id.poster);
            txtMovieTitle.setText(m.getTitle());
            iv.setImageBitmap(m.getPoster());
        }

        return v;
    }
}
