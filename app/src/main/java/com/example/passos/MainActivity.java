package com.example.passos;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvTotalPassos;
    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    private int totalPassos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalPassos = findViewById(R.id.tvTotalPassos);

        // Inicializar o sensor
        iniciarSensor();
    }

    private void iniciarSensor() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager != null) {
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

            if (mStepDetectorSensor == null) {
                tvTotalPassos.setText("Sensor de passos não disponível.");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mStepDetectorSensor != null) {
            mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            totalPassos++;
            tvTotalPassos.setText("Passos: " + totalPassos);
            Log.d("StepDetector", "Passos detectados: " + totalPassos);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não utilizado
    }
}
