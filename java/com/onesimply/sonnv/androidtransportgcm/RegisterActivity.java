package com.onesimply.sonnv.androidtransportgcm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.onesimply.sonnv.androidtransportgcm.asyncs.LocationAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.asyncs.RegisterAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.ConmadLineLocation;
import com.onesimply.sonnv.androidtransportgcm.tool.DialogMessage;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;
import com.onesimply.sonnv.androidtransportgcm.tool.SpinnerAdapter;

public class RegisterActivity extends AppCompatActivity {

    public EditText editText_address;
    public user us;
    public int role;
    public ProgressDialog mProgressDialog;
    EditText editText_email;
    EditText editText_password;
    EditText editText_phone;
    EditText editText_fullName;
    public  DialogMessage dialogMessage;
    GetSetDataFormService getSetDataFormService;
    CheckBox checkBox_getLocation;
    Spinner spinner_categoryUser;
    ConmadLineLocation cmd;
    Button button_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        editText_address = (EditText) findViewById(R.id.editText_address_reg);
        checkBox_getLocation = (CheckBox) findViewById(R.id.checkBox_getLocation);
        spinner_categoryUser = (Spinner) findViewById(R.id.spinner_category_user);
        editText_email = (EditText) findViewById(R.id.editText_email_reg);
        editText_password = (EditText) findViewById(R.id.editText_password_reg);
        editText_fullName = (EditText) findViewById(R.id.editText_fullname_reg);
        editText_phone = (EditText) findViewById(R.id.editText_phone_reg);
        dialogMessage = new DialogMessage(this);
        getSetDataFormService = new GetSetDataFormService();
        spinner_categoryUser.setAdapter(new SpinnerAdapter(this, R.layout.list_item_spinner, Languages));
        button_register = (Button) findViewById(R.id.button_reg);
        cmd = new ConmadLineLocation(this);
        checkBox_getLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox_getLocation.isChecked()) {
                    if (cmd.checkLocationSevicesEnabled()) {
                        getLocation();
                    } else {
                        dialogMessage.ShowDialog("Location", "Chua mo ket noi gps");
                    }
                }
            }
        });
        spinner_categoryUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(editText_email.getText().toString())) {
                    if (isPasswordValid(editText_password.getText().toString())){
                        if (!isFullNameValid(editText_fullName.getText().toString().trim())) {
                            if (isPhoneValid(editText_phone.getText().toString().trim())) {
                                if (isRoleValid(role)) {
                                    us = new user();
                                    us.setEmailLogin(editText_email.getText().toString());
                                    us.setPassword(editText_password.getText().toString());
                                    us.setFullName(editText_fullName.getText().toString());
                                    us.setPhone(editText_phone.getText().toString());
                                    us.setAddress(editText_address.getText().toString());
                                    us.setRole(role);
                                    us.setStatus("1");
                                    startRegister();
                                } else {
                                    dialogMessage.ShowDialog("x", "role");
                                }
                            } else {
                                dialogMessage.ShowDialog("x", "phone");
                            }
                        } else {
                            dialogMessage.ShowDialog("x", "name");
                        }
                    } else {
                        dialogMessage.ShowDialog("x", "password");
                    }
                } else {
                    dialogMessage.ShowDialog("x", "email");
                }
                //dialogMessage.ShowDialog("x", );
            }
        });
    }
    private void startRegister(){
        RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(this);
        registerAsyncTask.execute();
    }
    private boolean isEmailValid(String email){
        return email.contains("@");
    }
    private boolean isPasswordValid(String password){
        return password.length() > 4;
    }
    private boolean isFullNameValid(String name){
        return name.isEmpty();
    }
    private boolean isPhoneValid(String phone){
        return (phone.length() < 12 && phone.length() > 7);
    }
    private boolean isRoleValid(int role){
        return role > 0;
    }
    public String[] Languages = { "-- Chọn --", "Người đăng", "Người vận chuyển",
            "Người nhận", "Cả 3"};
    // Declaring the Integer Array with resourse Id's of Images for the Spinners
    public Integer[] images = { 0, android.R.drawable.ic_menu_mylocation, android.R.drawable.ic_menu_recent_history,
            android.R.drawable.ic_menu_agenda, android.R.drawable.ic_menu_sort_alphabetically};
    private void getLocation(){
        LocationAsyncTask locationAsyncTask = new LocationAsyncTask(this);
        locationAsyncTask.execute();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
