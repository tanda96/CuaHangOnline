package com.example.tnt.cuahangonline.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.adapter.DienthoaiAdapter;
import com.example.tnt.cuahangonline.adapter.LaptopAdapter;
import com.example.tnt.cuahangonline.model.Sanpham;
import com.example.tnt.cuahangonline.ultil.CheckConnection;
import com.example.tnt.cuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LapTopActivity extends AppCompatActivity {

    Toolbar toolbarlaptop;
    ListView lvlaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> manglaptop;
    int idlaptop = 0;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_top);
       if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Anhxa();
            GetIdloaisp();
            ActionToolbar();
            GetData(page);

        }else {
           CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
           finish();
       }

    }

    private void Anhxa() {
        toolbarlaptop = findViewById(R.id.toolbarlaptop);
        lvlaptop = findViewById(R.id.listviewlaptop);
        manglaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), manglaptop);
        lvlaptop.setAdapter(laptopAdapter);
    }
    private void GetIdloaisp() {
        idlaptop = getIntent().getIntExtra("idloaisanpham", -1);

    }
    private void ActionToolbar() {
        setSupportActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenlaptop = "";
                int Gialaptop = 0;
                String Hinhanhlaptop = "";
                String Motalaptop = "";
                int Idsplaptop = 0;
                if (response != null && response.length() != 2){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i< jsonArray.length(); i ++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenlaptop = jsonObject.getString("tensp");
                            Gialaptop = jsonObject.getInt("giasp");
                            Hinhanhlaptop = jsonObject.getString("hinhanhsp");
                            Motalaptop = jsonObject.getString("motasp");
                            Idsplaptop = jsonObject.getInt("idsanpham");
                            manglaptop.add(new Sanpham(id, Tenlaptop, Gialaptop, Hinhanhlaptop, Motalaptop, Idsplaptop));
                            laptopAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
