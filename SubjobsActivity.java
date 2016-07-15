package com.mylelojobs.android.mylelojobs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubjobsActivity extends AppCompatActivity {

    public String gid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjobs);
        Bundle getBundle = this.getIntent().getExtras();
        gid = getBundle.getString("id");
        new getSubjobs().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    private class getSubjobs extends AsyncTask<String,String,String> {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonObject = null;

        @Override
        protected String doInBackground(String... params){

            final String STRING_BASE_URL = "http://www.mylelojobs.com";
            final String ID_PARAM = "id";
            final String PATH = "getSubjobs.php";
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
                //System.out.println(jsonObject);

            }catch (IOException e){

            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(String result){
            if(result!=null){

                final String SUB_ID = "id";
                final String SUB_NAME = "sjn";
               // final String SUB_DESC = "jdesc";
                final String SUB_LOGO = "lg";
               // final String SUB_COURSE = "crs";
                final String SUB_GRADE = "deg";
                final String SUB_TYPE = "degt";
                final String SUB_LOC = "st";
                final String SUB_EXP = "exp";

                try{
                    JSONObject getDetail = new JSONObject(result);
                    System.out.println("checksesi "+getDetail);
                    int id = getDetail.getInt(SUB_ID);
                    String nm = getDetail.getString(SUB_NAME);
                    //String pr = getDetail.getString(SUB_DESC);
                    String lg = getDetail.getString(SUB_LOGO);
                    String dt = getDetail.getString(SUB_EXP);
                    //String course = getDetail.getString(SUB_COURSE);
                    String deg = getDetail.getString(SUB_GRADE);
                    String typ = getDetail.getString(SUB_TYPE);
                    String loc = getDetail.getString(SUB_LOC);
                    //String jSub = "";
                   /* dbhelper helper = new dbhelper(getApplicationContext());
                    SQLiteDatabase db = helper.getReadableDatabase();
                    ContentValues cont = new ContentValues();*/
                    //cont.put(jobDetail.COL_NAME,nm);cont.put(jobDetail.COL_DETAILS,pr);cont.put(jobDetail.COL_COMPANY,com_nm);
                    //cont.put(jobDetail.COL_DATE,dt);cont.put(jobDetail.COL_LOGO,lg);cont.put(jobDetail.COL_WEB,web);
                    //for (int i=0; i<sb.length(); i++){

                     //   JSONObject getSub = sb.getJSONObject(i);
                     //   int sid = getSub.getInt(SUB_ID);
                     //   String sn = getSub.getString(SUB_NAME);
                     //   if(!jSub.isEmpty()){
                     //       jSub = jSub+"@";
                      //  }
                      //  jSub = sid+","+sn;

                    //}*/

                    /*long confirm = db.insert(jobDetail.TABLE_NAME,null,cont);
                    if(confirm!=-1){
                        Toast.makeText(getApplicationContext(),"Inerted",Toast.LENGTH_LONG);
                    }*/

                    //Cursor sd = db.query(jobDetail.TABLE_NAME,new String[]{jobDetail.COL_ID,jobDetail.COL_NAME,jobDetail.COL_LOGO,jobDetail.COL_DETAILS,
                    //jobDetail.COL_JOBS,jobDetail.COL_LOGO,jobDetail.COL_WEB,jobDetail.COL_DATE},null,null,null,null,null,null);
                    //sd.moveToFirst();
                    //final viewAdapter getAdapter = new viewAdapter(getApplicationContext(),sd);
                    // ListView listView = (ListView) findViewById(R.id.detailView);
                    //listView.setAdapter(getAdapter);
                    LinearLayout rLay= (LinearLayout) findViewById(R.id.subjobView);
                    View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.main_subjob_view,rLay);
                    TextView mTxt = (TextView) v.findViewById(R.id.subName);
                    TextView md = (TextView) v.findViewById(R.id.subDesc);
                    TextView dty = (TextView) v.findViewById(R.id.degType);
                    TextView cse = (TextView)v.findViewById(R.id.course);
                    TextView grd = (TextView)v.findViewById(R.id.degGrade);
                    TextView exp = (TextView)v.findViewById(R.id.jobExp);
                    TextView lc = (TextView)v.findViewById(R.id.location);

                    //TextView item = (TextView)findViewById(R.id.detailView);
                    //View ch = getLayoutInflater().inflate(R.layout.show_job_items,null);
                    // item.addView(ch);
                    mTxt.setText(nm);
                    //String jnm = nm; //String glg = lg; String gdt = dt; String gcs = course;
                    //String jdt = pr; String gdeg = deg; String gtyp = typ; String gloc = loc;
                     //dty.setText(gtyp); cse.setText(gcs); grd.setText(gdeg);
                    //md.setText(jdt); exp.setText(gdt); lc.setText(gloc);
                    //View view;

                    //subTitle.setId(1000+i);
                    //subTitle.setText(sn);
                   // rLay.addView(view);

                    //System.out.println(jdt+", "+jnm);

                }catch (JSONException e){

                }
            }
        }

    }
}
