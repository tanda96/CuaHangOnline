package com.example.tnt.cuahangonline.activity;

import android.content.Intent;
import android.media.Image;
import android.support.test.espresso.core.internal.deps.protobuf.Internal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.model.Giohang;
import com.example.tnt.cuahangonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten,txtgia,txtmota;
    Button btndatmua;
    Spinner spinner;
    int id = 0;
    String Tenchitiet = "";
    int GiaChitiet = 0;
    String HinhanhChitiet = "";
    String MotaChitiet ="";
    int idsanpham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActitonToolBar();
        GetInfomation();
        CatchEventSpinner();
        EventButton();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.tnt.cuahangonline.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (MainActivity.manggiohang.size() >0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0 ;i<MainActivity.manggiohang.size();i++ ){
                        if (MainActivity.manggiohang.get(i).getIdsp()==id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluongsp() >=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists = false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * GiaChitiet;
                        MainActivity.manggiohang.add(new Giohang(id, Tenchitiet,Giamoi,HinhanhChitiet,soluong));
                    }
            } else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * GiaChitiet;
                    MainActivity.manggiohang.add(new Giohang(id, Tenchitiet,Giamoi,HinhanhChitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), com.example.tnt.cuahangonline.activity.Giohang.class);
                startActivity(intent);
            }

        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInfomation() {

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