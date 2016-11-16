package com.onesimply.sonnv.androidtransportgcm.tool;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.RegisterActivity;

/**
 * Created by N on 08/03/2016.
 */
public class SpinnerAdapter extends ArrayAdapter {

    RegisterActivity context;
    public SpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
       this.context = (RegisterActivity) context;
    }
    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {

// Inflating the layout for the custom Spinner
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.list_item_spinner, parent, false);

// Declaring and Typecasting the textview in the inflated layout
        TextView tvLanguage = (TextView) layout
                .findViewById(R.id.textView_category_spinner);

// Setting the text using the array
        tvLanguage.setText(context.Languages[position]);

// Setting the color of the text
        tvLanguage.setTextColor(Color.rgb(75, 180, 225));

// Declaring and Typecasting the imageView in the inflated layout
        ImageView img = (ImageView) layout.findViewById(R.id.imageView_category_spinner);

// Setting an image using the id's in the array
        img.setImageResource(context.images[position]);

// Setting Special atrributes for 1st element
        if (position == 0) {
// Removing the image view
            img.setVisibility(View.GONE);
// Setting the size of the text

// Setting the text Color
            tvLanguage.setTextColor(Color.BLACK);
        }
        return layout;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public Object getItem(int position) {
        return context.Languages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return context.Languages.length;
    }
}
