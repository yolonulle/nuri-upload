package com.inhwa.nan05.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inhwa_ on 2017-05-17.
 */

public class UploadPerformanceActivity extends Activity {

    private SQLiteHandler db;
    private SessionManager session;

    public static String s_genre = "";
    public static String s_region = "";
    public static String s_email;
    private static final String TAG = UploadPerformanceActivity.class.getSimpleName();


    private EditText edtSetTitle;

    private ImageView poster_view;
 //   private ImageButton btnSetImage;


    private ImageButton btnSetImage;
    private Button btnSetDate;
    private Button btnSetTime;
    private Spinner spinnerSetGenre;
    private Spinner spinnerSetRegion;

    private EditText edtSetLocation;
    private EditText edtKeyword;

    private EditText edtIntroPerformance;

    private Button btnUpload;
    private Button btnCancel;

   // private String Title;

    final int DIALOG_DATE = 1;
    final int DIALOG_TIME = 2;

    DatePickerDialog dpd;

   int s_year, s_month, s_day, s_hour, s_min; //selected play day and time
   int year, month, day, hour, min; //to initialize the date


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();
        s_email = user.get("email").toString();

       // Toast.makeText(getApplicationContext(), "이메일 : " + s_email, Toast.LENGTH_SHORT).show();
        java.util.Calendar cal = java.util.Calendar.getInstance();

        year = cal.get ( cal.YEAR );
        month = cal.get ( cal.MONTH );
        day = cal.get ( cal.DATE ) ;

        hour = cal.get ( cal.HOUR_OF_DAY ) ;
        min = cal.get ( cal.MINUTE );

        setContentView(R.layout.activity_upload_performance);

        edtSetTitle = (EditText) findViewById(R.id.edtSetTitle);

        poster_view = (ImageView) findViewById(R.id.btnSetImage);
        btnSetImage = (ImageButton) findViewById(R.id.imgbtn);
        btnSetDate = (Button) findViewById(R.id.btnSetDate);
        btnSetTime = (Button) findViewById(R.id.btnSetTime);
        spinnerSetGenre = (Spinner) findViewById(R.id.spinnerSetGenre);
        spinnerSetRegion = (Spinner) findViewById(R.id.spinnerSetRegion);

        edtSetLocation = (EditText) findViewById(R.id.edtSetLocation);
        edtKeyword = (EditText) findViewById(R.id.edtKeyword);
        edtIntroPerformance = (EditText) findViewById(R.id.edtIntroPerformance);

        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        //장르선택
        spinnerSetGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_genre = spinnerSetGenre.getSelectedItem().toString();
             //   Toast.makeText(getApplicationContext(), "장르 : " + s_genre, Toast.LENGTH_SHORT).show();
               /* if(parent.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "장르를 선택해줍쇼", Toast.LENGTH_SHORT).show();
                }else {
                    s_genre = spinnerSetGenre.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(), "장르 : " + s_genre, Toast.LENGTH_SHORT).show();
                }*/
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerSetRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_region = spinnerSetRegion.getSelectedItem().toString();
               // Toast.makeText(getApplicationContext(), "지역 : " + s_region, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        btnSetImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "공연 홍보 이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
            }

        });

        btnSetDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "공연 날짜를 선택하세요.", Toast.LENGTH_SHORT).show();
                showDialog(DIALOG_DATE);

            }

        });


        btnSetTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "공연 시간 선택.", Toast.LENGTH_LONG).show();
                showDialog(DIALOG_TIME);
            }

        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //공연 업로드
              //  Title = edtSetTitle.getText().toString();

                String title = edtSetTitle.getText().toString();
                String date = btnSetDate.getText().toString();
                String time = btnSetTime.getText().toString();
                String genre = s_genre;
                String region = s_region;
                String location = edtSetLocation.getText().toString();
                String keyword = edtKeyword.getText().toString();
                String content = edtIntroPerformance.getText().toString();
                String email = s_email;


               // String
                if (title.matches("")||date.matches("날짜 선택")||time.matches("시간 선택")||location.matches("")||content.matches("")) {
                    Toast.makeText(getApplicationContext(), "모든 항목을 입력하세요", Toast.LENGTH_LONG).show();
                } else {
                    uploadPerformance(title, date, time, genre, region, location, content, email);
                    Toast.makeText(getApplicationContext(), "업로드.", Toast.LENGTH_LONG).show();
                    finish();

                }
            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

    } //end of oncreate

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DATE:
                dpd = new DatePickerDialog(UploadPerformanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Toast.makeText(getApplicationContext(),
                                        year + "년 " + (month + 1) + "월 " + dayOfMonth + "일을 선택했습니다",
                                        Toast.LENGTH_SHORT).show();
                                s_year = year;
                                s_month = month + 1;
                                s_day = dayOfMonth;

                                btnSetDate.setText(s_year + "년 " + s_month + "월 " + s_day + " 일" );

                            }
                        }, year,month,day); //오늘 날짜로 초기화 하고싶다
                return dpd;

            case DIALOG_TIME:
                TimePickerDialog tpd = new TimePickerDialog(UploadPerformanceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Toast.makeText(getApplicationContext(),
                                        hourOfDay + "시 " + minute + "분을 선택했습니다",
                                        Toast.LENGTH_SHORT).show();
                                s_hour = hourOfDay;
                                s_min = minute;

                                btnSetTime.setText(s_hour + " 시" + s_min + " 분");

                            }
                        }, hour, min, false);
                return tpd;
        }
        return super.onCreateDialog(id);
    }

    private void uploadPerformance(final String title, final String date, final String time, final String genre, final String region,
                                   final String location, final String content, final String email) {

        String tag_string_req = "req_uploadPerformance";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPLOAD_PERFORMANCE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                // hideDialog();

            /*    try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                   if (!error) {

                       Toast.makeText(getApplicationContext(),title, Toast.LENGTH_LONG).show();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }*/

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
                params.put("title", title);
                params.put("date", date);
                params.put("time", time);
                params.put("genre", genre);
                params.put("region", region);
                params.put("location", location);
                params.put("content", content);
                params.put("email", email);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


