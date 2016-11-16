package com.onesimply.sonnv.androidtransportgcm.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.entities.user;

import java.util.ArrayList;

/**
 * Created by N on 15/03/2016.
 */
public class CustomListAdapterSelectUserPost extends BaseAdapter {
    private ArrayList<user> arrayList;
    private LayoutInflater layoutInflater;
    private Context context;
    public  CustomListAdapterSelectUserPost(Context context, ArrayList<user> list){
        this.context = context;
        this.arrayList = list;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_user_slect_type_post, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView_name_select);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar_name_slect);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        user us = arrayList.get(position);
        viewHolder.textView.setText(us.getFullName().toString());
        int n = us.getRate();
        viewHolder.ratingBar.setRating(n);
        return convertView;
    }
    static class ViewHolder{
        TextView textView;
        RatingBar ratingBar;
    }
}
