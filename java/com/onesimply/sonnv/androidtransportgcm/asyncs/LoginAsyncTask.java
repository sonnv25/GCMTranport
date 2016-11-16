package com.onesimply.sonnv.androidtransportgcm.asyncs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.LoginActivity;
import com.onesimply.sonnv.androidtransportgcm.MainActivity;
import com.onesimply.sonnv.androidtransportgcm.R;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

/**
 * Created by N on 08/03/2016.
 */
public class LoginAsyncTask extends AsyncTask<Void, Void, Boolean> {
    LoginActivity context = null;
    private String user;
    private String password;
    public LoginAsyncTask(LoginActivity context, String user, String password){
        this.context = context;
        this.user = user;
        this.password = password;
        this.context.mProgressDialog= new ProgressDialog(context);
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        GetSetDataFormService sv = new GetSetDataFormService();
        Boolean result = sv.checkLogin(user, password);
        return result;
    }

    @Override
    protected void onPreExecute() {
        context.mProgressDialog.setTitle("Đang đăng nhập..");
        context.mProgressDialog.setMessage("Vui lòng chờ ... ");
        context.mProgressDialog.setCancelable(false);
        context.mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        context.mProgressDialog.dismiss();
        context.checkLogin = aBoolean;
        if(context.checkLogin){
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("user",user);
            context.savingPrefereces(user, password);
            context.startActivity(intent);
        }else {
            Toast.makeText(context,context.getString(R.string.login_fail), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
