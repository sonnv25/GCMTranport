package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by N on 24/03/2016.
 */
public class UpdateCarricerAsync extends AsyncTask<Void, Void, Boolean> {
    Activity context;
    private GetSetDataFormService getSetDataFormService;
    ProgressDialog mProgressDialog;
    private int id;
    private String email;
    private int stateId;
    public  UpdateCarricerAsync(Activity context, int productId, String emailCarricer, int stateId){
        this.context = context;
        getSetDataFormService = new GetSetDataFormService();
        mProgressDialog = new ProgressDialog(context);
        this.id = productId;
        this.email = emailCarricer;
        this.stateId =stateId;
    }
    @Override
    protected Boolean doInBackground(Void... params) {

        List<user> userList = new ArrayList<user>();
        userList = getSetDataFormService.getListUserByEmail(email);
        boolean uS =false;
        if(userList !=null) {
            boolean uC = getSetDataFormService.updateCarrier(id, email, userList.get(0).getFullName().toString());
            if(uC){
                uS = getSetDataFormService.updateStateIdProduct(id, stateId);
            }
        }
        return uS;
    }
    @Override
    protected void onPreExecute() {
        mProgressDialog.setMessage("Waiting....");
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
        }
        else {
            Toast.makeText(context, "Nhận lỗi", Toast.LENGTH_SHORT).show();
        }
    }
}
