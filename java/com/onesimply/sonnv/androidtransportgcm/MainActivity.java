package com.onesimply.sonnv.androidtransportgcm;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.onesimply.sonnv.androidtransportgcm.asyncs.UpdateCarricerAsync;
import com.onesimply.sonnv.androidtransportgcm.entities.SlideMenuItem;
import com.onesimply.sonnv.androidtransportgcm.tool.CountdownTimer;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;
import com.onesimply.sonnv.androidtransportgcm.tool.OnCountdownFinish;
import com.onesimply.sonnv.androidtransportgcm.tool.SlideMenuListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import static com.onesimply.sonnv.androidtransportgcm.ServerTask.DISPLAY_MESSAGE_ACTION;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.EXTRA_MESSAGE;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.SENDER_ID;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.TAG;

/*stateId
        0 : Đăng cho tất cả mọi người
        1 : Đăng cho 1 người _ đăng chời xử lý
        2 : Đã có người nhận
        3 : Lưu
        4 : Đơn hàng đã hoàn thành*/

public class MainActivity extends AppCompatActivity {
    public static boolean active = false;
    public static Context context;
    AsyncTask<Void, Void, Boolean> gcmRegisterTask;
    ImageView imageView;
    public static String emaiLogin = "";
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence mDrawerTitle;
    // used tostore app title
    private CharSequence mTitle;
    private String[] navMenuItem;
    private TypedArray navMenuIcon;
    private ArrayList<SlideMenuItem> slideMenuItems;
    private SlideMenuListAdapter slideMenuListAdapter;
    private String FEEDBACK;
    private String email_feed;
    private ArrayList<String> arrayListRegId;
    final BroadcastReceiver handleMessageReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
                    /*if(newMessage.contains(getString(R.string.gcm_request_tranport))){
                        if(active) {
                            ShowDialogNote(newMessage);
                        }
                    }*/
                    Toast.makeText(getApplicationContext(), newMessage, Toast.LENGTH_LONG).show();
                    Log.i(TAG, newMessage);
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        active = true;
        context  =this;
        mTitle = mDrawerTitle = this.getTitle();
        navMenuItem = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcon = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        listView = (ListView) this.findViewById(R.id.list_slidermenu);
        slideMenuItems  =new ArrayList<SlideMenuItem>();

