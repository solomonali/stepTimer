package com.example.sulai.birthdaycard;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView time;
    private Button start;
    private Button cancel;
    private CountDownTimer countDownTimer;
    boolean running = false;
    SensorManager sensorManager;
    TextView tv_steps;
    float initialStep = 0;
    float finalStep = 0;
    boolean timerRunning = false;


    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.start :
                    start();
                    break;
                case R.id.cancel :
                    cancel();
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // btnClickListener = (Button) findViewById(R.id.addTaskButton);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(btnClickListener);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(btnClickListener);
        time = (TextView) findViewById(R.id.time);
       tv_steps = (TextView) findViewById(R.id.tv_steps);
       sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }


    private void start() {
        timerRunning = true;
        time.setText("15");


        countDownTimer = new CountDownTimer(15*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                time.setText("Done !");
            }
        };

        countDownTimer.start();

    }

    private void cancel() {
        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    protected  void onResume(){
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, sensorManager.SENSOR_DELAY_UI);

        } else {
            Toast.makeText(this, "Sensor not found!!1", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        running = false;
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running){
            initialStep = event.values[0];
            tv_steps.setText(String.valueOf(initialStep));
            start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
