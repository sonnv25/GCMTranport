package com.onesimply.sonnv.androidtransportgcm.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by N on 09/03/2016.
 */
public class DialogMessage {
    Activity context;
    public DialogMessage(Activity context){
        this.context = context;
    }
    public void ShowDialog(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setIcon(android.R.drawable.stat_sys_warning);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
