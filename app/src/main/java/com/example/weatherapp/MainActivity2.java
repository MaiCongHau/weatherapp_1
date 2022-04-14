package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    ImageView bnt_back;
    TextView txt_thanhpho;
    ListView lv;
    String tenThanhpho="";
    ArrayList<thoitiet> arrayList_thoitiet;
    CustomListview customListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        anhxa();
        Intent intent = getIntent();
        String city =intent.getStringExtra("name");
        String lon =intent.getStringExtra("lon");
        String lat =intent.getStringExtra("lat");
        dulieu7ngay(city,lon,lat);
        txt_thanhpho.setText(city);
        bnt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void anhxa() {
        bnt_back = (ImageView) findViewById(R.id.back);
        txt_thanhpho= (TextView) findViewById(R.id.thanhpho);
        lv = (ListView) findViewById(R.id.listview);
        arrayList_thoitiet = new ArrayList<thoitiet>();
        customListview = new CustomListview(MainActivity2.this,arrayList_thoitiet);
        lv.setAdapter(customListview);
    }

    public void dulieu7ngay(String city,String lon ,String lat)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=hourly,minute&appid=52e41ce5319abc4cd5f3c7b59e8835f3";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayList = jsonObject.getJSONArray("daily");
                    for (int i=0;i<jsonArrayList.length();i++)
                    {
                        JSONObject jsonObject_value = jsonArrayList.getJSONObject(i);
                        String day = jsonObject_value.getString("dt");
                        long date = Long.valueOf(day);
                        Date mili =  new Date(date*1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
                        String ngay = simpleDateFormat.format(mili);

                        JSONArray jsonArrayweather = jsonObject_value.getJSONArray("weather");
                        JSONObject jsonObjectweather = jsonArrayweather.getJSONObject(0);
                        String trangthai = jsonObjectweather.getString("main");
                        String icon = jsonObjectweather.getString("icon");

                        JSONObject jsonObject_main = jsonObject_value.getJSONObject("temp");
                        String temp_min= jsonObject_main.getString("min");
                        String temp_max= jsonObject_main.getString("max");
                         //public thoitiet(String day, String trangthai, String image, Float maxtemp, Float mintemp)
                        arrayList_thoitiet.add(new thoitiet(ngay,trangthai,icon,temp_max,temp_min));
                    }
                    customListview.notifyDataSetChanged();




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
}