package com.example.tnt.cuahangonline.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.ultil.CheckConnection;
import com.example.tnt.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
    EditText edttenkhachhang,edtemail,edtsodienthoai;
    Button btnxacnhan,btntrove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton ();

        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String imei = GetImeiKhachHang();
                final String ten = edttenkhachhang.getText().toString().trim();
                final String sdt = edtsodienthoai.getText().toString().trim();
                final String email = edtemail.getText().toString().trim();
                if (ten.length()>0 && sdt.length()>0 && email.length()>0){
                    //gui mail cho khach hang khi dat hang thanh cong
                    sendEmail();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if (Integer.parseInt(madonhang) >0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng của bạn bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }
                                ){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i= 0;i<MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            hashMap.put("imei", imei);
                           // System.out.println(imei+"day la imei");
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Anhxa() {
        edttenkhachhang =(EditText)  findViewById(R.id.edittexttenkhachhang);
        edtemail =(EditText) findViewById(R.id.edittextemail);
        edtsodienthoai =(EditText) findViewById(R.id.edittextsodienthoai);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        btntrove =(Button) findViewById(R.id.buttontrove);
    }
    private String GetImeiKhachHang() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "Error";
        }
        String imei = tm.getDeviceId();
        return imei;

    }

    private void sendEmail() {
        //Getting content for email
        String email = edtemail.getText().toString().trim();
        String subject = Server.subject;
        String sanpham = "";
        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
            sanpham += MainActivity.manggiohang.get(i).getTensp() + "    " + "x" + MainActivity.manggiohang.get(i).getSoluongsp() + "\n";
        }
        long gia = MainActivity.manggiohang.get(0).getGiasp();
        String msg = "Cảm ơn bạn đã mua hàng tại cửa hàng chúng tôi! \n" +
                "Thông tin đơn hàng: \n" +
                "Sản phẩm: \n" + sanpham + "Tổng giá: " + gia + "vnđ";



        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, msg);

        //Executing sendmail to send email
        sm.execute();
    }

}
