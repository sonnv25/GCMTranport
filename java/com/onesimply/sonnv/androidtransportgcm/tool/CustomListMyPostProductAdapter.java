package com.onesimply.sonnv.androidtransportgcm.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.entities.product;

import java.util.List;

/**
 * Created by N on 23/02/2016.
 */
public class CustomListMyPostProductAdapter extends BaseAdapter {
    private List<product> listProduct;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListMyPostProductAdapter(Context context, List<product> listProduct){
        this.context = context;
        this.listProduct = listProduct;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if ( convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_my_post, null);
            viewHolder = new ViewHolder();
            viewHolder.txtProductName =(TextView) convertView.findViewById(R.id.textView_productName_all);
            viewHolder.txtCreateDate = (TextView) convertView.findViewById(R.id.textView_date_all);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView_flag_my);
            viewHolder.textViewFlag = (TextView) convertView.findViewById(R.id.textView_flag_my);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        product pdc = listProduct.get(position);
        viewHolder.txtProductName.setText(pdc.getName());
        viewHolder.txtCreateDate.setText(pdc.getCreateDate());
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();
        if (pdc.getCarrier().toString().equals("isNull")){
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.textViewFlag.setVisibility(View.INVISIBLE);
        }
        else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageView.setBackgroundColor(color);
            viewHolder.textViewFlag.setVisibility(View.VISIBLE);
        }
        if(pdc.getStateId() == 4){
            viewHolder.textViewFlag.setText("Đã hoàn thành");
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageView.setBackgroundColor(color);
            viewHolder.textViewFlag.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    static class ViewHolder{
        TextView txtProductName;
        TextView txtCreateDate;
        ImageView imageView;
        TextView textViewFlag;
    }
}
