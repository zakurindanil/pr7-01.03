package com.example.stepcountzakurin;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public boolean Active = false;
    SensorManager SensorManager;
    int Count = 0;
    TextView tvResult;
    Button btnPause;
    long LastTimeUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        btnPause = findViewById(R.id.btnPause);
        tvResult.setText(String.valueOf(Count));
        SensorManager = (android.hardware.SensorManager) getSystemService(SENSOR_SERVICE);

        SensorManager.registerListener((SensorEventListener) this,
                SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                android.hardware.SensorManager.SENSOR_DELAY_FASTEST);
        LastTimeUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onResunme(){
        super.onResume();

        SensorManager.registerListener((SensorEventListener) this,
                SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                android.hardware.SensorManager.SENSOR_DELAY_FASTEST);
        LastTimeUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onPause(){
        super.onPause();

        SensorManager.unregisterListener((SensorEventListener) this);
    }

    public void OnStop(View view){
        Active = !Active;
        if(Active) btnPause.setText("ПАУЗА");
        else btnPause.setText("ВОЗОБНОВИТЬ");
    }
}