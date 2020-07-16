package gps.gps;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LocationManager lm;
    LocationProvider lp;
    Criteria criteria;
    private Button btnGetLocationProviderMsg;
    private Button btnAfterCriteriaMsg;
    private Button btnCheckGPSOpen;
    private Button btnOpenGPS;
    private Button btnOtherFunction;
    private Button btnGetLocationMsg;
    private Button btnOpenAreaRail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //获取GPS服务
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //获取关于定位设置信息（LocationProvider）
        //LocationManager.PASSIVE_PROVIDER --定位系统由其他系统提供
        //LocationManager.NETWORK_PROVIDER --定位系统由网络提供
        //LocationManager.GPS_PROVIDER --定位系统由GPS提供
        lp = lm.getProvider(LocationManager.GPS_PROVIDER);

        //初始化过滤条件
        criteria = new Criteria();
    }

    private void initView() {
        btnGetLocationProviderMsg = (Button) findViewById(R.id.btnGetLocationProviderMsg);
        btnAfterCriteriaMsg = (Button) findViewById(R.id.btnAfterCriteriaMsg);
        btnCheckGPSOpen = (Button) findViewById(R.id.btnCheckGPSOpen);
        btnOpenGPS = (Button) findViewById(R.id.btnOpenGPS);

        btnGetLocationProviderMsg.setOnClickListener(this);
        btnAfterCriteriaMsg.setOnClickListener(this);
        btnCheckGPSOpen.setOnClickListener(this);
        btnOpenGPS.setOnClickListener(this);
        btnOtherFunction = (Button) findViewById(R.id.btnOtherFunction);
        btnOtherFunction.setOnClickListener(this);
        btnGetLocationMsg = (Button) findViewById(R.id.btnGetLocationMsg);
        btnGetLocationMsg.setOnClickListener(this);
        btnOpenAreaRail = (Button) findViewById(R.id.btnOpenAreaRail);
        btnOpenAreaRail.setOnClickListener(this);
    }

    @SuppressLint({"WrongConstant", "MissingPermission"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //region 查看定位信息
            case R.id.btnGetLocationProviderMsg:
                StringBuilder sb = new StringBuilder();
                sb.append("getAccuracy精度:" + lp.getAccuracy() + "\n");
                sb.append("getName名称:" + lp.getName() + "\n");
                sb.append("getPowerRequirement电源需求:" + lp.getPowerRequirement() + "\n");
                sb.append("hasMonetaryCost是否收费:" + lp.hasMonetaryCost() + "\n");
                sb.append("requiresCell是否需要访问网络基站:" + lp.requiresCell() + "\n");
                sb.append("requiresNetwork是否需要网络:" + lp.requiresNetwork() + "\n");
                sb.append("requiresSatellite是否需要访问基于卫星的定位系统:" + lp.requiresSatellite() + "\n");
                sb.append("supportsAltitude是否支持高度:" + lp.supportsAltitude() + "\n");
                sb.append("supportsBearing是否支持方向:" + lp.supportsBearing() + "\n");
                sb.append("supportsSpeed是否支持速度:" + lp.supportsSpeed() + "\n");
                Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
                Log.e("aaa", "aaa" + sb.toString());
                break;
            //endregion
            //region设置过滤条件
            case R.id.btnAfterCriteriaMsg:
                criteria.setCostAllowed(false);//设置是否收费
                criteria.setSpeedAccuracy(Criteria.ACCURACY_LOW);//设置速度精度
                criteria.setBearingAccuracy(Criteria.ACCURACY_LOW);//设置方向精度
                criteria.setAccuracy(Criteria.ACCURACY_LOW);//设置定位精度
                criteria.setVerticalAccuracy(Criteria.ACCURACY_LOW);//设置垂直定位精度
                criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);//设置水平定位精度
                criteria.setPowerRequirement(Criteria.POWER_HIGH);//设置耗电量
                criteria.setAltitudeRequired(false);//设置是否提供高度信息
                criteria.setBearingRequired(false);//设置是否提供方向信息
                criteria.setSpeedRequired(false);//设置是否提供速度信息

                //当前定位设置是否满足过滤条件
                Toast.makeText(this, "meetsCriteria是否满足过滤条件：" + lp.meetsCriteria(criteria), Toast.LENGTH_SHORT).show();
                Log.e("aaa", "meetsCriteria是否满足过滤条件：" + lp.meetsCriteria(criteria));
                break;
            //endregion
            //region 是否打开GPS
            case R.id.btnCheckGPSOpen:
                Toast.makeText(this, "GPS是否打开:" + lm.isProviderEnabled(LocationManager.GPS_PROVIDER), Toast.LENGTH_SHORT).show();
                break;
            //endregion
            //region 强制打开GPS
            case R.id.btnOpenGPS:
                //方法一：强制帮用户打开GPS 5.0以前可用
//                Intent gpsIntent = new Intent();
//                gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//                gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
//                gpsIntent.setData(Uri.parse("custom:3"));
//                try {
//                    PendingIntent.getBroadcast(MainActivity.this, 0, gpsIntent, 0).send();
//                } catch (PendingIntent.CanceledException e) {
//                    e.printStackTrace();
//                }
                //方法二
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 0);
                break;
            //endregion
            //region LocationManager其他方法
            case R.id.btnOtherFunction:
