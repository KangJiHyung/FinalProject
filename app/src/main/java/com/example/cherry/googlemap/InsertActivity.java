package com.example.cherry.googlemap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;


public class InsertActivity extends ActionBarActivity {

    // Datavase 관련 객체들
    SQLiteDatabase db;
    String dbName = "idList.db"; // name of Database;
    String tableName = "idListTable"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;

    EditText mEtext;

    String resultState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // // Database 생성 및 열기
        db = openOrCreateDatabase(dbName, dbMode, null);
        // 테이블 생성
        createTable();

        mEtext = (EditText) findViewById(R.id.EditText);

        ////////////////////////// 라디오 버튼 ////////////////////////////
        // 공부
        findViewById(R.id.rBT1).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buttonCheck(v);
            }
        });
        // 놀기
        findViewById(R.id.rBT2).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buttonCheck(v);
            }
        });
        // 먹기
        findViewById(R.id.rBT3).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buttonCheck(v);
            }
        });
        // 취미생활
        findViewById(R.id.rBT4).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buttonCheck(v);
            }
        });
        // 기타
        findViewById(R.id.rBT5).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buttonCheck(v);
            }
        });

        ///////////////////////////// 버튼 ///////////////////////////////
        Button btSave = (Button) findViewById(R.id.bt_save);
        Button btCancel = (Button) findViewById(R.id.bt_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_save:    // 저장
                        //insertData(resultState);    // 상태 저장
                        String text = mEtext.getText().toString();  // editText에 적은 내용 가져오기
                        String addText = resultState + " ) " + text;  // 상태와 메세지를 같이 저장하기 위해
                        insertData(addText);   // 내용 저장
                        Toast.makeText(InsertActivity.this, "저장되었습니다.", Toast.LENGTH_LONG).show();
                        finish();
                        break;

                    case R.id.bt_cancel:     // 취소
                        finish();
                        break;
                }
            }
        };

        btSave.setOnClickListener(listener);
        btCancel.setOnClickListener(listener);

    }

    public void buttonCheck(View v) {
        RadioButton ra = (RadioButton) v;
        if (ra.isChecked()) {
            resultState = ra.getText().toString();
        }
    }

    // Table 생성
    public void createTable() {
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "name text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }

    // Data 추가
    public void insertData(String name) {
        String sql = "insert into " + tableName + " values(NULL, '" + name + "');";
        db.execSQL(sql);
    }


}