package com.mylelojobs.android.mylelojobs;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.mylelojobs.android.mylelojobs.dbcontract.*;
import com.squareup.picasso.Picasso;

public class jobTipsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_tips);

        new jobtips().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.job, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Redirect direct = new Redirect();
        Intent activity = direct.onClick(this,item);
        startActivity(activity);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class  jobtips extends AsyncTask<String,String,String>{
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonObject = null;

        @Override
        protected String doInBackground(String... params) {

            final String STRING_BASE_URL = "http://www.mylelojobs.com";
            final String TIP_PARAM = "job_tip";
            final String PATH = "getTips.php";
            Uri builtUri = Uri.parse(STRING_BASE_URL).buildUpon()
                    .appendPath(PATH).build();

            try{
                URL url = new URL(builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                // Read the input stream into a String
                InputStream inputStream = conn.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    //System.out.println("I read nothing");
                    return null;
                }
                jsonObject = buffer.toString();
                System.out.println(jsonObject);


            }catch (IOException e){

            }


            return jsonObject;
        }

        @Override
        protected void onPostExecute(String result){
            if(result != null){

                final String ID = "Id";
                final String SUBJ = "Subject";
                final String CAT_NAME = "category_name";

                try{
                    JSONArray getTipsArr = new JSONArray(result);
                    dbhelper helper = new dbhelper(getApplicationContext());
                    SQLiteDatabase db = helper.getReadableDatabase();
                    ContentValues cont = new ContentValues();

                    LinearLayout lay = (LinearLayout) findViewById(R.id.jobTipsView);

                    for(int i = 0; i < getTipsArr.length(); i++){

                        JSONObject getTips = getTipsArr.getJSONObject(i);
                        int id = getTips.getInt(ID);
                        String sub = getTips.getString(SUBJ);
                        String cat = getTips.getString(CAT_NAME);

                        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.jobtips_items,lay,false);
                        TextView txt = (TextView) v.findViewById(R.id.tipTitle);

                        //Toast.makeText(getApplicationContext(),sub,Toast.LENGTH_LONG).show();

                        txt.setText(sub);

                        lay.addView(v);

                    }





                }catch (JSONException e){}

            }


        }
    }
}
