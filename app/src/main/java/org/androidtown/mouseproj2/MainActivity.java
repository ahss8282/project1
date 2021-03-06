package org.androidtown.mouseproj2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.madu.common.constatns.ConstCommonNumberCode;
import com.madu.common.constatns.ConstCommonSenSorCode;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;
    private TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        myText = (TextView) findViewById(R.id.myText);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);

            if (gabOfTime > ConstCommonSenSorCode.GAP_OF_TIME) {
                lastTime = currentTime;
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
                //흔들림의 정도에따라 이벤트를 넣게됨
                if (speed > ConstCommonSenSorCode.SHAKE_THRESHOLD) {

                }

                lastX = event.values[ConstCommonSenSorCode.DATA_X];
                lastY = event.values[ConstCommonSenSorCode.DATA_Y];
                lastZ = event.values[ConstCommonSenSorCode.DATA_Z];
                myText.setText(String.valueOf(event.values[0])+","+String.valueOf(event.values[1])+","+String.valueOf(event.values[2])+"시발");
            }

        }
    }
}



