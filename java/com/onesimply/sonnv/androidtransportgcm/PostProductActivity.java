package com.onesimply.sonnv.androidtransportgcm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.onesimply.sonnv.androidtransportgcm.asyncs.ImageConvertAsync;
import com.onesimply.sonnv.androidtransportgcm.asyncs.ImageDeleteAsync;
import com.onesimply.sonnv.androidtransportgcm.asyncs.ImportUserToListSelectAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.asyncs.PTpostProductAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.entities.ArrayClass;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.CountdownTimer;
import com.onesimply.sonnv.androidtransportgcm.tool.CustomListAdapterSelectUserPost;
import com.onesimply.sonnv.androidtransportgcm.tool.DateTimePicker;
import com.onesimply.sonnv.androidtransportgcm.tool.DialogMessage;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;
import com.onesimply.sonnv.androidtransportgcm.tool.OnCountdownFinish;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.onesimply.sonnv.androidtransportgcm.ServerTask.DISPLAY_MESSAGE_ACTION;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.EXTRA_MESSAGE;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.TAG;

public class PostProductActivity extends AppCompatActivity {
    public int productId;
    private AsyncTask<Void, Void, Integer> asyncTask;
    private AsyncTask<Void, Void, ArrayList<String>> asyncArr;
    public ArrayList<user> dsUser;
    public CustomListAdapterSelectUserPost adapter;
    public String imageId_A;
    public String imageId_B;
    public String imageId_C;
    public ArrayList<String> imageIdArray;
    public byte[] imageArray;
    public String base64ImageString;
    public int time;
    private String email;
    private String carricerName;
    private Button button;
    private EditText editText_productName;
    public Spinner spinner_category;
    private EditText editText_myAddress;
    private EditText editText_AddressRec;
    private Switch deliveryDateTime_Switch;
    private TextView textView_DeliveryTime;
    private TextView textView_DeliveryDate;
    private EditText editText_length;
    private EditText editText_width;
    private EditText editText_height;
    private EditText editText_fee;
    private RadioButton radioButton_feeA;
    private RadioButton radioButton_feeB;
    private Switch switch_security_Deposits;
    private EditText editText_Security_Deposits;
    private EditText editText_Distance;
    public ImageView imageView_post_a;
    public ImageView imageView_post_b;
    public ImageView imageView_post_c;
    private EditText editText_receiverName;
    private EditText editText_receiverPhone;
    private EditText editText_receiverEmail;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    private int payer = 1;
    private int ctgId;
    private GetSetDataFormService getSetDataFormService;
    private DateTimePicker dateTimePicker ;
    private ProgressDialog mProgressDialog;
    private CountdownTimer countdownTimer;
    private ImageView imageView_ResultFeedBack;
    private List<product> listProducts;
    public ProgressBar progressBar_loading_image;
    public int RESULT_LOAD_IMAGE_A = 1;
    public int RESULT_LOAD_IMAGE_B = 2;
    public int RESULT_LOAD_IMAGE_C = 3;
    // Trạn thài đang chờ xử lý yêu cầu nhận vận chuyển hàng
    private boolean TIMEOUT = false;
    BroadcastReceiver handleBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            updateResultFeedBack(newMessage, textView);
            Toast.makeText(getApplication(), newMessage, Toast.LENGTH_LONG).show();
            Log.i(TAG, newMessage);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        getSetDataFormService =new GetSetDataFormService();
        imageIdArray = new ArrayList<String>();
        registerReceiver(handleBroadcastReceiver,
                new IntentFilter(DISPLAY_MESSAGE_ACTION));
        button = (Button) findViewById(R.id.button_post_product);
        editText_productName = (EditText) findViewById(R.id.editText_product_name);
        editText_myAddress = (EditText) findViewById(R.id.editText_my_address);
        editText_AddressRec = (EditText) findViewById(R.id.editText_address_rec);
        spinner_category =(Spinner) findViewById(R.id.spinner_category);
        deliveryDateTime_Switch = (Switch) findViewById(R.id.switch_delivery_dateTime);
        textView_DeliveryDate = (TextView) findViewById(R.id.textView_deliveryDate);
        textView_DeliveryTime = (TextView) findViewById(R.id.textView_deliveryTime);
        editText_length = (EditText) findViewById(R.id.editText_length);
        editText_width = (EditText) findViewById(R.id.editText_width);
        editText_height = (EditText) findViewById(R.id.editText_height);
        editText_fee = (EditText) findViewById(R.id.editText_fee);
        radioButton_feeA = (RadioButton) findViewById(R.id.radioButton_fee_a);
        radioButton_feeB = (RadioButton) findViewById(R.id.radioButton_fee_b);
        switch_security_Deposits = (Switch) findViewById(R.id.switch_security_deposits);
        editText_Security_Deposits = (EditText) findViewById(R.id.editText_security_deposits);
        editText_Distance = (EditText) findViewById(R.id.editText_distance);
        imageView_post_a = (ImageView) findViewById(R.id.imageView_post_a);
        imageView_post_b = (ImageView) findViewById(R.id.imageView_post_b);
        imageView_post_c = (ImageView) findViewById(R.id.imageView_post_c);
        progressBar_loading_image = (ProgressBar) findViewById(R.id.progressBar_loading_image);
        progressBar_loading_image.setVisibility(View.GONE);
        editText_receiverName = (EditText) findViewById(R.id.editText_receiver_full_name);
        editText_receiverPhone = (EditText) findViewById(R.id.editText_receiver_phone);
        editText_receiverEmail = (EditText) findViewById(R.id.editText_receiver_email);
        dateTimePicker = new DateTimePicker();
        Intent intent = getIntent();
        final String emaiLogin = intent.getStringExtra("emailLogin");
        getCategory();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        showDate(year, month + 1, day);
        // THOI GIAN GIAO HANG
        textView_DeliveryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
        textView_DeliveryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(998);
            }
        });
        // TIEN COC
        switch_security_Deposits.setChecked(true);
        deliveryDateTime_Switch.setChecked(true);
        radioButton_feeA.setChecked(true);
        deliveryDateTime_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textView_DeliveryDate.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                textView_DeliveryTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        switch_security_Deposits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editText_Security_Deposits.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        // CHI PHI
        radioButton_feeA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButton_feeB.setChecked(false);
                    payer = 1;
                }
            }
        });
        radioButton_feeB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButton_feeA.setChecked(false);
                    payer = 2;
                }
            }
        });
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ctgId = position+1;
               // Toast.makeText(getApplicationContext(), position +"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Image click
        imageView_post_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuSelectImage(imageView_post_a, RESULT_LOAD_IMAGE_A, imageId_A);
            }
        });
        imageView_post_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuSelectImage(imageView_post_b, RESULT_LOAD_IMAGE_B, imageId_B);
                /*byte[] bytes = Base64.decode(imageId, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap bm = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
                imageView_post_b.setImageBitmap(bm);*/
            }
        });
        imageView_post_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuSelectImage(imageView_post_c, RESULT_LOAD_IMAGE_C, imageId_C);
            }
        });
    }
    private void showPopupMenuSelectImage(final ImageView imageView , final int rs, final String imageId){
        if(imageId == null){
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, rs);
        }
        else {
            PopupMenu popupMenu = new PopupMenu(this, imageView);
            popupMenu.getMenuInflater().inflate(R.menu.menu_popup_image, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.item_add) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, rs);
                        //imageIdArray.remove(imageId);
                    } else if(item.getItemId() == R.id.item_delete) {
                        imageView.setImageResource(android.R.drawable.ic_menu_camera);
                        if(rs == RESULT_LOAD_IMAGE_A){
                            deleteImage(imageId_A);
                            imageId_A = null;
                        }else if(rs == RESULT_LOAD_IMAGE_B){
                            deleteImage(imageId_B);
                            imageId_B = null;
                        }else if(rs == RESULT_LOAD_IMAGE_C){
                            deleteImage(imageId_C);
                            imageId_C = null;
                        }
                        imageIdArray.remove(imageId);
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE_A && resultCode == RESULT_OK && data != null && data.getData() != null) {
            updateImage(data, imageView_post_a, RESULT_LOAD_IMAGE_A, imageId_A);
        }else if(requestCode == RESULT_LOAD_IMAGE_B && resultCode == RESULT_OK && data!=null && data.getData() != null){
            updateImage(data, imageView_post_b, RESULT_LOAD_IMAGE_B, imageId_B);
        }else if(requestCode == RESULT_LOAD_IMAGE_C && resultCode == RESULT_OK && data!=null && data.getData() != null){
            updateImage(data, imageView_post_c, RESULT_LOAD_IMAGE_C, imageId_C);
        }
    }
    private void updateImage(Intent data, ImageView imageView, int rs,String imageId){
        final Uri uri = data.getData();
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(uri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            Bitmap bitmap = Bitmap.createScaledBitmap(selectedImage, 256, 256, true);
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 30, stream);
            Random random = new Random();
            int n = random.nextInt(50)+1;
            String id = "img"+n;
            if(rs == RESULT_LOAD_IMAGE_A){
                imageId_A = id;
            }else if(rs == RESULT_LOAD_IMAGE_B){
                imageId_B = id;
            }else if(rs == RESULT_LOAD_IMAGE_C){
                imageId_C = id;
            }
            imageArray = stream.toByteArray();
            if(imageIdArray.contains(imageId)){
                imageIdArray.remove(imageId);
                deleteImage(imageId);
            }
            imageIdArray.add(id);
            dichImage(id);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void dichImage(String id){
        ImageConvertAsync imageConvertAsync = new ImageConvertAsync(this, id);
        imageConvertAsync.execute();
    }
    private void showRs(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
   /* public static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
        }
        return os.toByteArray();
    }*/
    private void deleteImage(String id){
        ImageDeleteAsync imageDeleteAsync = new ImageDeleteAsync(this, id);
        imageDeleteAsync.execute();
    }
    private void getCategory(){
        PTpostProductAsyncTask PTpostProductAsyncTask = new PTpostProductAsyncTask(this);
        PTpostProductAsyncTask.execute();
    }
    private boolean isNameValid(String name){
        return name.length()>10;
    }
    private boolean myAddressValid(String address){
        return !address.isEmpty();
    }
    private boolean addressRecValid(String address){
        return !address.isEmpty();
    }
    private boolean isReceiverNameValid(String name){
        return !name.isEmpty();
    }
    private boolean isReceiverPhoneValid(String phone){
        return phone.length()>6;
    }
    private boolean isFeeValid(String fee){
        return !fee.isEmpty();
    }

    public void sendMessagingGCM(String title, ArrayList<String> arrayList){
        try {
            Sender sender = new Sender(getString(R.string.API_KEY));
            Message message = new Message.Builder()
                    .collapseKey("message")
                    .timeToLive(30)
                    .delayWhileIdle(true)
                    .addData("message", title)
                    .build();
            MulticastResult multicastResult = sender.send(message, arrayList, 0);
         //   Toast.makeText(this, multicastResult.getTotal() + "", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_product, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        DialogMessage dialogMessage = new DialogMessage(this);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            if (isNameValid(editText_productName.getText().toString())) {
                if (myAddressValid(editText_myAddress.getText().toString())) {
                    if (addressRecValid(editText_AddressRec.getText().toString())) {
                        if (isFeeValid(editText_fee.getText().toString())) {
                            if (isReceiverNameValid(editText_receiverName.getText().toString())) {
                                if (isReceiverPhoneValid(editText_receiverPhone.getText().toString())) {
                                    this.ShowDialogSelectTypePost();
                                }else {
                                    dialogMessage.ShowDialog("", getString(R.string.post_error_receiverPhone));
                                }
                            }else {
                                dialogMessage.ShowDialog("", getString(R.string.post_error_receiverName));
                            }
                        }else {
                            dialogMessage.ShowDialog("", getString(R.string.post_error_fee));
                        }
                    }else {
                        dialogMessage.ShowDialog("", getString(R.string.post_error_addressRec));
                    }
                }else {
                    dialogMessage.ShowDialog("", getString(R.string.post_error_myAddress));
                }
            }else {
                dialogMessage.ShowDialog("", getString(R.string.post_error_name));
            }
        }
            //-- DĂNG SẢN PHẨM CẦn VẬn CHUYỂN

            //DateTimePicker dateTimePicker = new DateTimePicker(this);
            //Toast.makeText(this, dateTimePicker.formDateVNtoEN("23/3/1993")+"", Toast.LENGTH_LONG).show();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getListUser(){
        ImportUserToListSelectAsyncTask importUserToListSelectAsyncTask = new ImportUserToListSelectAsyncTask(this);
        importUserToListSelectAsyncTask.execute();
    }
    private void ShowDialogSelectTypePost(){
        final int n = 0;
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Chọn kiểu");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_select_type_post);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final ListView listView = (ListView) dialog.findViewById(R.id.listView_select_type_post);
        final ListView listView_show_user = (ListView) dialog.findViewById(R.id.listView_show_user);
        final Button button_next = (Button) dialog.findViewById(R.id.button_next_post);
        Button button_back = (Button) dialog.findViewById(R.id.button_back_post);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, ArrayClass.type_post));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean rs = false;
                if (position == 1) {
                    rs = true;
                }else if(position ==2){
                    // Lưu lại sản phẩm
                    insertProduct("isNull", "isNull", 3, null, null);
                }
                listView_show_user.setVisibility(rs ? View.VISIBLE : View.GONE);
                button_next.setVisibility(rs ? View.GONE : View.VISIBLE);
            }
        });
        dsUser = new ArrayList<user>();
        adapter = new CustomListAdapterSelectUserPost(this, dsUser);
        listView_show_user.setAdapter(adapter);
        listView_show_user.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListUser();
        listView_show_user.setVisibility(View.GONE);
        listView_show_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), dsUser.get(position).toString(), Toast.LENGTH_SHORT).show();
                    email = dsUser.get(position).getEmailLogin().toString();
                    carricerName  =dsUser.get(position).getFullName().toString();
                    ShowDialogSelectTime();
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncArr = new AsyncTask<Void, Void, ArrayList<String>>() {
                    @Override
                    protected ArrayList<String> doInBackground(Void... params) {
                        ArrayList<String> list = new ArrayList<String>();
                        list = getSetDataFormService.getRe();
                        return list;
                    }
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        Toast.makeText(getApplicationContext(), "Đăng gửi ...", Toast.LENGTH_SHORT);
                    }
                    @Override
                    protected void onPostExecute(ArrayList<String> arrayList) {
                        super.onPostExecute(arrayList);
                        Toast.makeText(getApplicationContext(), "Gửi thanhg công !", Toast.LENGTH_SHORT);
                        if(!arrayList.isEmpty()) {
                            // Đăng cho tất cả mọi người đều xem được
                            insertProduct("isNull", "isNull", 0, getString(R.string.gcm_new_post)+"@"+MainActivity.emaiLogin, arrayList);
                        }
                        asyncArr = null;
                    }
                };
                asyncArr.execute();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public TextView textView;
    private void ShowDialogSelectTime(){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Thời gian chờ");
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Toast;
        dialog.setContentView(R.layout.dialog_select_time);
        textView = (TextView) dialog.findViewById(R.id.textView_count_down);
        final EditText editText_set_minute = (EditText) dialog.findViewById(R.id.editText_set_minute);
        final EditText editText_set_second = (EditText) dialog.findViewById(R.id.editText_set_second);
        final LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.linearLayout_set_time);
        final ListView listView = (ListView) dialog.findViewById(R.id.listView_set_timer);
        imageView_ResultFeedBack = (ImageView) dialog.findViewById(R.id.imageView_result_feedback);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, ArrayClass.time_set));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        imageView_ResultFeedBack.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        final Button button = (Button) dialog.findViewById(R.id.button_start);
        final Button button_cancel = (Button) dialog.findViewById(R.id.button_ba);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        time = 5 * 60;
                        linearLayout.setVisibility(View.GONE);
                        return;
                    case 1:
                        time = 10 * 60;
                        linearLayout.setVisibility(View.GONE);
                        return;
                    case 2:
                        time = 20 * 60;
                        linearLayout.setVisibility(View.GONE);
                        return;
                    case 3:
                        time = 30 * 60;
                        linearLayout.setVisibility(View.GONE);
                        return;
                    case 4:
                        linearLayout.setVisibility(View.VISIBLE);
                    default:
                        return;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equals(getString(R.string.start))) {
                    if (editText_set_minute.getText().toString().isEmpty() && editText_set_second.getText().toString().isEmpty()) {
                        //Toast.makeText(getApplicationContext(),"", )
                    } else if (editText_set_minute.getText().toString().isEmpty()) {
                        time = Integer.parseInt(editText_set_second.getText().toString());
                    } else if (editText_set_second.getText().toString().isEmpty()) {
                        time = Integer.parseInt(editText_set_minute.getText().toString()) * 60;
                    } else if (Integer.parseInt(editText_set_minute.getText().toString()) > 60) {
                        Toast.makeText(getApplicationContext(), "Nho hon 60p", Toast.LENGTH_SHORT).show();
                    } else {
                        time = Integer.parseInt(editText_set_second.getText().toString()) + (Integer.parseInt(editText_set_minute.getText().toString()) * 60);
                    }
                    if (time > 0) {
                        finishActivityAndSendResult(time);
                    }
                }else {
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
        dialog.show();
    }
    private void finishActivityAndSendResult(int time){
        if (!email.isEmpty()) {
            ArrayList<String> arrayListRegID = getSetDataFormService.getListRegIdByEmail(email);
            if (!arrayListRegID.isEmpty()) {
                String message = getString(R.string.gcm_request_tranport) + "@Time@" + time + "@" + MainActivity.emaiLogin + "@" + editText_productName.getText();
                //Đăng cho 1 người có thể xem
                insertProduct("isNull", "isNull", 1, message, arrayListRegID);
            } else {
                Toast.makeText(getApplicationContext(), "User chưa cài ứng dụng", Toast.LENGTH_SHORT).show();
            }
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("time", time+"");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    public void confirmed(){
        textView.setText(getString(R.string.denied));
        //button.setText(getString(R.string.buttom_cancel));
    };
    private void startCountDownTimer(final TextView textView){
        countdownTimer = new CountdownTimer(this, time, textView);
        countdownTimer.setOnCountdownFinish(new OnCountdownFinish() {
            @Override
            public void onCountdownFinish() {
                confirmed();
            }
        });
        countdownTimer.start();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id ==999){
            return new DatePickerDialog(this, myDateSetListener, year, month, day);
        }
        else if (id == 998) {
            return new TimePickerDialog(this, myTimeSetListener, hour, minute, true);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showDate(year, monthOfYear +1, dayOfMonth);
        }
    };
    private TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            showTime(hourOfDay, minute);
        }
    };
    private void showDate(int year, int month, int day){
        textView_DeliveryDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
    private void showTime(int hour, int minute){
        textView_DeliveryTime.setText(new StringBuilder().append(hour).append(":").append(minute));
    }
    private void insertProduct(String emailCarricer, String carricer, int stateId, final String message, final ArrayList<String> arrayList) {
        String size = "0";
        float sds = 0;
        float distance = 0;
        String recEmail = "0";
        String image = "0";
        if (imageIdArray != null) {
            for (int i = 0; i < imageIdArray.size(); i++) {
                if (image.equals("0")) {
                    image = imageIdArray.get(i).toString();
                } else {
                    image = image + "," + imageIdArray.get(i).toString();
                }
            }
        }
        if (!editText_length.getText().toString().isEmpty() || !editText_width.getText().toString().isEmpty() || !editText_height.getText().toString().isEmpty()) {
            size = editText_length.getText().toString() + "-" + editText_width.getText().toString() + "-" + editText_height.getText().toString();
        }
        if (!editText_Security_Deposits.getText().toString().isEmpty()) {
            sds = Float.parseFloat(editText_Security_Deposits.getText().toString());
        }
        if (!editText_Distance.getText().toString().isEmpty()) {
            distance = Float.parseFloat(editText_Distance.getText().toString());
        }
        if (!editText_receiverEmail.getText().toString().isEmpty()) {
            recEmail = editText_receiverEmail.getText().toString();
        }
        if (imageIdArray != null) {
            for (int i = 0; i < imageIdArray.size(); i++) {
                if (image.isEmpty()) {
                    image = imageIdArray.get(i).toString();
                } else {
                    image = image + "," + imageIdArray.get(i).toString();
                }
            }
        }
        final product p = new product();
        p.setEmailLogin(MainActivity.emaiLogin);
        p.setEmailCarrier(emailCarricer);
        p.setName(editText_productName.getText().toString());
        p.setMyAddress(editText_myAddress.getText().toString());
        p.setAddressRec(editText_AddressRec.getText().toString());
        p.setDeliveryDate(dateTimePicker.formatStringtoDate(textView_DeliveryDate.getText().toString()));
        p.setDeliveryTime(textView_DeliveryTime.getText().toString());
        p.setCarrier(carricer);
        p.setSize(size);
        p.setFee(Float.parseFloat(editText_fee.getText().toString()));
        p.setPayer(payer);
        p.setSecurityDeposits(sds);
        p.setDistance(distance);
        p.setImage(image);
        p.setReceiverName(editText_receiverName.getText().toString());
        p.setReceiverPhone(editText_receiverPhone.getText().toString());
        p.setReceiverEmail(recEmail);
        p.setCatgId(ctgId);
        p.setStateId(stateId);
        asyncTask = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                int rs = getSetDataFormService.insertProduct(p);
                return rs;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(getApplicationContext(), "Đang đăng tin...", Toast.LENGTH_SHORT).show();
            }
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if (integer > 0) {
                    sendMessagingGCM(message+"@"+integer, arrayList);
                } else {
                    // Toast.makeText(getApplicationContext(), "Lỗi, không đăng sản phẩm được, vui lòng thử lại!" , Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), integer + "", Toast.LENGTH_LONG).show();
                    // }
                }
                asyncTask=null;
            }
        };
        asyncTask.execute();

    }
    private void ShowResult(){
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Save")
                .setMessage(getString(R.string.post_complete))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.continute), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetControl();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.buttom_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closePostActivity();
                        dialog.dismiss();
                    }
                });
        Dialog dialog = aBuilder.create();
        dialog.show();
    }
    private void closePostActivity(){
        this.finish();
    }
    private void resetControl(){
        editText_productName.setText("");
    }
    private void updateResultFeedBack(String message,TextView textView){
        if (message.contains("FEEDBACK")){
            String[] rs = message.split("@");
            countdownTimer.stop();
            imageView_ResultFeedBack.setVisibility(View.VISIBLE);
            if(rs[0].toString().equals("FEEDBACK_YES")){
                textView.setText(getString(R.string.accepted));
                setTextAppearance(textView, android.R.style.TextAppearance_Medium);
                imageView_ResultFeedBack.setImageResource(android.R.drawable.presence_online);
               // insertProduct(rs[1], carricerName);
            }else {
                textView.setText(getString(R.string.denied));
                setTextAppearance(textView, android.R.style.TextAppearance_Medium);
                imageView_ResultFeedBack.setImageResource(android.R.drawable.presence_busy);
            }
        }
    }
    public void setTextAppearance(TextView textView, int resId) {
        if (Build.VERSION.SDK_INT < 23) {
            textView.setTextAppearance(this, resId);
            textView.setTextColor(Color.BLUE);
        } else {
            textView.setTextAppearance(resId);
            textView.setTextColor(Color.BLUE);
        }
    }
}