        slideMenuItems.add(new SlideMenuItem(navMenuItem[0], navMenuIcon.getResourceId(0, -1)));
        slideMenuItems.add(new SlideMenuItem(navMenuItem[1],"100", navMenuIcon.getResourceId(1, -1), true));
        slideMenuItems.add(new SlideMenuItem(navMenuItem[2], navMenuIcon.getResourceId(2, -1)));
        slideMenuItems.add(new SlideMenuItem(navMenuItem[3],"12", navMenuIcon.getResourceId(3, -1),true));
        slideMenuItems.add(new SlideMenuItem(navMenuItem[4], navMenuIcon.getResourceId(4, -1)));
        slideMenuItems.add(new SlideMenuItem(navMenuItem[5], navMenuIcon.getResourceId(5, -1)));
        navMenuIcon.recycle();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });
        slideMenuListAdapter = new SlideMenuListAdapter(getApplicationContext(), slideMenuItems);
        listView.setAdapter(slideMenuListAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        if(savedInstanceState == null){
            displayView(1);
        }
        Intent intent = getIntent();
        emaiLogin = intent.getStringExtra("user");
        xuLyDangKyGCMServer(emaiLogin);

    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinsp\n SimplifiableIfStatement
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void xuLyDangKyGCMServer(final String emailLogin) {
// Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
// Make sure the manifest was properly set - comment out this line
// while developing the app, then uncomment it when it's ready.
//        GCMRegistrar.checkManifest(this);
//msg = (TextView) findViewById(R.id.display);
        registerReceiver(handleMessageReceiver,
                new IntentFilter(DISPLAY_MESSAGE_ACTION));
        final String regId = GCMRegistrar.getRegistrationId(this);
        Toast.makeText(this, "REGID của bạn = \n" + regId, Toast.LENGTH_LONG).show();
        if (regId.equals("")) {
// Automatically registers application on startup.
            GCMRegistrar.register(this, SENDER_ID);
        } else {
// Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(this)) {
// Skips registration.
                Toast.makeText(this, "REGID của bạn đã được đăng ký", Toast.LENGTH_LONG).show();
                Log.i(TAG, "đã đăng ký :\n" + regId + "\n Thành công");
                final Context context = this;
                gcmRegisterTask = new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        GetSetDataFormService getSetDataFormService = new GetSetDataFormService();
                        boolean rs = getSetDataFormService.UpdateEmailInGCM(regId, emailLogin);
                        return rs;
                    }
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        gcmRegisterTask = null;
                        Toast.makeText(getApplicationContext(), "Updated email GCM!", Toast.LENGTH_LONG).show();
                    }
                };
                gcmRegisterTask.execute(null, null, null);
            }else {
                // Try to register again, but not in the UI thread.
// It's also necessary to cancel the thread onDestroy(),
// hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                gcmRegisterTask = new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        gcmRegisterTask = null;
                    }
                };
                gcmRegisterTask.execute(null, null, null);
            }
        }
    }
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new PosterFragment();
                break;
            case 2:
                fragment = new ShipperFragment();
                break;
            case 3:
                fragment = new ReceiverFragment();
                break;
            case 5:
                logout();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            listView.setItemChecked(position, true);
            listView.setSelection(position);
            setTitle(navMenuItem[position]);
            mDrawerLayout.closeDrawer(listView);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(listView);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    TextView textView;
    public void ShowDialogNote(String time ){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.setCancelable(false);
        dialog.setTitle(getString(R.string.title));
        textView = (TextView) dialog.findViewById(R.id.textView_count_down_cf);
        TextView textView_product_name = (TextView) dialog.findViewById(R.id.textView_name_product_dialog_cf);
        final TextView textView_email_feed = (TextView) dialog.findViewById(R.id.textView_email_feed);
        textView_email_feed.setVisibility(View.INVISIBLE);

        Button button_ok = (Button) dialog.findViewById(R.id.button_yes);
        button_ok.setText(context.getString(R.string.yes));
        // TIMER COUNT DOWN
        final String[] second = time.split("@");
        int s = Integer.parseInt(second[2].toString());
        textView_email_feed.setText(second[3].toString());
        textView_product_name.setText(second[4]);
        startCountDownTimer(s, textView);
        // BUTTON EVENT
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FEEDBACK = "FEEDBACK_YES@"+MainActivity.emaiLogin;
                sendFeedBack(textView_email_feed.getText().toString(), FEEDBACK);
                updateCarricer(Integer.parseInt( second[5].toString()), textView_email_feed.getText().toString(), 0);
                dialog.dismiss();
            }
        });
        Button button_no = (Button) dialog.findViewById(R.id.button_no);
        button_no.setText(context.getString(R.string.no));
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FEEDBACK  ="FEEDBACK_NO@"+MainActivity.emaiLogin;
                sendFeedBack(textView_email_feed.getText().toString(),FEEDBACK);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void updateCarricer(int productID, String email, int sateId){
        UpdateCarricerAsync updateCarricerAsync = new UpdateCarricerAsync(this,productID, email, sateId );
        updateCarricerAsync.execute();
    }
    public void startCountDownTimer(int s, final TextView textView){
        if(s>0){
            CountdownTimer countdownTimer = new CountdownTimer(this, s, textView);
            countdownTimer.stop();
            countdownTimer.setOnCountdownFinish(new OnCountdownFinish() {
                @Override
                public void onCountdownFinish() {

                }
            });
            countdownTimer.start();
        }
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
    private void sendFeedBack(final String email, final String title){
        final GetSetDataFormService getSetDataFormService = new GetSetDataFormService();
        AsyncTask<Void, Void, ArrayList<String>> asyncTask = new AsyncTask<Void, Void, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Void... params) {
                return getSetDataFormService.getListRegIdByEmail(email);
            }
            @Override
            protected void onPreExecute() {

            }
            @Override
            protected void onPostExecute(ArrayList<String> arrayList) {
                super.onPostExecute(arrayList);
                if(!arrayList.isEmpty()) {
                    sendMessagingGCM(title, arrayList);
                    Toast.makeText(context, getString(R.string.complete), Toast.LENGTH_SHORT).show();
                }
            }
        };
        asyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(handleMessageReceiver!=null)
                unregisterReceiver(handleMessageReceiver);
        }catch(Exception e)
        {

        }

    }
}
