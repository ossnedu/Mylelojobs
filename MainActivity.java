package com.mylelojobs.android.mylelojobs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button loginPg;

    String emailMsg;
    String passMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginPg = (Button) findViewById(R.id.login_pg);

        loginPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dbhelper helper = new dbhelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
       /* cv.put("id",1);cv.put("jobName","Jobs at Michelin Nig ltd");cv.put("companyName","Michelin Nig Ltd");
        cv.put("companyProfile","We are a multinational company");cv.put("companyEmail","michelin@gmail.com");
        cv.put("companyWebsite","www.michelinng.com");cv.put("industry","Agricultural");
        long confirm = db.insert("rootJobs",null,cv);
        if(confirm!=-1){
            Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_LONG).show();
        }

        Cursor cd = db.query("rootJobs",new String[]{"id","jobName","industry","companyProfile","companyEmail"},null,null,null,null,null,null);
        cd.moveToFirst();
        String name = cd.getString(cd.getColumnIndex("jobName"));
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();*/
        //System.out.println(name);
        //Comment newComment = cursorToComment(cd);
        //cd.close();

    }

   // @Override
    public void jobLocation(View v){
        Intent intent = new Intent(this,JobActivity.class);
        startActivity(intent);
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
    public void register(View view)
    {
        EditText emailText = (EditText) findViewById(R.id.email);
        //make password text field object
        EditText passText = (EditText) findViewById(R.id.password);
        //make button object
        Button sendButton = (Button) findViewById(R.id.regButton);
        //When the send button is clicked
        //get data from form inputs
        emailMsg =  emailText.getText().toString();
        passMsg =  passText.getText().toString();
        System.out.println(emailMsg);
        System.out.println(passMsg);
        new uploadForm().execute();

    }
    private class uploadForm extends AsyncTask<String,String,String> {

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonObject = null;
        @Override
        protected String doInBackground(String... params){
            //URL url = new URL("http://www.mylelojobs.com/processSignups.php?pw="+passMsg+"&rm="+emailMsg+"");
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestMethod("GET");
            //conn.setDoInput(true);
            //conn.setDoOutput(true);
            // conn.connect();
            final String STRING_BASE_URL = "http://www.mylelojobs.com";
            final String EMAIL_PARAM = "rm";
            final String PASS_PARAM = "pw";
            final String PATH = "processSignups.php";
            Uri builtUri = Uri.parse(STRING_BASE_URL).buildUpon()
                        .appendPath(PATH)
                        .appendQueryParameter(EMAIL_PARAM,emailMsg)
                        .appendQueryParameter(PASS_PARAM,passMsg).build();

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
                    System.out.println("I just got out with nothing");
                    return null;
                }
                System.out.println("I something o");
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
                    System.out.println("I read nothing");
                    return null;
                }
                jsonObject = buffer.toString();
                System.out.println(buffer.toString());
            }catch(IOException e)
            {

            }
            return jsonObject;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result!=null){
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                jobLocation(null);
            }
        }
    }
}
