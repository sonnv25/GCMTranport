package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.PostProductActivity;
import com.onesimply.sonnv.androidtransportgcm.tool.DialogMessage;

/**
 * Created by N on 23/03/2016.
 */
public class ImageConvertAsync extends AsyncTask<Void, Void, String> {
    PostProductActivity context;
    DialogMessage dialogMessage;
    private String id;
    public ImageConvertAsync(PostProductActivity context, String id){
        this.context = context;
        dialogMessage = new DialogMessage(context);
        this.id = id;
    }
    @Override
    protected String doInBackground(Void... params) {
        String kq = Base64.encodeToString(context.imageArray, Base64.DEFAULT);
        return kq;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Đang dịch..", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, "Đã dịch xong!", Toast.LENGTH_SHORT).show();
        //dialogMessage.ShowDialog("x" , s);
        context.base64ImageString = s;
        ImageInsertAsync imageInsertAsync = new ImageInsertAsync(context, id);
        imageInsertAsync.execute();
    }
}
