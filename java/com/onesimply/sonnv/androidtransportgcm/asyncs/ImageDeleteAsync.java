package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

/**
 * Created by N on 23/03/2016.
 */
public class ImageDeleteAsync extends AsyncTask<Void, Void, Boolean> {
    Activity context = null;
    ProgressDialog mProgressDialog;
    private String id;
    private GetSetDataFormService getSetDataFormService;
    public ImageDeleteAsync(Activity context, String id){
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
        this.id = id;
        getSetDataFormService = new GetSetDataFormService();
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        return getSetDataFormService.deleteImage(this.id);
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setMessage("Waiting....");
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        if(aBoolean){
            Toast.makeText(context, "Đã xóa image", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "xóa image thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
