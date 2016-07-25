package com.mylelojobs.android.mylelojobs;

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
import android.view.LayoutInflater;
import android.view.Menu;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.mylelojobs.android.mylelojobs.dbcontract.*;
import com.squareup.picasso.Picasso;

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

public class DetailActivity extends AppCompatActivity {
    public String gid;
    public String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Bundle getBundle = null;
        Bundle getBundle = this.getIntent().getExtras();
        gid = getBundle.getString("id");
        new getJobDetail().execute();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class getJobDetail extends AsyncTask<String,String,String> {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonObject = null;

        @Override
        protected String doInBackground(String... params){

            final String STRING_BASE_URL = "http://www.mylelojobs.com";
            final String ID_PARAM = "id";
            final String PATH = "getDetail.php";
            Uri builtUri = Uri.parse(STRING_BASE_URL).buildUpon()
                    .appendPath(PATH)
                    .appendQueryParameter(ID_PARAM,gid).build();

            try {
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
            if(result!=null){
                final String DET_ID = "Id";
                final String DET_NAME = "Job_Name";
                final String DET_COMP_NAME = "Company_Name";
                final String DET_COM_PRO = "Company_Profile";
                final String DET_LAST = "Last_Entry_Date";
                final String DET_LOGO = "company_logo";
                final String DET_COM_WEB = "Company_Website";
                final String DET_SUB = "sub";
                final String SUB_ID = "Id";
                final String SUB_NAME = "Sub_Job_Name";

                try{
                    JSONObject getDetail = new JSONObject(result);
                    System.out.println(getDetail.length());
                    int id = getDetail.getInt(DET_ID);
                    String nm = getDetail.getString(DET_NAME);
                    String pr = getDetail.getString(DET_COM_PRO);
                    final String lg = getDetail.getString(DET_LOGO);
                    String dt = getDetail.getString(DET_LAST);
                    final String web = getDetail.getString(DET_COM_WEB);
                    JSONArray sb = getDetail.getJSONArray(DET_SUB);
                    String com_nm = getDetail.getString(DET_COMP_NAME);
                    String jSub = "";
                    //String jjSub = "";
                    //String ssid = "";
                    dbhelper helper = new dbhelper(getApplicationContext());
                    SQLiteDatabase db = helper.getReadableDatabase();
                    ContentValues cont = new ContentValues();
                    //cont.put(jobDetail.COL_NAME,nm);cont.put(jobDetail.COL_DETAILS,pr);cont.put(jobDetail.COL_COMPANY,com_nm);
                    //cont.put(jobDetail.COL_DATE,dt);cont.put(jobDetail.COL_LOGO,lg);cont.put(jobDetail.COL_WEB,web);
                    /*for (int i=0; i<sb.length(); i++){

                        JSONObject getSub = sb.getJSONObject(i);
                        int sid = getSub.getInt(SUB_ID);
                        String sn = getSub.getString(SUB_NAME);
                        if(!jSub.isEmpty()){
                            jSub = jSub+"@";
                        }
                        jSub = sid+","+sn;
                        jjSub = sn;
                        //ssid = sid;


                    }*/

                    /*long confirm = db.insert(jobDetail.TABLE_NAME,null,cont);
                    if(confirm!=-1){


                        Toast.makeText(getApplicationContext(),"Inerted",Toast.LENGTH_LONG);

                    }


                    Cursor sd = db.query(jobDetail.TABLE_NAME,new String[]{jobDetail.COL_ID,jobDetail.COL_NAME,jobDetail.COL_LOGO,jobDetail.COL_DETAILS,
                            jobDetail.COL_JOBS,jobDetail.COL_LOGO,jobDetail.COL_WEB,jobDetail.COL_DATE},null,null,null,null,null,null);
                    sd.moveToFirst();
                    final viewAdapter getAdapter = new viewAdapter(getApplicationContext(),sd);
                    ListView listView = (ListView) findViewById(R.id.detailView);
                    //listView.removeAllViewsInLayout();
                    listView.setAdapter(getAdapter);*/
                    Button apply = (Button) findViewById(R.id.apply);
                    apply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(getApplicationContext(),web,Toast.LENGTH_LONG).show();
                            //int sid = getSub.getInt(SUB_ID);
                            Uri uri = Uri.parse("http://www.mylelojobs.com"); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);


                        }
                    });


                    LinearLayout rLay= (LinearLayout) findViewById(R.id.detailView);
                    View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.show_job_items,rLay);
                    TextView mt = (TextView) v.findViewById(R.id.jobTitle);
                    TextView md = (TextView) v.findViewById(R.id.profile);
                    ImageView imgLogo = (ImageView) v.findViewById(R.id.logo);

                    if(lg.length()>4)
                    {
                        String logoClean = lg.substring(2);
                        StringBuilder str = new StringBuilder("http://www.mylelojobs.com").append(logoClean);
                        Picasso.with(getApplicationContext()).load(str.toString()).placeholder(R.drawable.logo3).error(R.drawable.logo3).into(imgLogo);
                    }



                    //String jsb = jjSub;
                    mt.setText(nm);
                    md.setText(pr);
                    //sj.setText(jsb);

                    for (int i=0; i<sb.length(); i++){

                        JSONObject getSub = sb.getJSONObject(i);
                        final int gid = getSub.getInt(SUB_ID);
                        String sn = getSub.getString(SUB_NAME);
                        //String sub_dt = getSub.getString(SUB_DESC);
                        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.subjobs_item,rLay,false);
                        TextView subTitle = (TextView)view.findViewById(R.id.subjobTitle);
                        subTitle.setId(gid);
                        subTitle.setText(sn);
                        subTitle.measure(0,0);
                        int height = subTitle.getMeasuredHeight();
                        //subTitle.setPadding(10,0,0,0);

                        subTitle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                //int sid = getSub.getInt(SUB_ID);
                                Intent intent = new Intent(DetailActivity.this, SubjobsActivity.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("id",""+gid);
                                intent.putExtras(bundle);
                                startActivity(intent);


                            }
                        });

                        rLay.addView(view,rLay.getWidth(),height);

                    }



                }catch (JSONException e){

                }
            }
        }

    }


}
