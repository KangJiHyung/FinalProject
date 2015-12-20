package com.example.cherry.googlemap;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mBtList = (Button) findViewById(R.id.bt_list);
        Button mBtInsert = (Button) findViewById(R.id.bt_insert);
        Button mBtState = (Button) findViewById(R.id.bt_state);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_list:      // 조회
                        Intent intent1 = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.bt_insert:    // 입력
                        Intent intent = new Intent(MainActivity.this, InsertMapActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.bt_state:     // 통계
                        Intent intent2 = new Intent(MainActivity.this, StatisticActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        };

        mBtList.setOnClickListener(listener);
        mBtInsert.setOnClickListener(listener);
        mBtState.setOnClickListener(listener);
    }



}
