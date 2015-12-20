package com.example.cherry.googlemap;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class InsertMapActivity extends ActionBarActivity {

    int count = 0;

    TextView logView;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_map);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            Marker ME;

            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                if (count == 0) {
                    LatLng MyLocation = new LatLng(lat, lng);
                    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    ME = map.addMarker(new MarkerOptions().position(MyLocation).title("내위치"));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocation, 15));
                    count = 1;  // 처음 위치를 잡으면 1
                } else if (count == 1) {
                    ME.remove();    // 전에꺼 지우기

                    LatLng MyLocation = new LatLng(lat, lng);
                    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    ME = map.addMarker(new MarkerOptions().position(MyLocation).title("내위치"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(MyLocation));
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                logView.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                logView.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                logView.setText("onProviderDisabled");
            }
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        Button btInsert = (Button) findViewById(R.id.bt_insert);
        Button btCancel = (Button) findViewById(R.id.bt_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_insert:    // 입력
                        Intent intent = new Intent(InsertMapActivity.this, InsertActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.bt_cancel:     // 취소
                        finish();
                        break;
                }
            }
        };

        btInsert.setOnClickListener(listener);
        btCancel.setOnClickListener(listener);
    }

}
