package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.RegisterActivity;
import com.onesimply.sonnv.androidtransportgcm.entities.ArrayClass;
import com.onesimply.sonnv.androidtransportgcm.tool.ConmadLineLocation;

/**
 * Created by N on 08/03/2016.
 */
public class LocationAsyncTask extends AsyncTask<Void, Void, String> {

    RegisterActivity context = null;
    public LocationAsyncTask(RegisterActivity context){
        this.context = context;
        this.context.mProgressDialog = new ProgressDialog(context);
    }
    @Override
    protected String doInBackground(Void... params) {
        String address = "";

        ConmadLineLocation cmd = new ConmadLineLocation(context);
        cmd.getMyLocation();
        if(ArrayClass.lat ==0 && ArrayClass.lng == 0){
            Toast.makeText(context, context.getString(R.string.location_error), Toast.LENGTH_SHORT).show();
        }else {
            address = cmd.showAddress(ArrayClass.lat, ArrayClass.lng);
        }
        return address;
    }
    @Override
    protected void onPreExecute() {
        context.mProgressDialog.setTitle("Đang lấy vị trí của bạn..");
        context.mProgressDialog.setMessage("Vui lòng chờ ...");
        context.mProgressDialog.setCancelable(false);
//        context.mProgressDialog.show();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        context.mProgressDialog.dismiss();
        context.editText_address.setText(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
