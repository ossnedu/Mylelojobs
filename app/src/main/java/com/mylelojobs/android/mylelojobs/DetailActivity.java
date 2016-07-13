package com.mylelojobs.android.mylelojobs;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    public String gid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Bundle getBundle = null;
        Bundle getBundle = this.getIntent().getExtras();
        gid = getBundle.getString("id");
        System.out.println("I getting to oncreate");
        new getJobDetail().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    private class getJobDetail extends AsyncTask<String,String,String> {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonObject = null;

        @Override
        protected String doInBackground(String... params){
            System.out.println("i getting this "+gid);
            final String STRING_BASE_URL = "http://www.mylelojobs.com";
            final String ID_PARAM = "id";
            final String PATH = "getDetail.php";
            Uri builtUri = Uri.parse(STRING_BASE_URL).buildUpon()
                    .appendPath(PATH)
                    .appendQueryParameter(ID_PARAM,gid).build();
            System.out.println("I getting here o");
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
                System.out.println(jsonObject);

            }catch (IOException e){

            }
            return jsonObject;
        }

    }
}
