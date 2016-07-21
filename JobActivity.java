package com.mylelojobs.android.mylelojobs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.mylelojobs.android.mylelojobs.dbcontract.*;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class JobActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        new getJobs().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //"ewu".substring(1);
        //String yep = "ewu".substring(1);
        //Toast.makeText(getApplicationContext(),yep,Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    private class getJobs extends AsyncTask<String,Void,String> {

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonObject = null;

        private ArrayAdapter<String> jobAdapter;
        @Override
        protected String doInBackground(String... params){
            //URL url = new URL("http://www.mylelojobs.com/processSignups.php?pw="+passMsg+"&rm="+emailMsg+"");
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestMethod("GET");
            //conn.setDoInput(true);
            //conn.setDoOutput(true);

            // conn.connect();
            int limit = 10;
            int page = 1;
            String para = "im,jn,dt,pf";
            final String STRING_BASE_URL = "http://www.mylelojobs.com";
            final String LIMIT_PARAM = "limits";
            final String PAGE_PARAM = "pages";
            final String T_PARAM = "t";
            final String PATH = "processAjax2.php";
            Uri builtUri = Uri.parse(STRING_BASE_URL).buildUpon()
                    .appendPath(PATH)
                    .appendQueryParameter(LIMIT_PARAM,Integer.toString(limit))
                    .appendQueryParameter(T_PARAM,para)
                    .appendQueryParameter(PAGE_PARAM,Integer.toString(page)).build();

            try{
                URL url = new URL(builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                //conn.getOutputStream().write( ("name=" + name).getBytes());
                // Read the input stream into a String
                InputStream inputStream = conn.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    // Nothing to do.
                    //System.out.println("I just got out with nothing");
                    return null;
                }
                //System.out.println("I something o");
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
                //System.out.println(jsonObject);
            }catch(IOException e)
            {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result!=null){
                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                //System.out.println(result);
                final String JOB_ID = "id";
                final String JOB_LOGO = "im";
                final String JOB_NAME = "jn";
                final String JOB_SUB = "sub";
                final String SUB_ID = "Id";
                final String SUB_NAME = "Sub_Job_Name";
                try{
                    JSONArray getJsonObj = new JSONArray(result);
                    JSONArray jobArray = getJsonObj;
                    dbhelper helper = new dbhelper(getApplicationContext());
                    SQLiteDatabase db = helper.getReadableDatabase();
                    ContentValues cv = new ContentValues();
                    //System.out.println(jobArray.length());
                    for (int i=0; i<jobArray.length(); i++){
                        //System.out.println("jobs"+jobArray.getJSONObject(i));
                        int id;
                        //String logo;
                        String jn;
                        JSONArray sub;
                        String jSub="";
                        int sid;
                        String sn;
                        JSONObject getChild = jobArray.getJSONObject(i);
                        id = getChild.getInt(JOB_ID);
                        String logo = getChild.getString(JOB_LOGO);
                        jn = getChild.getString(JOB_NAME);
                        sub = getChild.getJSONArray(JOB_SUB);

                        if(logo.length() != 0) {
                            final String STRING_BASE_URL = "http://www.mylelojobs.com";

                            //StringBuffer lg = new StringBuffer(logo);
                            String logoClean = logo.substring(2);
                            StringBuilder str = new StringBuilder(STRING_BASE_URL);

                            StringBuilder y = str.append(logoClean);
                            String yy = y.toString();
                            //Toast.makeText(getApplicationContext(),yy,Toast.LENGTH_LONG).show();

                            /*try {
                                //http://www.mylelojobs.com/company_logo/19802virus.jpg
                                URL url = new URL(yy);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                                //conn = (HttpURLConnection) url.openConnection();
                                //connection.setRequestMethod("GET");
                                //conn.connect();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(input);

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                                byte[] image1 = stream.toByteArray();
                                cv.put(rootJobs.COL_LOGO,image1);
                                //imgLogo.setImageBitmap(map);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                            //Bitmap b=BitmapFactory.decodeResource(getResources(), yy);
                            //ByteArrayOutputStream bos=new ByteArrayOutputStream();
                            //b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            //img=bos.toByteArray();


                        //System.out.println(jn);
                        //Toast.makeText(getApplicationContext(),y,Toast.LENGTH_LONG).show();
                        }

                        for(int j=0; j<sub.length(); j++){

                            JSONObject getSub = sub.getJSONObject(j);
                            sid = getSub.getInt(SUB_ID);
                            sn = getSub.getString(SUB_NAME);
                            if(!jSub.isEmpty()){
                                jSub = jSub+"@";
                            }
                            jSub = sid+","+sn;

                        }
                        //System.out.println("Am getting here");
                        cv.put(rootJobs.COL_JOB_ID,id);
                        cv.put(rootJobs.COL_NAME,jn);
                        cv.put(rootJobs.COL_LOGO,logo);
                        //cv.put(rootJobs.COL_JOBS,jn);
                        //cv.put(rootJobs.COL_JOBS,jSub);
                        long confirm = db.insert(rootJobs.TABLE_NAME,null,cv);
                        if(confirm!=-1){
                            //Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_LONG).show();
                        }
                        // Toast.makeText(getApplicationContext(),jobArray.length(),Toast.LENGTH_LONG);
                        Cursor sd = db.query(rootJobs.TABLE_NAME,new String[]{rootJobs.COL_ID,rootJobs.COL_JOB_ID,rootJobs.COL_LOGO,rootJobs.COL_NAME,
                                rootJobs.COL_JOBS},null,null,null,null,null,null);
                        sd.moveToFirst();

                        final viewAdapter getAdapter = new viewAdapter(getApplicationContext(),sd);
                        ListView listview = (ListView) findViewById(R.id.listView);
                        listview.setAdapter(getAdapter);
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Cursor cur = (Cursor) getAdapter.getItem(position);
                                cur.moveToPosition(position);
                                String ids = cur.getString(cur.getColumnIndexOrThrow(rootJobs.COL_JOB_ID));
                                Intent intent = new Intent(JobActivity.this, DetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString ("id", ids);
                                intent.putExtras(bundle);

                                //ListView listView = (ListView) findViewById(R.id.detailView);
                                //listView.removeAllViewsInLayout();

                                startActivity(intent);
                            }
                        });
                        /*String jName = sd.getString(sd.getColumnIndex(rootJobs.COL_NAME));
                        int jid = sd.getInt(sd.getColumnIndex(rootJobs.COL_ID));
                        int mid = sd.getInt(sd.getColumnIndex(rootJobs.COL_JOB_ID));
                        String gSubJobs = sd.getString(sd.getColumnIndex(rootJobs.COL_JOBS));
                        String jLogo = sd.getString(sd.getColumnIndex(rootJobs.COL_LOGO));
                        Toast.makeText(getApplicationContext(), jName, Toast.LENGTH_SHORT).show();
                        System.out.println(jid+", "+mid+", "+jLogo+", "+gSubJobs+", "+jName);*/
                    }

                }catch (JSONException e){
                    // System.out.println("I am not seeing anything");
                    // e.printStackTrace();
                }
            }
        }
    }

}
