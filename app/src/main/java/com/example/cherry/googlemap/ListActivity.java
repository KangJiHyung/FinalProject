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
import android.widget.ListView;

import java.util.ArrayList;


public class ListActivity extends ActionBarActivity {

    // Database 관련 객체들
    SQLiteDatabase db;
    String dbName = "idList.db"; // name of Database;
    String tableName = "idListTable"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;

    Button mbtLook;
    Button mbtDelete;
    Button mbtBack;

    ListView mList;
    ArrayAdapter<String> baseAdapter;
    ArrayList<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // // Database 생성 및 열기
        db = openOrCreateDatabase(dbName,dbMode,null);
        // 테이블 생성
        createTable();

        mbtLook = (Button) findViewById(R.id.bt_look);
        mbtDelete = (Button) findViewById(R.id.bt_delete);
        mbtBack = (Button) findViewById(R.id.bt_back);
        mList = (ListView) findViewById(R.id.listView);

        mbtLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameList.clear();
                selectAll();
                baseAdapter.notifyDataSetChanged();
            }
        });

        mbtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                removeDataAll();

                nameList.clear();
                selectAll();
                baseAdapter.notifyDataSetChanged();
            }
        });

        mbtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Create listview
        nameList = new ArrayList<String>();
        baseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        mList.setAdapter(baseAdapter);

    }

    // Table 생성
    public void createTable() {
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "name text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite","error: "+ e);
        }
    }

    // Table 삭제
    public void removeDataAll() {
        String sql = "delete from " + tableName;
        db.execSQL(sql);
    }

    // 모든 Data 읽기
    public void selectAll() {
        String sql = "select * from " + tableName + " order by id desc;";   // 데이터를 최신순으로 보여주기
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(1);
            Log.d("lab_sqlite", "index= " + id + " name=" + name);

            nameList.add(name);
            results.moveToNext();
        }
        results.close();
    }

}
