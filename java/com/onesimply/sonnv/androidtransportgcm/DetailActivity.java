package com.onesimply.sonnv.androidtransportgcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.tool.PagerAdapterDetail;

public class DetailActivity extends AppCompatActivity {
    public static   int id=0;
    public static String email ="";
    public static boolean note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LoadPreferences();

        Intent intent = getIntent();

        String idP = intent.getStringExtra("id");
        Toast.makeText(this, idP, Toast.LENGTH_SHORT).show();
        if(!idP.isEmpty()){
            id = Integer.parseInt(idP);
            email = intent.getStringExtra("email");
            note = intent.getBooleanExtra("note", false);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_detail);
        tabLayout.addTab(tabLayout.newTab().setText("Thông tin SP"));
        tabLayout.addTab(tabLayout.newTab().setText("Thông tin đối tác"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.BLACK, Color.argb(255, 251, 113, 38));
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_detail);
        final PagerAdapterDetail adapter = new PagerAdapterDetail
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public void LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        boolean islogined  = sharedPreferences.getBoolean("isLogin", false);
        if(!islogined){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id ==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
