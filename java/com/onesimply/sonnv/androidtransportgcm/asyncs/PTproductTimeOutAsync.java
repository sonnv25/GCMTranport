package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.onesimply.sonnv.androidtransportgcm.MainActivity;
import com.onesimply.sonnv.androidtransportgcm.ProductTimeoutFragment;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;

/**
 * Created by N on 25/03/2016.
 */
public class PTproductTimeOutAsync extends AsyncTask<Void, Void, ArrayList<product>> {
    ProductTimeoutFragment context;
    private GetSetDataFormService getSetDataFormService;
    ProgressDialog mProgressDialog;
    public PTproductTimeOutAsync(ProductTimeoutFragment context){
        this.context = context;
        getSetDataFormService = new GetSetDataFormService();
        //mProgressDialog = new ProgressDialog(context.getActivity());
    }
    @Override
    protected ArrayList<product> doInBackground(Void... params) {
        ArrayList<product> arrayList = (ArrayList<product>) getSetDataFormService.getMyProduct(MainActivity.emaiLogin, 1);
        return arrayList;
    }

    @Override
    protected void onPreExecute() {
       /* mProgressDialog.setMessage("Waiting ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();*/
    }

    @Override
    protected void onPostExecute(ArrayList<product> products) {
        super.onPostExecute(products);
        /*if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }*/
        context.dsProduct.clear();
        context.dsProduct.addAll(products);
        context.adapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
