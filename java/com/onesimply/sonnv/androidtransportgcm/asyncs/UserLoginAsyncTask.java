package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.HomeFragment;
import com.onesimply.sonnv.androidtransportgcm.MainActivity;
import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.entities.ArrayClass;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;

/**
 * Created by N on 09/03/2016.
 */
public class UserLoginAsyncTask extends AsyncTask<Void, Void, ArrayList<user>> {
    HomeFragment context;
    private ProgressDialog mProgressDialog;
    private GetSetDataFormService getSetDataFormService;

    public UserLoginAsyncTask(HomeFragment context){
        this.context = context;
        mProgressDialog = new ProgressDialog(context.getActivity());
        getSetDataFormService = new GetSetDataFormService();

    }
    @Override
    protected ArrayList<user> doInBackground(Void... params) {
        ArrayList<user> arrayList = new ArrayList<user>();
        arrayList = (ArrayList<user>) getSetDataFormService.getListUserByEmail(MainActivity.emaiLogin);
        return arrayList;
    }
    @Override
    protected void onPreExecute() {
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    @Override
    protected void onPostExecute(ArrayList<user> users) {
        super.onPostExecute(users);
        mProgressDialog.dismiss();
        try {
            context.textView_fullName.setText(users.get(0).getFullName().toString());
            context.textView_email.setText(users.get(0).getEmailLogin().toString());
            context.textView_phone.setText(users.get(0).getPhone().toString());
            context.textView_address.setText(users.get(0).getAddress().toString());
            context.textView_role.setText(ArrayClass.user_role[users.get(0).getRole()]);
            context.role = users.get(0).getRole();
            ArrayClass.loaction_address.add(users.get(0).getAddress());
        }catch (Exception ex){
            Toast.makeText(context.getActivity(), context.getString(R.string.get_data_sevice_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
