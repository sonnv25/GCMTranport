package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.onesimply.sonnv.androidtransportgcm.MyPostFragment;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;

/**
 * Created by N on 14/03/2016.
 */
public class PTmyPostAsyncTask extends AsyncTask<Void, Void, ArrayList<product>> {
    MyPostFragment context;
    private GetSetDataFormService getSetDataFormService;
    private ProgressDialog mProgressDialog;
    public PTmyPostAsyncTask(MyPostFragment context){
        this.context = context;
        getSetDataFormService = new GetSetDataFormService();
        mProgressDialog = new ProgressDialog(context.getActivity());
    }
    @Override
    protected ArrayList<product> doInBackground(Void... params) {
        ArrayList<product> list = (ArrayList<product>) getSetDataFormService.getMyProduct(context.email, 0);
        ArrayList<product> arrayList = (ArrayList<product>) getSetDataFormService.getMyProduct(context.email, 4);
        list.addAll(arrayList);
        return list;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<product> products) {
        super.onPostExecute(products);
        mProgressDialog.dismiss();
        context.dsProduct.clear();
        context.dsProduct.addAll(products);
        context.adapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
