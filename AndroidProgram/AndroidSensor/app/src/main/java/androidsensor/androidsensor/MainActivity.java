package androidsensor.androidsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private TextView txt6;
    private TextView txt7;
    private TextView txt8;
    private TextView txt9;
    private TextView txt10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //初始化创建传感器管理器
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> list=sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < list.size(); i++) {
            Log.e("bbb",list.get(i).getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        //在super前调用，表示先清空子类，再清空父类
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        int sensorType = sensorEvent.sensor.getType();
        StringBuilder sb;
        switch (sensorType) {
            case Sensor.TYPE_ROTATION_VECTOR:
                Log.e("aaa", "This is " + "旋转矢量传感器");
                sb = new StringBuilder();
                sb.append("X方向上的欧拉角：");
                sb.append(values[0]);
                sb.append("\nY方向上的欧拉角：");
                sb.append(values[1]);
                sb.append("\nZ方向上的欧拉角：");
                sb.append(values[2]);
                txt1.setText("旋转矢量传感器："+sb.toString());
                break;
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                Log.e("aaa", "This is " + "未标记旋转矢量传感器");
                sb = new StringBuilder();
                sb.append("X方向上的欧拉角：");
                sb.append(values[0]);
                sb.append("\nY方向上的欧拉角：");
                sb.append(values[1]);
                sb.append("\nZ方向上的欧拉角：");
                sb.append(values[2]);
                txt2.setText("未标记旋转矢量传感器："+sb.toString());
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                Log.e("aaa", "This is " + "地磁旋转传感器");
                sb = new StringBuilder();
                sb.append("X方向上的欧拉角：");
                sb.append(values[0]);
                sb.append("\nY方向上的欧拉角：");
                sb.append(values[1]);
                sb.append("\nZ方向上的欧拉角：");
                sb.append(values[2]);
                txt3.setText("地磁旋转传感器："+sb.toString());
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void initView() {
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        txt7 = (TextView) findViewById(R.id.txt7);
        txt8 = (TextView) findViewById(R.id.txt8);
        txt9 = (TextView) findViewById(R.id.txt9);
        txt10 = (TextView) findViewById(R.id.txt10);
    }
}
