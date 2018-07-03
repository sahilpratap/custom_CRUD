package com.ultron.sahilpratap.listitems;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends ArrayAdapter {

    ArrayList<String> names;
    ArrayList<String> description;
    ArrayList<Integer> images;
    Activity activity;

    public CustomAdapter (Activity activity, ArrayList<String> names,ArrayList<String> description,ArrayList<Integer> images)

    {
        super(activity,R.layout.custom,names);
        this.activity = activity;
        this.names = names;
        this.description = description;
        this.images = images;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.custom,null);

        TextView tv = v.findViewById(R.id.textView);
        TextView tv2 = v.findViewById(R.id.textView2);
        CircleImageView img = v.findViewById(R.id.circleImageView);


        tv.setText(""+names.get(position));
        tv2.setText(""+description.get(position));
        img.setImageResource(images.get(position));

        return  v;
    }

}
