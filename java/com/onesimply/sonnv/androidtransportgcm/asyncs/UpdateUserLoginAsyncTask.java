package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.onesimply.sonnv.androidtransportgcm.HomeFragment;
import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.tool.DialogMessage;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

/**
 * Created by N on 09/03/2016.
 */
public class UpdateUserLoginAsyncTask extends AsyncTask<Void, Void, Boolean> {
    HomeFragment context;
    private GetSetDataFormService getSetDataFormService;
    private ProgressDialog mProgressDialog;
    private DialogMessage dialogMessage;
    public UpdateUserLoginAsyncTask(HomeFragment context){
        this.context = context;
        this.getSetDataFormService = new GetSetDataFormService();
        this.mProgressDialog = new ProgressDialog(context.getActivity());
        this.dialogMessage = new DialogMessage(context.getActivity());
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean rs = getSetDataFormService.UpdateUserLogin(context.us);
        return rs;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if(aBoolean){
            if (context.index ==1){
                context.textView_phone.setText(context.us.getPhone().toString());
            }else if(context.index ==2){
                context.textView_address.setText(context.us.getAddress().toString());
            }else if(context.index ==3){
                context.textView_fullName.setText(context.us.getFullName().toString());
            }
        }else {
            dialogMessage.ShowDialog("Lỗi", context.getString(R.string.update_sevice_error));
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
