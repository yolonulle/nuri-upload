/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.inhwa.nan05.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.inhwa.nan05.R;
import com.inhwa.nan05.app.AppConfig;
import com.inhwa.nan05.app.AppController;
import com.inhwa.nan05.helper.SQLiteHandler;
import com.inhwa.nan05.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterArtistActivity extends Activity {

    private static final String TAG = RegisterArtistActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private Button btnVerify; //버튼누르면 인증 이메일 보냄

    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputIntroduction;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_register);

        inputFullName= (EditText) findViewById(R.id.name);
        inputIntroduction= (EditText) findViewById(R.id.artist_introduction);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterArtistActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // verification Button Click event
        btnVerify.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //verfication override
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String introduction = inputIntroduction.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerArtist(name, email, password, introduction);
                    Toast.makeText(getApplicationContext(), "인증메일을 발송하였습니다. 인증을 완료해야 회원가입이 완료됩니다.", Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });



        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String email = inputEmail.getText().toString().trim();
                registerArtist2(email);
              /*  HashMap<String, String> user = db.getUserDetails();
                String verify = user.get("verify");

                if(verify == "0"){
                    Toast.makeText(getApplicationContext(),
                            "인증메일을 확인해주세요", Toast.LENGTH_LONG).show();

                }else {
                    String email = inputEmail.getText().toString().trim();
                    registerArtist2(email);

                    // Launch login activity
                    Intent intent = new Intent(
                            RegisterArtistActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
*/

            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    //registerArtist start, take the data from MYSQL and store them to the sqlite
    private void registerArtist2(final String email){
        String tag_string_req = "req_verify";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER_ARTIST2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        String verify = user.getString("verify");

                        db.addUser(name, email, uid, created_at, verify);
                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterArtistActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();



                        // Inserting row in users table
                   /*     if(verify.matches("0")){
                            Toast.makeText(getApplicationContext(), "check your e-mail.", Toast.LENGTH_LONG)
                                    .show();
                        }else if(verify.matches("1")){
                            db.addUser(name, email, uid, created_at, verify);
                            // Launch login activity
                            Intent intent = new Intent(
                                    RegisterArtistActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        }*/



                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { //error

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //     hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);

                //데이터넘기기

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    } //verify end


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */

    //verify & register to MYSQL DB - registerArtist2
    private void registerArtist(final String name, final String email,
                                final String password, final String introduction) {
        // Tag used to cancel the request
        String tag_string_req = "req_register_artist";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER_ARTIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register_artist Response: " + response.toString());
                hideDialog();
/*
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        String verify = user.getString("verify");


                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at, verify);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("introduction", introduction);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
