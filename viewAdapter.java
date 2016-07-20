package com.mylelojobs.android.mylelojobs;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mylelojobs.android.mylelojobs.dbcontract.*;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pegasus Amazing on 7/11/2016.
 */
public class viewAdapter extends CursorAdapter{

    public viewAdapter(Context context, Cursor c){
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
    }

    @Override
    public void bindView(View view,Context context,Cursor cursor){
        TextView txtName = (TextView) view.findViewById(R.id.jobName);
        ImageView imgLogo = (ImageView) view.findViewById(R.id.logo);

        String jName = cursor.getString(cursor.getColumnIndex(rootJobs.COL_NAME));
        String jLog = cursor.getString(cursor.getColumnIndex(rootJobs.COL_LOGO));

        if(jLog.length() >4) {
            final String STRING_BASE_URL = "http://www.mylelojobs.com";

            String logoClean = jLog.substring(2);
            StringBuilder str = new StringBuilder(STRING_BASE_URL).append(logoClean);

            Picasso.with(context).load(str.toString()).into(imgLogo);

        }

        txtName.setText(jName);

    }
}