//                GpsStatus.Listener listener=new GpsStatus.Listener() {
//                    @Override
//                    public void onGpsStatusChanged(int i) {
//                        Log.e("aaa","状态码"+i);
//                    }
//                };
//                //添加一个GPS状态监听器
//                lm.addGpsStatusListener(listener);
//                //获取所有的LocationProvider列表
//                lm.getAllProviders();
//                //根据指定条件返回最优LocationProvider
//                lm.getBestProvider(criteria,true);
//                //获取最后一次一直的location
//                lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                //根据名称获取LocationProvider
//                lm.getProvider(LocationManager.GPS_PROVIDER);
//                //获取所有LocationProvider
//                lm.getProviders(true);
//                //获取指定条件的LocationProvider
//                lm.getProviders(criteria,true);
//                //判断LocationProvider是否可用
//                lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//                //删除一个GPS状态监听器
//                lm.removeGpsStatusListener(listener);
//                break;
                //endregion
            //region GPS实时定位信息
            case R.id.btnGetLocationMsg:
                Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                updateShow(lc);
                //参数一：使用的定位服务
                //参数二：最短时间触发
                //参数三：最短距离触发
                //参数四：位置改变的监听器
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, new LocationListener() {
                    //当位置改变时触发
                    @Override
                    public void onLocationChanged(Location location) {
                        updateShow(location);
                    }

                    //当GPS状态发生变化时触发
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    //当LocationProvider可用时
                    @Override
                    public void onProviderEnabled(String provider) {
                        updateShow(lm.getLastKnownLocation(provider));
                    }

                    //当LocationProvider不可用时
                    @Override
                    public void onProviderDisabled(String provider) {
                        updateShow(null);
                    }
                });
                break;
            //endregion
            //region 地域围栏
            case R.id.btnOpenAreaRail:
                double longitude = -122.0840;
                double latitude = 37.42199833;
                float radius = 10;     //定义半径，米
                Intent intent1 = new Intent(this, RemoveReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(this, -1, intent1, 0);
                //参数一：固定点的精度
                //参数二：固定点的纬度
                //参数三：半径长度
                //参数四：经过多少秒后过期，-1表示永不过期
                //参数五：触发对应的intent组件
                lm.addProximityAlert(latitude, longitude, radius, -1, pi);
                break;
            //endregion
        }
    }

    //定义一个更新显示的方法
    private void updateShow(Location location) {
        if (location != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("当前的位置信息：\n");
            sb.append("精度：" + location.getLongitude() + "\n");
            sb.append("纬度：" + location.getLatitude() + "\n");
            sb.append("高度：" + location.getAltitude() + "\n");
            sb.append("速度：" + location.getSpeed() + "\n");
            sb.append("方向：" + location.getBearing() + "\n");
            sb.append("定位精度：" + location.getAccuracy() + "\n");
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
            Log.e("aaa", sb.toString());
        } else {
            Toast.makeText(this, "当前的位置信息：无", Toast.LENGTH_SHORT).show();
            Log.e("aaa", "当前的位置信息：无");
        }
    }
}
