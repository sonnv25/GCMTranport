package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

/**
 * Created by N on 30/03/2016.
 */
public class RatingUserAsync extends AsyncTask<Void, Void, Boolean>{
    Activity context;
    ProgressDialog mProgressDialog;
    private String email;
    private int rate;
    private int productId;
    private GetSetDataFormService getSetDataFormService;
    private Button button;
    public RatingUserAsync(Activity context, String email, int rate, int productId, Button button){
        this.context = context;
        this.email = email;
        this.rate = rate;
        this.productId =productId;
        this.button = button;
        mProgressDialog = new ProgressDialog(context);
        getSetDataFormService = new GetSetDataFormService();
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        boolean rs = getSetDataFormService.insertUserRate(email, rate, productId);
        return rs;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setMessage(context.getString(R.string.waiting));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        if(aBoolean){
            Toast.makeText(context, context.getString(R.string.complete), Toast.LENGTH_SHORT).show();
            button.setVisibility(View.GONE);
        }else {
            Toast.makeText(context, context.getString(R.string.fail), Toast.LENGTH_SHORT).show();
        }
    }
}
