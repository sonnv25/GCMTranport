package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.PostProductActivity;
import com.onesimply.sonnv.androidtransportgcm.tool.DialogMessage;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;

/**
 * Created by N on 11/03/2016.
 */
public class PTpostProductAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {
    PostProductActivity context;
    private ProgressDialog mProgressDialog;
    private DialogMessage dialogMessage;
    private GetSetDataFormService getSetDataFormService;
    public PTpostProductAsyncTask(PostProductActivity context){
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
        dialogMessage = new DialogMessage(context);
        getSetDataFormService = new GetSetDataFormService();
    }
    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        ArrayList<String> list = new ArrayList<String>();
        list = getSetDataFormService.GetProductCategory();

        return list;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        mProgressDialog.dismiss();
        if(strings!=null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            context.spinner_category.setAdapter(adapter);
        }else {
            Toast.makeText(context, "Khong lay dc danh sach danh muc", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
