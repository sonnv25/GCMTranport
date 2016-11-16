package com.onesimply.sonnv.androidtransportgcm.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.entities.product;

import java.util.List;

/**
 * Created by N on 24/02/2016.
 */
public class CustomListPostProductAllAdapter extends BaseAdapter {
    private List<product> productList;
    private LayoutInflater layoutInflater;
    private Context context;
    public CustomListPostProductAllAdapter(Context c, List<product> productList){
        this.context = c;
        this.productList = productList;
        this.layoutInflater = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if ( convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_all_product, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewProductName = (TextView)convertView.findViewById(R.id.textView_productName_all);
            viewHolder.textViewAddress = (TextView)convertView.findViewById(R.id.textView_Address_all);
            viewHolder.textViewDate = (TextView)convertView.findViewById(R.id.textView_date_all);
            viewHolder.textViewTime = (TextView)convertView.findViewById(R.id.textView_Time_all);
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.imageView_flag_all);
            viewHolder.textViewEmailLogin = (TextView) convertView.findViewById(R.id.textView_emailLogin_all);
            viewHolder.textViewId = (TextView) convertView.findViewById(R.id.textView_id_all);
            viewHolder.textViewCarrier = (TextView) convertView.findViewById(R.id.textView_carrier_all);
            viewHolder.textViewAddreRec = (TextView) convertView.findViewById(R.id.textView_addressRec_all);
            viewHolder.textViewEmailCarrier = (TextView) convertView.findViewById(R.id.textView_emailCarrier_all);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        product prd = productList.get(position);
        viewHolder.textViewProductName.setText(prd.getName().toString());
        viewHolder.textViewAddress.setText(prd.getMyAddress().toString());
        viewHolder.textViewDate.setText(prd.getCreateDate().toString());
        viewHolder.textViewTime.setText(prd.getCreateTime().toString());
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(prd.getName().toString().substring(0,1), color);

        viewHolder.imgView.setImageDrawable(textDrawable);
        viewHolder.textViewEmailLogin.setText(prd.getEmailLogin().toString());
        viewHolder.textViewId.setText(prd.getId() + "");
        viewHolder.textViewAddreRec.setText(prd.getAddressRec().toString());
        viewHolder.textViewCarrier.setText(prd.getCarrier().toString());
        viewHolder.textViewEmailCarrier.setText(prd.getEmailCarrier().toString());

        viewHolder.textViewEmailLogin.setVisibility(View.INVISIBLE);
        viewHolder.textViewId.setVisibility(View.INVISIBLE);
        viewHolder.textViewAddreRec.setVisibility(View.INVISIBLE);
        viewHolder.textViewCarrier.setVisibility(View.INVISIBLE);
        viewHolder.textViewEmailCarrier.setVisibility(View.INVISIBLE);
        return convertView;
    }
    public class ViewHolder{
        TextView textViewProductName;
        TextView textViewAddress;
        TextView textViewDate;
        TextView textViewTime;
        ImageView imgView;
        TextView textViewEmailLogin;
        TextView textViewId;
        TextView textViewAddreRec;
        TextView textViewCarrier;
        TextView textViewEmailCarrier;
    }
}
