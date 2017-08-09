package com.example.luvthemoon.test;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inhwa_ on 2017-05-17.
 */

public class UploadPerformanceActivity extends Activity {

    private static final String TAG = UploadPerformanceActivity.class.getSimpleName();

    private EditText edtSetTitle;

    private ImageView poster_view;
    private ImageButton btnSetImage;
    private Button btnSetDate;
    private Button btnSetTime;
    private Spinner spinnerSetGenre;

    private EditText edtSetLocation;
    private EditText edtKeyword;

    private EditText edtIntroPerformance;

    private Button btnUpload;
    private Button btnCancel;

    private String Title;

    final int DIALOG_DATE = 1;
    final int DIALOG_TIME = 2;

    DatePickerDialog dpd;

    int s_year, s_month, s_day, t_year;

    Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSetTitle = (EditText) findViewById(R.id.edtSetTitle);

        poster_view = (ImageView) findViewById(R.id.btnSetImage);
        btnSetImage = (ImageButton) findViewById(R.id.imgbtn);

        btnSetDate = (Button) findViewById(R.id.btnSetDate);
        btnSetTime = (Button) findViewById(R.id.btnSetTime);
        spinnerSetGenre = (Spinner) findViewById(R.id.spinnerSetGenre);

        edtSetLocation = (EditText) findViewById(R.id.edtSetLocation);
        edtKeyword = (EditText) findViewById(R.id.edtKeyword);
        edtIntroPerformance = (EditText) findViewById(R.id.edtIntroPerformance);

        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        //장르선택
        spinnerSetGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    Toast.makeText(getApplicationContext(), "장르 : " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnSetImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                albumAction();
                //Toast.makeText(getApplicationContext(), "공연 홍보 이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
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
            public void onClick(View v) {
                Title = edtSetTitle.getText().toString();

                String title = edtSetTitle.getText().toString();
                if (Title.matches("")) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    uploadPerformance(title);
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

    private void albumAction() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        albumIntent.setType("image/*");
        albumIntent.putExtra("crop", "false");
        //albumIntent.putExtra("aspectX", 1);
        //albumIntent.putExtra("aspectY", 1);
        albumIntent.putExtra("outputX", 150);
        albumIntent.putExtra("outputY", 205);
        albumIntent.putExtra("return-data", false);

        startActivityForResult(albumIntent, 2);
    }

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

                            }
                        }, 2017,6,25); //오늘 날짜로 초기화 하고싶다
                return dpd;

            case DIALOG_TIME:
                TimePickerDialog tpd = new TimePickerDialog(UploadPerformanceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Toast.makeText(getApplicationContext(),
                                        hourOfDay + "시 " + minute + "분을 선택했습니다",
                                        Toast.LENGTH_SHORT).show();

                                btnSetTime.setText(hourOfDay + "시" + minute + "분");

                            }
                        }, 7, 30, false);
                return tpd;
        }
        return super.onCreateDialog(id);
    }

    private void uploadPerformance(final String title) {

        String tag_string_req = "req_uploadPerformance";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPLOAD_PERFORMANCE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
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
                params.put("title", title);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent      data) {
        /**if (resultCode != RESULT_OK) {
         return;
         }**/
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            btnSetImage.setImageBitmap(image);
        }
    }

}