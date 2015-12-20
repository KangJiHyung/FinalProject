package com.example.cherry.googlemap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class StatisticActivity extends ActionBarActivity {

    // Database 관련 객체들
    SQLiteDatabase db;
    String dbName = "idList.db"; // name of Database;
    String tableName = "idListTable"; // name of Table;
    int dbMode = Context.MODE_PRIVATE; // 이 프로젝트 안에서만 쓰겠다

    TextView mTV;
    Button mBtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        // Database 생성 및 열기
        db = openOrCreateDatabase(dbName, dbMode, null);
        // 테이블 생성
        createTable();

        mTV = (TextView) findViewById(R.id.textView);
        mBtBack = (Button) findViewById(R.id.bt_back);

        allDataCheck();     // 화면에 통계량 보여주기

        mBtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    int totalDataCount = 0;     // 전체 데이터 갯수
    // 각각의 상태에 해당하는 데이터 갯수
    int count1 = 0;  int count2 = 0;  int count3 = 0;  int count4 = 0;  int count5 = 0;

    // 모든 Data 읽고 해당 문자열이 있는지 찾기
    public void allDataCheck() {
        String sql = "select * from " + tableName + " order by id desc;";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            String text = results.getString(1);

            if (text.contains("공부") == true) {
                count1++;
            } else if (text.contains("놀기")) {
                count2++;
            } else if (text.contains("먹기")) {
                count3++;
            } else if (text.contains("취미")) {
                count4++;
            } else if (text.contains("기타")) {
                count5++;
            }

            totalDataCount++;

            results.moveToNext();
        }


        String study = "공부 : " + String.valueOf((count1 * 100) / totalDataCount) + "%\n";
        String play = "놀기 : " + String.valueOf((count2 * 100) / totalDataCount) + "%\n";
        String eat = "먹기 : " + String.valueOf((count3 * 100) / totalDataCount) + "%\n";
        String hobby = "취미생활 : " + String.valueOf((count4 * 100) / totalDataCount) + "%\n";
        String others = "기타 : " + String.valueOf((count5 * 100) / totalDataCount) + "%";

        mTV.setText(study + play + eat + hobby + others);

        results.close();
    }
}
