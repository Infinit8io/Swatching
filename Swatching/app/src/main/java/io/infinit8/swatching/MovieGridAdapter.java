package io.infinit8.swatching;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dom on 29.11.2016.
 */

public class MovieGridAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private int layoutResourceId;
    private boolean selected;
    private ArrayList<Movie> data = new ArrayList<Movie>();

    public MovieGridAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.selected = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Movie item = data.get(position);

        // Set movie image item infos
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageBitmap(item.getPoster());


        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}