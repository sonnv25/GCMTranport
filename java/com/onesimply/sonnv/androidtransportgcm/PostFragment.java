package com.onesimply.sonnv.androidtransportgcm;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onesimply.sonnv.androidtransportgcm.entities.ArrayClass;
import com.onesimply.sonnv.androidtransportgcm.tool.ConmadLineLocation;
import com.onesimply.sonnv.androidtransportgcm.tool.CountdownTimer;
import com.onesimply.sonnv.androidtransportgcm.tool.DialogMessage;
import com.onesimply.sonnv.androidtransportgcm.tool.OnCountdownFinish;
import java.util.Calendar;

import static com.onesimply.sonnv.androidtransportgcm.ServerTask.DISPLAY_MESSAGE_ACTION;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.EXTRA_MESSAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    GoogleMap mMap;
    private ProgressDialog mProgressDialog;

    public ImageView imageView_gif;
    public int time = 0;
    public Calendar calendar;
    public PostFragment() {
        // Required empty public constructor
    }
    private Button button;
    ConmadLineLocation cmd;

    CountdownTimer countdownTimer;
    public TextView textView_count_down;

    BroadcastReceiver handleBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            if(newMessage.contains(getString(R.string.feedback))){
                updateResultFeedBack(newMessage);
            }
        }
    };
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        button = (Button) view.findViewById(R.id.button_to_post_product_activity);
        textView_count_down = (TextView) view.findViewById(R.id.textView_count_down_time);
        imageView_gif = (ImageView) view.findViewById(R.id.imageView_gif);

        Intent intent = getActivity().getIntent();
        final String user = intent.getStringExtra("user");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostProductActivity.class);
                intent.putExtra("emailLogin", user);
                startActivityForResult(intent, 10);
            }
        });
        textView_count_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
        if(isAdded()) {
            getActivity().registerReceiver(handleBroadcastReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
        }
        return view;
    }
    public void showPopupMenu(){
        PopupMenu popupMenu = new PopupMenu(getActivity(), textView_count_down);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_time_count_down, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_time) {
                }
                return true;
            }
        });
        popupMenu.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==10){
            if (resultCode == Activity.RESULT_OK){
                time = Integer.parseInt(data.getStringExtra("time"));
                if(time>0){
                    ArrayClass.time = time;
                    calendar = Calendar.getInstance();
                    ArrayClass.hour = calendar.get(Calendar.HOUR_OF_DAY);
                    ArrayClass.minute = calendar.get(Calendar.MINUTE);
                    ArrayClass.second = calendar.get(Calendar.SECOND);
                    startCountDownTimer();
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        cmd = new ConmadLineLocation(getActivity());
        if(cmd.checkLocationSevicesEnabled()){
            setUpMapIfNeeded();
        }else {
            Toast.makeText(getActivity(), "@@@@@@@@@@@@@@@@@@@@@@@@@@", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity(), "Tải lại", Toast.LENGTH_LONG).show();
        calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);
        if(ArrayClass.time>0){
            time = ArrayClass.time - ((h - ArrayClass.hour) * 60 * 60 + (m - ArrayClass.minute) * 60 + (s - ArrayClass.second));
            startCountDownTimer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    public void startCountDownTimer(){
        countdownTimer = new CountdownTimer(getActivity(), time, textView_count_down);
        countdownTimer.stop();
        countdownTimer.setOnCountdownFinish(new OnCountdownFinish() {
            @Override
            public void onCountdownFinish() {
                time = 0;
                textView_count_down.setText(getString(R.string.time_end));
            }
        });
        countdownTimer.start();
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            android.app.Fragment fragment = new android.app.Fragment();
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_api))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                DialogMessage dialogMessage = new DialogMessage(getActivity());
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setTitle("Loading map ...");
                mProgressDialog.setMessage("waiting...");
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        mProgressDialog.dismiss();
                    }
                });
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.setMyLocationEnabled(true);
                    cmd.getMyLocation();
                    if (ArrayClass.lng != 0 && ArrayClass.lat != 0){
                        zomMapLocation(ArrayClass.lat, ArrayClass.lng);
                    }else {
                        dialogMessage.ShowDialog("Location", "lat = long = 0");
                    }
                   // LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, mLocationListener);
            }
        }
    }
    private void  zomMapLocation(double lat, double lng) {
            LatLng latLng = new LatLng(lat, lng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(15)
                    .bearing(90)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            MarkerOptions option=new MarkerOptions();
            option.title("Vị trí của tôi");
           // option.snippet(""+ showAddress(location.getLatitude(), location.getLongitude()));
            option.position(latLng);
            Marker currentMarker= mMap.addMarker(option);
            currentMarker.showInfoWindow();
    }
    private void updateResultFeedBack(String message){
        if (message.contains(getString(R.string.feedback))){
            String[] rs = message.split("@");
            countdownTimer.stop();
            //imageView_ResultFeedBack.setVisibility(View.VISIBLE);
            if(rs[0].toString().equals("FEEDBACK_YES")){
                textView_count_down.setText(getString(R.string.accepted));
                setTextAppearance(textView_count_down, android.R.style.TextAppearance_Medium);
               // imageView_ResultFeedBack.setImageResource(android.R.drawable.presence_online);
                // insertProduct(rs[1], carricerName);
            }else {
                textView_count_down.setText(getString(R.string.denied));
                setTextAppearance(textView_count_down, android.R.style.TextAppearance_Medium);
               // imageView_ResultFeedBack.setImageResource(android.R.drawable.presence_busy);
            }
        }
    }
    public void setTextAppearance(TextView textView, int resId) {
        if (Build.VERSION.SDK_INT < 23) {
            textView.setTextAppearance(getActivity(), resId);
            textView.setTextColor(Color.BLUE);
        } else {
            textView.setTextAppearance(resId);
            textView.setTextColor(Color.BLUE);
        }
    }
}
