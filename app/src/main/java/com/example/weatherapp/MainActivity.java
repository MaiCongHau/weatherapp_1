package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button bnt_search,bnt_chuyenmanhinh;
    ImageView img_trangthai;
    TextView txt_tenthanhpho,txt_tenquocgia,txt_nhietdo,txt_trangthai,txt_doam,txt_may,txt_gio,txt_date;
    String city_hcm="";
    String lon_value="";
    String lat_value="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        city_hcm = editText.getText().toString().trim();
        if(city_hcm.equals(""))
        {
            city_hcm = "Ho Chi Minh";
            Getweather(city_hcm);
        }
        bnt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editText.getText().toString().trim();
                if(city.equals(""))
                {
                    city = "Ho Chi Minh";
                }
                Getweather(city);
            }
        });
        bnt_chuyenmanhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editText.getText().toString().trim();
                if(city.equals(""))
                {
                    city = "Ho Chi Minh";
                }
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("name",city);
                intent.putExtra("lon",lon_value);
                intent.putExtra("lat",lat_value);
                startActivity(intent);
            }
        });
    }
    void Getweather(String diadiem)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+diadiem+"&appid=52e41ce5319abc4cd5f3c7b59e8835f3";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject_coord = jsonObject.getJSONObject("coord");
                    String lon = jsonObject_coord.getString("lon");
                    String lat = jsonObject_coord.getString("lat");
                    lon_value = lon;
                    lat_value= lat;
                    Toast.makeText(MainActivity.this, lon_value+""+lat_value, Toast.LENGTH_SHORT).show();
                    String day = jsonObject.getString("dt");
                    String name = jsonObject.getString("name");
                    txt_tenthanhpho.setText("Tên thành phố "+name);
                    long date = Long.valueOf(day);
                    Date mili =  new Date(date*1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH-mm-ss");
                    String ngay = simpleDateFormat.format(mili);
                    txt_date.setText(ngay);
                    JSONArray jsonArrayweather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectweather = jsonArrayweather.getJSONObject(0);
                    String weather = jsonObjectweather.getString("main");
                    txt_trangthai.setText(weather);

                    String icon = jsonObjectweather.getString("icon");
                    Picasso.with(MainActivity.this).load("https://openweathermap.org/img/wn/"+icon+"@2x.png").into(img_trangthai);
                    JSONObject sys = jsonObject.getJSONObject("sys");
                    String tenQuocgia= sys.getString("country");
                    txt_tenquocgia.setText("Ten quoc gia "+tenQuocgia);
                    JSONObject jsonObject_main = jsonObject.getJSONObject("main");
                    String apsuat = jsonObject_main.getString("pressure");
                    txt_may.setText(apsuat);
                    String nhietdo = jsonObject_main.getString("temp");
                    Double temp  = Double.valueOf(nhietdo);
                    double roundOff = Math.round(temp * 100.0) / 100.0;
                    float nhietdo_float = (float) (roundOff - 273.15);
                    txt_nhietdo.setText(nhietdo_float+"°C");
                    String doam = jsonObject_main.getString("humidity");
                    txt_doam.setText(doam+"%");
                    JSONObject jsonObject_wind = jsonObject.getJSONObject("wind");
                    String wind = jsonObject_wind.getString("speed");
                    txt_gio.setText(wind);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
    void anhxa()
    {
        editText = (EditText) findViewById(R.id.diadiem);
        bnt_search = (Button) findViewById(R.id.search);
        bnt_chuyenmanhinh = (Button) findViewById(R.id.chuyenmanhinh);
        img_trangthai = (ImageView) findViewById(R.id.icon);
        txt_tenthanhpho=(TextView) findViewById(R.id.tenthanhpho);
        txt_tenquocgia=(TextView) findViewById(R.id.tenquocgia);
        txt_nhietdo=(TextView) findViewById(R.id.nhietdo);
        txt_trangthai=(TextView) findViewById(R.id.trangthai);
        txt_doam=(TextView) findViewById(R.id.doam);
        txt_may=(TextView) findViewById(R.id.may);
        txt_gio=(TextView) findViewById(R.id.gio);
        txt_date=(TextView) findViewById(R.id.date);
    }
}