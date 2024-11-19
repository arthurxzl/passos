package com.example.passosjava;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorAvailable;
    private int stepCount = 0;  // Variável para armazenar a contagem de passos
    private int initialSteps = 0; // Para zerar a contagem ao abrir

    private TextView stepCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCountView = findViewById(R.id.stepCountView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorAvailable = true;
        } else {
            stepCountView.setText("Sensor não disponível!");
            isSensorAvailable = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorAvailable) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (initialSteps == 0) {
                // Captura o valor inicial de passos na primeira leitura
                initialSteps = (int) event.values[0];
            }
            // Atualiza a contagem descontando os passos iniciais
            stepCount = (int) event.values[0] - initialSteps;
            stepCountView.setText("Passos: " + stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não usado neste exemplo
    }
}
