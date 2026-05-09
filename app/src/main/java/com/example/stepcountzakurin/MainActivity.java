package com.example.stepcountzakurin;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float X = event.values[0];
            float Y = event.values[1];
            float Z = event.values[2];

            float AccelerationSquare = (X*X+Y*Y+Z*Z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
            long ActualTime = System.currentTimeMillis();

            if(AccelerationSquare >= 2){
                if(ActualTime - LastTimeUpdate < 200) return;
                if(!Active) return;
                .
                LastTimeUpdate = ActualTime;

                Count++;
                tvResult.setText(String.valueOf(Count));
            }
        }
    }
}