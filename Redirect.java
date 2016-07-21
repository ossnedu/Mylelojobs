package com.mylelojobs.android.mylelojobs;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

public class Redirect {
    Intent activity;
    public Intent onClick(Context context, MenuItem item)
    {

        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            activity = new Intent(context,jobTipsActivity.class);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            //activity = new Intent(context,jobTipsActivity.class);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }
        return activity;
    }

}
