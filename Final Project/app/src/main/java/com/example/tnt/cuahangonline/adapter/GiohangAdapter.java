package com.example.tnt.cuahangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tnt.cuahangonline.R;
import com.example.tnt.cuahangonline.activity.MainActivity;
import com.example.tnt.cuahangonline.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {

    Context context;
    ArrayList<Giohang> arraygiohang;
    public GiohangAdapter(Context context,ArrayList<Giohang> arraygiohang){
        this.context = context;
        this.arraygiohang = arraygiohang;
    }
    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arraygiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus, btnvalues, btnplus, btnXoa;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang =(TextView) view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = (TextView) view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = (ImageView) view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = (Button) view.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = (Button)view.findViewById(R.id.buttonvalues);
            viewHolder.btnplus =(Button) view.findViewById(R.id.buttonplus);
            viewHolder.btnXoa = (Button) view.findViewById(R.id.buttonxoa);
            view.setTag(viewHolder);
            } else {
            viewHolder = (ViewHolder) view.getTag();
            }
            Giohang giohang= (Giohang) getItem(i);
            viewHolder.txttengiohang.setText(giohang.getTensp());
            DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
            viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + "Đ");
            Picasso.with(context).load(giohang.getHinhsp())
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.error)
                    .into(viewHolder.imggiohang);
            viewHolder.btnvalues.setText(giohang.getSoluongsp() + "");
            int sl  = Integer.parseInt(viewHolder.btnvalues.getText().toString());
            if (sl >=10 ){
                viewHolder.btnplus.setVisibility(View.INVISIBLE);
                viewHolder.btnminus.setVisibility(View.VISIBLE);

            }else if (sl <= 1){
                viewHolder.btnminus.setVisibility(View.INVISIBLE);

            }else if (sl >= 1){
                viewHolder.btnminus.setVisibility(View.VISIBLE);
                viewHolder.btnplus.setVisibility(View.VISIBLE);

            }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) + 1;
                int slht = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + "Đ");
                com.example.tnt.cuahangonline.activity.Giohang.EventUltil();
                if (slmoinhat > 9) {
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));

                    }
                }
            });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int slmoinhat = Integer.parseInt(finalViewHolder1.btnvalues.getText().toString()) -1;
                    int slht = MainActivity.manggiohang.get(i).getSoluongsp();
                    long giaht = MainActivity.manggiohang.get(i).getGiasp();
                    MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                    long giamoinhat = (giaht * slmoinhat) / slht;
                    MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                    DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                    finalViewHolder1.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + "Đ");
                    com.example.tnt.cuahangonline.activity.Giohang.EventUltil();
                    if (slmoinhat < 2){
                        finalViewHolder1.btnminus.setVisibility(View.INVISIBLE);
                        finalViewHolder1.btnplus.setVisibility(View.VISIBLE);
                        finalViewHolder1.btnvalues.setText(String.valueOf(slmoinhat));
                    }else {
                        finalViewHolder1.btnminus.setVisibility(View.VISIBLE);
                        finalViewHolder1.btnplus.setVisibility(View.VISIBLE);
                        finalViewHolder1.btnvalues.setText(String.valueOf(slmoinhat));
                    }
                }
            });
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.manggiohang.remove(i);
                notifyDataSetChanged();
                com.example.tnt.cuahangonline.activity.Giohang.EventUltil();

            }
        });
            return view;
    }
}
