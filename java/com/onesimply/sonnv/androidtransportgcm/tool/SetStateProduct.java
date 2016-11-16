package com.onesimply.sonnv.androidtransportgcm.tool;

import android.app.Activity;
import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by N on 28/03/2016.
 */
public class SetStateProduct {
    public Activity context;
    private RadioButton radioButton_a;
    private RadioButton radioButton_b;
    private RadioButton radioButton_c;
    private TextView textView_a;
    private TextView textView_b;
    public SetStateProduct(Activity context, RadioButton radioButton_a, RadioButton radioButton_b, RadioButton radioButton_c, TextView textView_a, TextView textView_b){
        this.context = context;
        this.radioButton_a =radioButton_a;
        this.radioButton_b =radioButton_b;
        this.radioButton_c = radioButton_c;
        this.textView_a = textView_a;
        this.textView_b = textView_b;
    }
    public void LastPost(){
        this.radioButton_a.setChecked(true);
        this.radioButton_b.setChecked(false);
        this.radioButton_c.setChecked(false);
        this.textView_a.setBackgroundColor(Color.GRAY);
        this.textView_b.setBackgroundColor(Color.GRAY);
    }
    public void HasBeenReceived(){
        this.radioButton_a.setChecked(true);
        this.radioButton_b.setChecked(true);
        this.radioButton_c.setChecked(false);
        this.textView_a.setBackgroundColor(Color.RED);
        this.textView_b.setBackgroundColor(Color.GRAY);
    }
    public void ProductComplete(){
        this.radioButton_a.setChecked(true);
        this.radioButton_b.setChecked(true);
        this.radioButton_c.setChecked(true);
        this.textView_a.setBackgroundColor(Color.RED);
        this.textView_b.setBackgroundColor(Color.RED);
    }
    public void setData(String carricer, int stateId){
        if(carricer.equals("isNull")){
            LastPost();
        }else {
            HasBeenReceived();
        }
        if(stateId ==4){
            ProductComplete();
        }
    }
}
