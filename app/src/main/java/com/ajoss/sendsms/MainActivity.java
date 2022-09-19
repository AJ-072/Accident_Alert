package com.ajoss.sendsms;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ajoss.sendsms.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private LocationManager locationManager;
    private Location location;
    public static final String PHONE_NUMBER = "8129350423";
    private SmsManager smsManager= SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 12345);
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.SEND_SMS
                    }, 1234);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

//        binding.phone.setText(PHONE_NUMBER);
        binding.submitButton.setOnClickListener(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location){
        this.location = location;
        binding.longitude.setText(location.getLongitude()+"");
        binding.latitude.setText(location.getLatitude()+"");

    }

    @Override
    public void onClick(View view) {
        String phone = binding.phoneNumber.getText().toString();
        String msg = "longitude : "+location.getLongitude()+"\nlatitude : "+location.getLatitude();
        smsManager.sendTextMessage(phone,null,msg,null,null);
    }
}