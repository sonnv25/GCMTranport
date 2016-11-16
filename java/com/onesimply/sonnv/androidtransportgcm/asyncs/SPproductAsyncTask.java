package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.onesimply.sonnv.androidtransportgcm.ProductInfoFragment;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;

/**
 * Created by N on 08/03/2016.
 */
public class SPproductAsyncTask extends AsyncTask<Void, Void, ArrayList<product>> {
    ProductInfoFragment context = null;
    private ProgressDialog mProgressDialog;
    public SPproductAsyncTask(ProductInfoFragment context){
        this.context = context;
        mProgressDialog = new ProgressDialog(context.getActivity());
    }
    @Override
    protected ArrayList<product> doInBackground(Void... params) {
        GetSetDataFormService sv = new GetSetDataFormService();
        ArrayList<product> products = new ArrayList<product>();
        products = (ArrayList<product>) sv.getProductAll(0);
        return products;
    }
    @Override
    protected void onPostExecute(ArrayList<product> products) {
        super.onPostExecute(products);
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        context.dsProduct.clear();
        context.dsProduct.addAll(products);
        context.adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setTitle("Đang tải dữ liệu...");
        mProgressDialog.setMessage("Vui lòng chờ..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
