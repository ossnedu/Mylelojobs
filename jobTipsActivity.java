package com.mylelojobs.android.mylelojobs;

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

public class jobTipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_tips);

        new jobtips().execute();



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
                //Log.v("RESULT",result);
                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
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


                        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();


                        /*cont.put(listTips.COL_ID,id);
                        cont.put(listTips.COL_CAT,cat);
                        cont.put(listTips.COL_TITLE,sub);

                        long confirm = db.insert(listTips.TABLE_NAME,null,cont);
                        if(confirm!=-1){
                            //Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_LONG).show();
                        }*/
                        //Cursor sd = db.query(listTips.TABLE_NAME,new String[]{listTips.COL_ID,listTips.COL_TIP_ID,listTips.COL_TITLE,listTips.COL_CAT},null,null,null,null,null,null);
                        //sd.moveToFirst();


                        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.jobtips_items,lay,false);
                        TextView txt = (TextView) v.findViewById(R.id.tipTitle);

                        Toast.makeText(getApplicationContext(),sub,Toast.LENGTH_LONG).show();

                        txt.setText(sub);

                        lay.addView(v);

                    }





                }catch (JSONException e){}

            }


        }
    }
}
