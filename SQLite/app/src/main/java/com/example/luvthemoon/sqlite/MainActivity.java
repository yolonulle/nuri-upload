package com.example.luvthemoon.sqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    TextView addrecord;
    EditText stu_num, grade, name;
    Button save;
    View dialogView;
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        stu_num = (EditText) findViewById(R.id.studentnum);
        grade = (EditText) findViewById(R.id.grade);
        name = (EditText) findViewById(R.id.name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dialogView = (View) View.inflate(MainActivity.this,
                R.layout.dialog1, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(
                MainActivity.this);
        dlg.setTitle("Add Record");
        dlg.setView(dialogView);
        dlg.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO studentTBL VALUES ( '"
                                + stu_num.getText().toString() + "' , "
                                + grade.getText().toString() + "' , "
                                + name.getText().toString() + ");");
                        sqlDB.close();
                    }
                });
        myHelper = new myDBHelper(this);
        dlg.show();
        return false;
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE studentTBL (id char(10), 학번 int, 학년 int, 이름 char(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {


    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}
