package com.onesimply.sonnv.androidtransportgcm;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesimply.sonnv.androidtransportgcm.asyncs.UpdateUserLoginAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.asyncs.UserLoginAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.DialogMessage;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    public TextView textView_fullName;
    public TextView textView_email;
    public TextView textView_phone;
    public TextView textView_address;
    public TextView textView_role;
    public ArrayList<user> dsUser;
    public user us;
    public int role;
    private ImageView imageView_phone;
    private ImageView imageView_address;
    private ImageView imageView_fullName;
    public int index = 0;
    private DialogMessage dialogMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dialogMessage  = new DialogMessage(getActivity());
        textView_fullName = (TextView) view.findViewById(R.id.textView_fullName_us);
        textView_email = (TextView) view.findViewById(R.id.textView_email_us);
        textView_phone = (TextView) view.findViewById(R.id.textView_phone_us);
        textView_address = (TextView) view.findViewById(R.id.textView_address_us);
        textView_role = (TextView) view.findViewById(R.id.textView_role_us);
        imageView_phone = (ImageView) view.findViewById(R.id.imageView_edit_phone);
        imageView_address = (ImageView) view.findViewById(R.id.imageView_edit_address);
        imageView_fullName = (ImageView) view.findViewById(R.id.imageView_edit_fullname);
        getUser();
        imageView_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                openDialogEdit(getString(R.string.phone), textView_phone.getText().toString(), InputType.TYPE_CLASS_PHONE);
            }
        });
        imageView_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index =2;
                openDialogEdit(getString(R.string.address), textView_address.getText().toString(), InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
            }
        });
        imageView_fullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 3;
                openDialogEdit(getString(R.string.full_name), textView_fullName.getText().toString(), InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            }
        });
        return  view;
    }
    private void getUser(){
        UserLoginAsyncTask userLoginAsyncTask = new UserLoginAsyncTask(this);
        userLoginAsyncTask.execute();
    }
    private void updateUserLogin(){
        UpdateUserLoginAsyncTask updateUserLoginAsyncTask = new UpdateUserLoginAsyncTask(this);
        updateUserLoginAsyncTask.execute();
    }
    private void openDialogEdit(String tView, String eView, int inputType){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(tView);
        dialog.setContentView(R.layout.dialog_edit_user);
        final EditText editText = (EditText) dialog.findViewById(R.id.editText_dialog_edit);

        editText.setInputType(inputType);
        Button button = (Button) dialog.findViewById(R.id.button_dialog_edit);
        Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel_edit );
        editText.setText(eView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                us = new user();
                us.setEmailLogin(textView_email.getText().toString());
                if(index == 1){
                    if(isPhoneValid(editText.getText().toString())) {
                        us.setFullName(textView_fullName.getText().toString());
                        us.setPhone(editText.getText().toString());
                        us.setAddress(textView_address.getText().toString());
                        isValid = true;
                    }else {isValid = false; dialogMessage.ShowDialog("x", "phone");}
                }else if(index ==2) {
                    if(isAddressValid(editText.getText().toString())) {
                        us.setFullName(textView_fullName.getText().toString());
                        us.setPhone(textView_phone.getText().toString());
                        us.setAddress(editText.getText().toString());
                        isValid = true;
                    }else {isValid = false; dialogMessage.ShowDialog("x", "address");}
                }
                else if(index ==3){
                    if(isFullNameValid(editText.getText().toString())) {
                        us.setFullName(editText.getText().toString());
                        us.setPhone(textView_phone.getText().toString());
                        us.setAddress(textView_address.getText().toString());
                        isValid = true;
                    }else {isValid = false; dialogMessage.ShowDialog("x", "full name");}
                }
                us.setRole(role);
                if (isValid) {
                    updateUserLogin();
                    dialog.dismiss();
                }
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
    private boolean isPhoneValid(String phone){
        return phone.length()>7 && phone.length() <12;
    }
    private boolean isAddressValid(String address){
        return !address.isEmpty();
    }
    private boolean isFullNameValid(String name){
        return !name.isEmpty();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

}
