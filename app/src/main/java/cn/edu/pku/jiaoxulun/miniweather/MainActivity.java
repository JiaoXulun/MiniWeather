package cn.edu.pku.jiaoxulun.miniweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.jiaoxulun.bean.TodayWeather;
import cn.edu.pku.jiaoxulun.util.NetUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final int UPDATE_TODAY_WEATHER = 1;

    private ImageView mUpdateBtn;

    private ImageView mCitySelect;

    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv, pmQualityTv, temperatureTv, climateTv, windTv, city_name_Tv;
    private ImageView weatherImg, pmImg;

    //定义未来6天天气所需的变量
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1_weather, R.id.iv2_weather};

    private TextView w1_date1, w1_temperature1, w1_climate1, w1_wind1;
    private ImageView w1_img1;
    private TextView w1_date2, w1_temperature2, w1_climate2, w1_wind2;
    private ImageView w1_img2;
    private TextView w2_date1, w2_temperature1, w2_climate1, w2_wind1;
    private ImageView w2_img1;
    private TextView w2_date2, w2_temperature2, w2_climate2, w2_wind2;
    private ImageView w2_img2;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    void initView() {
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);

        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
        weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
    }
    public int getImageId(String type){
        switch (type) {
            case "暴雪":
                return R.drawable.biz_plugin_weather_baoxue;
            case "暴雨":
                return R.drawable.biz_plugin_weather_baoyu;

            case "大暴雨":
                return R.drawable.biz_plugin_weather_dabaoyu;

            case "大雪":
                return R.drawable.biz_plugin_weather_daxue;

            case "大雨":
                return R.drawable.biz_plugin_weather_dayu;

            case "多云":
                return R.drawable.biz_plugin_weather_duoyun;

            case "雷阵雨":
                return R.drawable.biz_plugin_weather_leizhenyu;

            case "雷阵雨冰雹":
                return R.drawable.biz_plugin_weather_leizhenyubingbao;

            case "晴":
                return R.drawable.biz_plugin_weather_qing;

            case "沙尘暴":
                return R.drawable.biz_plugin_weather_shachenbao;

            case "特大暴雨":
                return R.drawable.biz_plugin_weather_tedabaoyu;

            case "雾":
                return R.drawable.biz_plugin_weather_wu;

            case "小雪":
                return R.drawable.biz_plugin_weather_xiaoxue;

            case "小雨":
                return R.drawable.biz_plugin_weather_xiaoyu;

            case "阴":
                return R.drawable.biz_plugin_weather_yin;

            case "雨夹雪":
                return R.drawable.biz_plugin_weather_yujiaxue;

            case "阵雪":
                return R.drawable.biz_plugin_weather_zhenxue;

            case "阵雨":
                return R.drawable.biz_plugin_weather_zhenyu;

            case "中雪":
                return R.drawable.biz_plugin_weather_zhongxue;

            case "中雨":
                return R.drawable.biz_plugin_weather_zhongyu;

            default:
                return R.drawable.biz_plugin_weather_qing;

        }
    }
    void updateTodayWeather(TodayWeather todayWeather) {

        city_name_Tv.setText(todayWeather.getCity() + "天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime() + "发布");
        humidityTv.setText("温度:" + todayWeather.getShidu());
        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate());
        temperatureTv.setText(todayWeather.getLow() + "~" + todayWeather.getHigh());
        climateTv.setText(todayWeather.getType());
        windTv.setText("风力" + todayWeather.getFengli());
        weatherImg.setImageResource(getImageId(todayWeather.getType()));

        w1_date1.setText(todayWeather.getDate1());
        w1_temperature1.setText(todayWeather.getLow1() + "~" + todayWeather.getHigh1());
        w1_climate1.setText(todayWeather.getType1());
        w1_img1.setImageResource(getImageId(todayWeather.getType1()));

        w1_date2.setText(todayWeather.getDate2());
        w1_temperature2.setText(todayWeather.getLow2() + "~" + todayWeather.getHigh2());
        w1_climate2.setText(todayWeather.getType2());
        w1_img2.setImageResource(getImageId(todayWeather.getType2()));

        w2_date1.setText(todayWeather.getDate3());
        w2_temperature1.setText(todayWeather.getLow3() + "~" + todayWeather.getHigh3());
        w2_climate1.setText(todayWeather.getType3());
        w2_img1.setImageResource(getImageId(todayWeather.getType3()));

        w2_date2.setText(todayWeather.getDate4());
        w2_temperature2.setText(todayWeather.getLow4() + "~" + todayWeather.getHigh4());
        w2_climate2.setText(todayWeather.getType4());
        w2_img2.setImageResource(getImageId(todayWeather.getType4()));

        Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);

        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

        mCitySelect = (ImageView) findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);

        initView();
        initViews();
        initDots();
    }

    private void initViews(){
        //通过LayoutInflater来动态加载这些视图
        LayoutInflater inflater = LayoutInflater.from(this); //获取LayoutInflater对象
        views = new ArrayList<View>(); //构造View类型的数组
        //向views集合中加入三个view对象
        //通过LayoutInflater动态加载布局文件并转化的
        views.add(inflater.inflate(R.layout.weather_p1, null));
        views.add(inflater.inflate(R.layout.weather_p2, null));
        //实例化一个ViewPagerAdapter对象
        viewPagerAdapter = new ViewPagerAdapter(views, this);
        //通过findViewById方法获取ViewPager对象
        viewPager = (ViewPager) findViewById(R.id.viewpager_weather);
        //设置ViewPager的适配器
        viewPager.setAdapter(viewPagerAdapter);
        //为ViewPager控件设置页面变化的监听事件
        viewPager.setOnPageChangeListener(this);

        //初始化所有控件
        w1_date1 = (TextView)views.get(0).findViewById(R.id.w1_date1);
        w1_temperature1 = (TextView)views.get(0).findViewById(R.id.w1_temperature1);
        w1_climate1 = (TextView)views.get(0).findViewById(R.id.w1_climate1);
        w1_img1 = (ImageView)views.get(0).findViewById(R.id.w1_img1);

        w1_date2 = (TextView)views.get(0).findViewById(R.id.w1_date2);
        w1_temperature2 = (TextView)views.get(0).findViewById(R.id.w1_temperature2);
        w1_climate2 = (TextView)views.get(0).findViewById(R.id.w1_climate2);
        w1_img2 = (ImageView)views.get(0).findViewById(R.id.w1_img2);

        w2_date1 = (TextView)views.get(1).findViewById(R.id.w2_date1);
        w2_temperature1 = (TextView)views.get(1).findViewById(R.id.w2_temperature1);
        w2_climate1 = (TextView)views.get(1).findViewById(R.id.w2_climate1);
        w2_img1 = (ImageView)views.get(1).findViewById(R.id.w2_img1);

        w2_date2 = (TextView)views.get(1).findViewById(R.id.w2_date2);
        w2_temperature2 = (TextView)views.get(1).findViewById(R.id.w2_temperature2);
        w2_climate2 = (TextView)views.get(1).findViewById(R.id.w2_climate2);
        w2_img2 = (ImageView)views.get(1).findViewById(R.id.w2_img2);
    }

    //将三个小圆点控件对象，存入dots数组
    void initDots(){
        dots = new ImageView[views.size()];
        for(int i=0; i<views.size(); i++)
            dots[i] = (ImageView) findViewById(ids[i]);
    }

    /*
    增加页面变化的监听事件，动态地修改三个导航小圆点的属性
     */

    //重写ViewPager.OnPageChangeListener接口的方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //视图发生变化后调用此方法，动态修改小圆点属性，实现相应的导航效果
    @Override
    public void onPageSelected(int position) {
        for(int a=0; a<ids.length; a++){
            //若是选中的视图，则图片src属性为被选中的效果
            if(a == position)
                dots[a].setImageResource(R.drawable.page_indicator_focused);
            else
                dots[a].setImageResource(R.drawable.page_indicator_unfocused);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.title_update_btn) {

            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code","101010100");
            Log.d("myWeatherc", cityCode);

            if (NetUtil.getNetworkState(this) != NetUtil.NETWORK_NONE) {
                Log.d("myWeather", "网络O的K");
                queryWeatherCode(cityCode);
            } else {
                Log.d("myWeather", "网络GG");
                Toast.makeText(MainActivity.this, "网络GG！", Toast.LENGTH_LONG).show();
            }

        }
        if (view.getId() == R.id.title_city_manager) {
            Intent i = new Intent(this, SelectCity.class);
            startActivityForResult(i, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newCityCode = data.getStringExtra("cityCode");
            SharedPreferences.Editor editor = getSharedPreferences("config",MODE_APPEND).edit();
            editor.putString("main_city_code", newCityCode);
            editor.commit();
            Log.d("myweather", "selected cityCode:" + newCityCode);
            if(NetUtil.getNetworkState(this)!=NetUtil.NETWORK_NONE){
                Log.d("myWeather","网络OK");
                queryWeatherCode(newCityCode);
            } else {
                Log.d("myweather","网络GG");
                Toast.makeText(MainActivity.this,"网络GG",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void queryWeatherCode(String cityCode) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("myWeather", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                TodayWeather todayWeather = null;
                try {
                    URL url = new URL(address);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;

                    while ((str = reader.readLine()) != null) {
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr = response.toString();
                    Log.d("myWeather", responseStr);

                    todayWeather = parseXML(responseStr);
                    if (todayWeather != null) {
                        Log.d("myWeather", todayWeather.toString());

                        Message msg = new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj = todayWeather;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }

    private TodayWeather parseXML(String xmlData) {
        TodayWeather todayweather = null;
        int fengxiangCount = 0;
        int fengliCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("resp")) {
                            todayweather = new TodayWeather();
                        }
                        if (todayweather != null) {
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                todayweather.setCity(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")) {
                                eventType = xmlPullParser.next();
                                todayweather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                todayweather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                todayweather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                todayweather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                todayweather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                                eventType = xmlPullParser.next();
                                todayweather.setFengxiang(xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {
                                eventType = xmlPullParser.next();
                                todayweather.setFengli(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {
                                eventType = xmlPullParser.next();
                                todayweather.setDate(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {
                                eventType = xmlPullParser.next();
                                todayweather.setHigh(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {
                                eventType = xmlPullParser.next();
                                todayweather.setLow(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {
                                eventType = xmlPullParser.next();
                                todayweather.setType(xmlPullParser.getText());
                                typeCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 1) {
                                eventType = xmlPullParser.next();
                                todayweather.setDate1(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 1) {
                                eventType = xmlPullParser.next();
                                todayweather.setHigh1(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 1) {
                                eventType = xmlPullParser.next();
                                todayweather.setLow1(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 1) {
                                eventType = xmlPullParser.next();
                                todayweather.setType1(xmlPullParser.getText());
                                typeCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 2) {
                                eventType = xmlPullParser.next();
                                todayweather.setDate2(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 2) {
                                eventType = xmlPullParser.next();
                                todayweather.setHigh2(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 2) {
                                eventType = xmlPullParser.next();
                                todayweather.setLow2(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 2) {
                                eventType = xmlPullParser.next();
                                todayweather.setType2(xmlPullParser.getText());
                                typeCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 3) {
                                eventType = xmlPullParser.next();
                                todayweather.setDate3(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 3) {
                                eventType = xmlPullParser.next();
                                todayweather.setHigh3(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 3) {
                                eventType = xmlPullParser.next();
                                todayweather.setLow3(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 3) {
                                eventType = xmlPullParser.next();
                                todayweather.setType3(xmlPullParser.getText());
                                typeCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 4) {
                                eventType = xmlPullParser.next();
                                todayweather.setDate4(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 4) {
                                eventType = xmlPullParser.next();
                                todayweather.setHigh4(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 4) {
                                eventType = xmlPullParser.next();
                                todayweather.setLow4(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 4) {
                                eventType = xmlPullParser.next();
                                todayweather.setType4(xmlPullParser.getText());
                                typeCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 5) {
                                eventType = xmlPullParser.next();
                                todayweather.setDate5(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 5) {
                                eventType = xmlPullParser.next();
                                todayweather.setHigh5(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 5) {
                                eventType = xmlPullParser.next();
                                todayweather.setLow5(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 5) {
                                eventType = xmlPullParser.next();
                                todayweather.setType5(xmlPullParser.getText());
                                typeCount++;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayweather;
    }

}