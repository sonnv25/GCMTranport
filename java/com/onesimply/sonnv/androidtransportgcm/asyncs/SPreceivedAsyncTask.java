package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.onesimply.sonnv.androidtransportgcm.MainActivity;
import com.onesimply.sonnv.androidtransportgcm.ReceivedProductFragment;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;

/**
 * Created by N on 08/03/2016.
 */
public class SPreceivedAsyncTask extends AsyncTask<Void, Void, ArrayList<product>> {
    ReceivedProductFragment context = null;
    private ProgressDialog mProgressDialog;
    public SPreceivedAsyncTask(ReceivedProductFragment context){
        this.context =context;
        mProgressDialog = new ProgressDialog(context.getActivity());
    }
    @Override
    protected ArrayList<product> doInBackground(Void... params) {
        GetSetDataFormService sv = new GetSetDataFormService();
        ArrayList<product> products = (ArrayList<product>) sv.getProductReceived(MainActivity.emaiLogin);
        return products;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setTitle("Đăng tải..");
        mProgressDialog.setMessage("Vui lòng chờ ... ");
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
