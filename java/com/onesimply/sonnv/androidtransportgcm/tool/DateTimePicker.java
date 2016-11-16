package com.onesimply.sonnv.androidtransportgcm.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by N on 14/03/2016.
 */
public class DateTimePicker {


    private int mYear;
    private int mMonth;
    private int mDay;
    public DateTimePicker(){
    }
    public String formatDateENtoVN(String textView){
        SimpleDateFormat simpleDateFormatVN = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDateFormatEn = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = simpleDateFormatEn.parse(textView);
            return simpleDateFormatVN.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String formDateDateToStringEN(Date date){
        SimpleDateFormat simpleDateFormatEn = new SimpleDateFormat("yyyy-MM-dd");

        String txtDate = simpleDateFormatEn.format(date);
        return txtDate;

    }
    public Date formatStringtoDate(String text){
        SimpleDateFormat simpleDateFormatVN = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDateFormatEn = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormatVN.parse(text);
            String txtDate = simpleDateFormatEn.format(date);
            return simpleDateFormatEn.parse(txtDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
