package com.mylelojobs.android.mylelojobs;

/**
 * Created by Digital Dreams on 14/07/2016.
 */
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * import static android.Manifest.permission.READ_CONTACTS;
 */


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private Button registerButton;
    //private EditText password;
    //private AutoCompleteTextView email;
    String emailMsgLogin;
    String passMsgLogin;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //loginButton = (Button) findViewById(R.id.email_sign_in_button);
        registerButton = (Button) findViewById(R.id.email_register_button);
        //password = (EditText) findViewById(R.id.password);
        //email = (AutoCompleteTextView) findViewById(R.id.email);


        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        //dbHelper dbhelp = new dbHelper(getApplicationContext());
        //SQLiteDatabase db = dbhelp.getWritableDatabase();

        //ContentValues cv = new ContentValues();

        /**cv.put(specCategory.COLUMN_ID, 1);
         //cv.put("primary key", 1);
         cv.put(specCategory.COLUMN_NAME, "Engineer");

         //db.insert(subJobs.TABLE_NAME);
         long check = db.insert("spec_category", null, cv);
         if(check!=-1)
         Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
         //db.query("sku_table", columns, "owner=?", new String[] { owner }, null, null, null);



         //db.query("sub_jobs", String[]columns, "Job_Description", null, null, null);
         // db.query("sub_jobs", FROM,select, null, null, null, null);
         String[] them = {specCategory.COLUMN_ID,specCategory.COLUMN_NAME};

         Cursor cd = db.query(specCategory.TABLE_NAME, them ,null,null,null,null,null,null);
         cd.moveToFirst();
         String name = cd.getString(cd.getColumnIndex(specCategory.COLUMN_ID));
         Toast.makeText(LoginActivity.this,name,Toast.LENGTH_LONG).show();
         **/


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    public void jobLocation(View v){
        Intent intent = new Intent(this,JobActivity.class);
        startActivity(intent);
    }


    public void login(View view) {
        AutoCompleteTextView emailTextLogin = (AutoCompleteTextView) findViewById(R.id.email);
        //make password text field object
        EditText passTextLogin = (EditText) findViewById(R.id.password);
        //make button object
        Button sendButton = (Button) findViewById(R.id.regButton);
        //When the send button is clicked
        //get data from form inputs
        emailMsgLogin = emailTextLogin.getText().toString();
        passMsgLogin = passTextLogin.getText().toString();
        System.out.println(emailMsgLogin);
        System.out.println(passMsgLogin);
        new uploadForm().execute();

    }



    private class uploadForm extends AsyncTask<String, String, String> {

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonObject = null;

        @Override
        protected String doInBackground(String... params) {
            //URL url = new URL("http://www.mylelojobs.com/processSignups.php?pw="+passMsg+"&rm="+emailMsg+"");
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestMethod("GET");
            //conn.setDoInput(true);
            //conn.setDoOutput(true);
            // conn.connect();
            final String STRING_BASE_URL = "http://www.mylelojobs.com";
            final String EMAIL_PARAM = "user";
            final String PASS_PARAM = "pass";
            final String PATH = "signin.php";
            Uri builtUri = Uri.parse(STRING_BASE_URL).buildUpon()
                    .appendPath(PATH)
                    .appendQueryParameter(EMAIL_PARAM, emailMsgLogin)
                    .appendQueryParameter(PASS_PARAM, passMsgLogin).build();

            try {
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
                    Toast.makeText(getApplicationContext(), "I just got out with nothing", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "I read nothing", Toast.LENGTH_LONG).show();
                    return null;
                }
                jsonObject = buffer.toString();
                System.out.println(buffer.toString());
            } catch (IOException e) {

            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                System.out.println(result);
                //Toast.makeText(getApplicationContext(), "hacked!", Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                try{
                    final String GET_MSG = "message";
                    JSONObject getJson = new JSONObject(result);
                    String msg = getJson.getString(GET_MSG);
                    //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    if(msg.equals("allow")){
                        jobLocation(null);
                    }else{
                        Toast.makeText(getApplicationContext(),"wrong username or password",Toast.LENGTH_LONG).show();
                        Intent stayback = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(stayback);

                    }
                }catch (JSONException e){

                }
                result = result.toString();

                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                //String r = "allow";



                /**Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_VIEW);
                 intent.addCategory(Intent.CATEGORY_BROWSABLE);
                 intent.setData(Uri.parse(result));
                 startActivity(intent);**/

            }
        }
    }


}
