package cn.edu.pku.jiaoxulun.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.jiaoxulun.miniweather.ClearEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.pku.jiaoxulun.app.MyApplication;
import cn.edu.pku.jiaoxulun.bean.City;

/**
 * Created by jiaoxulun on 2017/10/24.
 */

public class SelectCity extends Activity implements View.OnClickListener {
    private ImageView mBackBtn;
    private ListView mList;
    private List<City> cityList;
    private ClearEditText mClearEditText;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_city);

        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                //Intent i = new Intent();
                //i.putExtra("cityCode", "101160101");
                //setResult(RESULT_OK, i);
                finish();
                break;
            default:
                break;
        }
    }


    public void initViews() {
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        mClearEditText = (ClearEditText) findViewById(R.id.search_city);
        mList = (ListView) findViewById(R.id.title_list);

        MyApplication myApplication = (MyApplication) getApplication();
        cityList = myApplication.getMyCityList();

        final List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (City city : cityList) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("City", city.getCity());
            listem.put("Number", city.getNumber());
            listems.add(listem);
        }
        final SimpleAdapter simplead = new SimpleAdapter(this, listems, R.layout.item, new String[]{"City"/*,"Number"*/}, new int[]{R.id.list_city_name});

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Map<String, Object> listem = listems.get(position);
                //Map<String, Object> listem = simplead.getItem(position);
                String a = simplead.getItem(position).toString();
                //String CityCode = listem.get("Number").toString();
                String CityCode = a.substring(8,17);
                Intent i = new Intent();
                i.putExtra("cityCode", CityCode);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //当输入框为l里面的值为空时，更新为原始列表，否则更新为过滤列表
                simplead.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mList.setAdapter(simplead);
    }
}
