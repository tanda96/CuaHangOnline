package com.example.tnt.cuahangonline.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.adapter.DonHangAdapter;
import com.example.tnt.cuahangonline.model.DonHang;
import com.example.tnt.cuahangonline.model.ThongTinDonHang;
import com.example.tnt.cuahangonline.ultil.CheckConnection;
import com.example.tnt.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonHangActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbarDonHang;
    DonHangAdapter donHangAdapter;
    ArrayList<DonHang> arrDonHang;
    ArrayList<ThongTinDonHang> arrThongTinDh;
    ListView lvDonHang;
    View footerview;
    String imei="error";
    boolean isLoading = false;
    mHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionToolBar();
            GetImeiKhachHang();
            GetData();
        }
    }

    public void GetImeiKhachHang() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        imei = tm.getDeviceId();
    }
    private void GetData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanThongTinDonHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String responseDonHang[] = response.split("]",2);
                responseDonHang[0] = responseDonHang[0]+"]";
                int idDonhang=0, idSanPham=0, maDonHang=0, idThongTin=0;
                int status=0;
                int giaSanPham=0;
                int soLuongSanPham=0;
                String tenSanPham="";
                String tenKhachHang="";
                String soDt ="";
                String email="";
                if(responseDonHang[0] != null){
                    lvDonHang.removeFooterView(footerview);
                    try {
                        //System.out.println(responseDonHang[1]);
                        JSONArray jsonArrayDonHang = new JSONArray(responseDonHang[0]);
                        JSONArray jsonArrayThongTinDH = new JSONArray(responseDonHang[1]);
                        for (int i=0;i<jsonArrayThongTinDH.length();i++){
                            JSONObject jsonObject = jsonArrayThongTinDH.getJSONObject(i);
                            idThongTin = jsonObject.getInt("id");
                            System.out.println(idThongTin+"id");
                            maDonHang = jsonObject.getInt("madonhang");
                            System.out.println(maDonHang+"ma dh");
                            idSanPham = jsonObject.getInt("masanpham");
                            System.out.println(idSanPham+"ma sp");
                            tenSanPham = jsonObject.getString("tensanpham");
                            System.out.println(tenSanPham+"ten SP");
                            giaSanPham = jsonObject.getInt("giasanpham");
                            System.out.println(giaSanPham+"gia sp");
                            soLuongSanPham = jsonObject.getInt("soluongsanpham");
                            System.out.println(soLuongSanPham+"slsp");
                            arrThongTinDh.add(new ThongTinDonHang(idThongTin,maDonHang,idSanPham,tenSanPham,giaSanPham,soLuongSanPham));
                        }
                        for(int i=0; i<jsonArrayDonHang.length();i++){
                            JSONObject jsonObject = jsonArrayDonHang.getJSONObject(i);
                            idDonhang = jsonObject.getInt("id");
                            status = jsonObject.getInt("status");
                            tenKhachHang = jsonObject.getString("tenkhachhang");
                            soDt = jsonObject.getString("sodienthoai");
                            email = jsonObject.getString("email");
                            ArrayList<ThongTinDonHang> thongTinDonHangs = new ArrayList<>();
                            for(int j=0;j<arrThongTinDh.size();j++){
                                if(arrThongTinDh.get(j).getMaDonHang()==idDonhang){
                                    thongTinDonHangs.add(arrThongTinDh.get(j));
                                }
                            }
                            arrDonHang.add(new DonHang(idDonhang,status,tenKhachHang,email,soDt,thongTinDonHangs));
                            donHangAdapter.notifyDataSetChanged();
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                } else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"No data");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("imei",imei);
                System.out.println(imei+"day la imei");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDonHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        arrDonHang = new ArrayList<>();
        toolbarDonHang = findViewById(R.id.toolbardonhang);
        lvDonHang = findViewById(R.id.listviewdonhang);
        arrDonHang = new ArrayList<>();
        arrThongTinDh = new ArrayList<>();
        donHangAdapter = new DonHangAdapter(getApplicationContext(),arrDonHang);
        lvDonHang.setAdapter(donHangAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }


    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvDonHang.addFooterView(footerview);
                    break;
                case 1:
                    GetData();
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
