package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.PostProductActivity;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;

/**
 * Created by N on 15/03/2016.
 */
public class ImportUserToListSelectAsyncTask extends AsyncTask<Void, Void, ArrayList<user>> {
    PostProductActivity context;
    ProgressBar mProgressBar;
    private GetSetDataFormService getSetDataFormService;
    public  ImportUserToListSelectAsyncTask(PostProductActivity context){
        this.context = context;
        getSetDataFormService = new GetSetDataFormService();
    }
    @Override
    protected ArrayList<user> doInBackground(Void... params) {
        ArrayList<user> arrayList = (ArrayList<user>) getSetDataFormService.getListUser(1);
        return arrayList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Đang tải ....", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(ArrayList<user> users) {
        super.onPostExecute(users);
        context.dsUser.clear();
        context.dsUser.addAll(users);
        context.adapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
