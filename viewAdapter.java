package com.mylelojobs.android.mylelojobs;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.mylelojobs.android.mylelojobs.dbcontract.*;

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
        TextView txtLogo = (TextView) view.findViewById(R.id.jobLogo);

        String jName = cursor.getString(cursor.getColumnIndex(rootJobs.COL_NAME));
        String jLog = cursor.getString(cursor.getColumnIndex(rootJobs.COL_LOGO));

        txtName.setText(jName);
        txtLogo.setText(jLog);
    }
}
