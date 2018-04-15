package com.example.tnt.cuahangonline.activity;

import android.app.usage.UsageEvents;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.adapter.GiohangAdapter;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {

    ListView  lvgiohang;
    TextView txtthongbao;
    TextView txttongtien;
    Button btnthanhtoan,btntieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolBar();
        Checkdata();
        EventUltil();
    }

    private void EventUltil() {
        long tongtien = 0;
        for (int i= 0 ; i < MainActivity.manggiohang.size() ; i++){
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ");

    }

    private void Checkdata() {
        if (MainActivity.manggiohang.size() <=0 ){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);

        } else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Anhxa() {
        lvgiohang =(ListView) findViewById(R.id.listviewgiohang);
        txtthongbao =(TextView) findViewById(R.id.textviewthongbao);
        txttongtien =(TextView) findViewById(R.id.textviewtongtien);
        btnthanhtoan =(Button) findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua =(Button) findViewById(R.id.buttontieptucmuahang);
        toolbargiohang =(Toolbar) findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(Giohang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}
