package com.example.tnt.cuahangonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.activity.DonHangActivity;
import com.example.tnt.cuahangonline.model.DonHang;
import com.example.tnt.cuahangonline.ultil.CheckConnection;
import com.example.tnt.cuahangonline.ultil.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;
import static com.example.tnt.cuahangonline.activity.DonHangActivity.*;


public class DonHangAdapter  extends BaseAdapter {


    Context context;
    ArrayList<DonHang> arrayDonHang;
    public DonHangAdapter(Context context, ArrayList<DonHang> donHangs){
        this.context = context;
        this.arrayDonHang = donHangs;
    }

    @Override
    public int getCount() {
        return arrayDonHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDonHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        public TextView textviewtenkhachhang, textviewtinhtrang, textviewemail;
        public Button btnHuy, btnXem;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(null==view){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_donhang, null);
            viewHolder.textviewtenkhachhang = (TextView) view.findViewById(R.id.textviewtenkhachhang);
            viewHolder.textviewtinhtrang = (TextView) view.findViewById(R.id.textviewtinhtrang);
            viewHolder.btnHuy = (Button) view.findViewById(R.id.btnHuy);
            viewHolder.textviewemail = (TextView) view.findViewById(R.id.textviewemail);
            viewHolder.btnXem = (Button) view.findViewById(R.id.btnXem);
            view.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) view.getTag();
        }
        final DonHang donHang =(DonHang) getItem(i);
        viewHolder.textviewtenkhachhang.setText(donHang.getTenKhachHang());
        viewHolder.textviewemail.setText(donHang.getEmailKhachHang());
        if(0==donHang.getStatus()){
            viewHolder.textviewtinhtrang.setText("Chờ xác nhận");
            viewHolder.btnHuy.setVisibility(View.VISIBLE);
        } else if(1==donHang.getStatus()){
            viewHolder.textviewtinhtrang.setText("Đang giao");
            viewHolder.btnHuy.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textviewtinhtrang.setText("Đã giao");
            viewHolder.btnHuy.setVisibility(View.INVISIBLE);
        }
        viewHolder.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (1 == donHang.getStatus()) {
                    CheckConnection.ShowToast_Short(context, "Không thể hủy đơn hàng này, vui lòng hủy qua mail hoặc điện thoại");
                } else if (2 == donHang.getStatus()) {
                    CheckConnection.ShowToast_Short(context, "Không thể hủy đơn hàng đã giao");
                } else {
                    final int status = -1;
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanThongTinDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response + " day la rs");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<String, String>();
                            param.put("idclick", String.valueOf(donHang.getIdDonHang()));
                            param.put("statusclick", String.valueOf(status));
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                    arrayDonHang.remove(donHang);
                    CheckConnection.ShowToast_Short(context, "Hủy thành công");
                    notifyDataSetChanged();
                }
            }
        });
        viewHolder.btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sanpham = "";
                int gia = 0;
                String id = String.valueOf(arrayDonHang.get(i).getThongTinDonHangs().get(0).getId());
                for(int j=0;j<arrayDonHang.get(i).getThongTinDonHangs().size();j++){
                    sanpham+= arrayDonHang.get(i).getThongTinDonHangs().get(j).getTenSanPham()+"   x"+arrayDonHang.get(i).getThongTinDonHangs().get(j).getSoLuongSanPham()+" \n";
                }
                gia = arrayDonHang.get(i).getThongTinDonHangs().get(0).getGiaSanPham();
                /*AlertDialog.Builder ab = new AlertDialog.Builder(DonHangActivity.getActivity(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
                        .setTitle(id)
                        .setMessage("Sản Phẩm: \n"+sanpham+"Giá: "+ gia)
                        .setPositiveButton("OK",null);
                AlertDialog dialog = ab.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();*/
                CheckConnection.ShowToast_Short(context,"Tên sản phẩm: \n"+sanpham+"Tổng giá: "+gia);

            }
        });
        return view;
    }
}
