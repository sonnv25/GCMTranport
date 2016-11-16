package com.onesimply.sonnv.androidtransportgcm.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.entities.SlideMenuItem;

import java.util.ArrayList;

/**
 * Created by N on 07/03/2016.
 */
public class SlideMenuListAdapter extends BaseAdapter{

    private Context context;
    ArrayList<SlideMenuItem> slideMenuItems;

    public SlideMenuListAdapter(Context context, ArrayList<SlideMenuItem> slideMenuItems) {
        this.context = context;
        this.slideMenuItems = slideMenuItems;
    }

    @Override
    public int getCount() {
        return slideMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return slideMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.slidingmenu_drawer_list_item, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_slide);
        TextView textView_Title = (TextView) convertView.findViewById(R.id.title_slide);
        TextView textView_Count = (TextView) convertView.findViewById(R.id.counter_slide);

        imageView.setImageResource(slideMenuItems.get(position).getIcon());
        textView_Title.setText(slideMenuItems.get(position).getTitle());
        if(slideMenuItems.get(position).isCountVisible()){
            textView_Count.setText(slideMenuItems.get(position).getCount());
        }else {
            textView_Count.setVisibility(View.GONE);
        }
        return convertView;
    }
}
