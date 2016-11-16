package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.onesimply.sonnv.androidtransportgcm.RegisterActivity;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

/**
 * Created by N on 09/03/2016.
 */
public class RegisterAsyncTask extends AsyncTask<Void, Void, Boolean> {
    RegisterActivity context;
    GetSetDataFormService getSetDataFormService;
    ProgressDialog mProgressDialog;

    Boolean check;
    public RegisterAsyncTask(RegisterActivity context){
        this.context = context;
        getSetDataFormService = new GetSetDataFormService();
        mProgressDialog = new ProgressDialog(context);
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean rs = getSetDataFormService.insertUserLogin(context.us);
        return rs;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setTitle("Đăng đăng ký .. ");
        mProgressDialog.setMessage("Vui lòng chờ ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        this.check = aBoolean;
        if(check){
            context.dialogMessage.ShowDialog("Save", "Đăng ký tài khoản thành công.");
            context.finish();
        }else {
            context.dialogMessage.ShowDialog("Lỗi" , "Không thể đăng ký.");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
