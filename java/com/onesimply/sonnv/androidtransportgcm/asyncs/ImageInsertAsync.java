package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.PostProductActivity;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

/**
 * Created by N on 18/03/2016.
 */
public class ImageInsertAsync extends AsyncTask<Void, Void, Boolean> {
    PostProductActivity context = null;
    GetSetDataFormService getSetDataFormService;
    ProgressDialog mProgressDialog;
    private String id;
    public ImageInsertAsync(PostProductActivity context, String id){
        this.context = context;
        getSetDataFormService = new GetSetDataFormService();
        this.id = id;
        this.mProgressDialog = new ProgressDialog(context);
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        boolean rs = getSetDataFormService.InserImage(id, context.base64ImageString);
        return rs;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setMessage("Waiting ....");
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
            //context.imageView_post_a.setImageBitmap(context.bitmap);
            //context.imageIdArray.add(context.imageId);
            Toast.makeText(context, "Tải ảnh lên thành công!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Không tải được ảnh lên!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
