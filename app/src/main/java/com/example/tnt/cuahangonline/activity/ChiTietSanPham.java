package com.example.tnt.cuahangonline.activity;

import android.media.Image;
import android.support.test.espresso.core.internal.deps.protobuf.Internal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten,txtgia,txtmota;
    Button btndatmua;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActitonToolBar();
        GetInfomation();
        CatchEventSpinner();

    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInfomation() {
        int id = 0;
        String Tenchitiet = "";
        int GiaChitiet = 0;
        String HinhanhChitiet = "";
        String MotaChitiet ="";
        int idsanpham = 0;
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        GiaChitiet = sanpham.getGiasanpham();
        HinhanhChitiet = sanpham.getHinhanhsangpham();
        MotaChitiet = sanpham.getMotasanpham();
        idsanpham = sanpham.getIDsanpham();
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+ decimalFormat.format(GiaChitiet)+ "VNĐ");
        txtmota.setText(MotaChitiet);
        Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitiet);

    }

    private void ActitonToolBar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet = findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = findViewById(R.id.imageviewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.buttomdatmua);


    }
}