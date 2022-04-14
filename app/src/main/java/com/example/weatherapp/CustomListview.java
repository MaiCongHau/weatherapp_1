package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListview extends BaseAdapter {
    Context context;
    ArrayList<thoitiet> arrayList;

    public CustomListview(Context context, ArrayList<thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        thoitiet thoitiet_1 = arrayList.get(i);


        view = layoutInflater.inflate(R.layout.listview,null);
        TextView txt_ngaythang = view.findViewById(R.id.ngaythang);
        TextView txt_trangthai = view.findViewById(R.id.trangthai);
        TextView txt_maxtemp = view.findViewById(R.id.maxtemp);
        TextView txt_mintemp = view.findViewById(R.id.mintemp);
        ImageView img_icon = view.findViewById(R.id.icon);

        txt_ngaythang.setText(thoitiet_1.day);
        txt_trangthai.setText(thoitiet_1.trangthai);
        txt_maxtemp.setText(thoitiet_1.maxtemp+"°C");
        txt_mintemp.setText(thoitiet_1.mintemp+"°C");

        Picasso.with(context).load("https://openweathermap.org/img/wn/"+thoitiet_1.image+"@2x.png").into(img_icon);
        return view;
    }
}
