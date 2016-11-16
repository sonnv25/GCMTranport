package com.onesimply.sonnv.androidtransportgcm.tool;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.onesimply.sonnv.androidtransportgcm.entities.ArrayClass;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by N on 04/03/2016.
 */
public class ConmadLineLocation {

    Activity context;
    private DialogMessage dialogMessage;
    public ConmadLineLocation(Activity context){
        this.context = context;
        dialogMessage = new DialogMessage(context);
    }
    public boolean checkLocationSevicesEnabled() {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean GPS_ENABLED = false;
            boolean NETWORT_ENABLED = false;
            try {
                GPS_ENABLED = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }
            try {
                NETWORT_ENABLED = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }
            if (!GPS_ENABLED && !NETWORT_ENABLED) {
                return false;
            }
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    public void getMyLocation() {
        try {
            double lat=0;
            double lng=0;
            String textView_GpsLocation = "";
            String textView_MyLocation ="";
            String textView_NetworkLocation ="";
            final String gpsLocationProvider = LocationManager.GPS_PROVIDER;
            final String networkLocationProvider = LocationManager.NETWORK_PROVIDER;

            LocationManager locationManager =
                    (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

            Location lastKnownLocation_byGps =
                    locationManager.getLastKnownLocation(gpsLocationProvider);
            Location lastKnownLocation_byNetwork =
                    locationManager.getLastKnownLocation(networkLocationProvider);

            if(lastKnownLocation_byGps==null){
                textView_GpsLocation=("GPS Last Location not available");

                if(lastKnownLocation_byNetwork==null){
                    textView_NetworkLocation =("Network Last Location not available");
                    textView_MyLocation=("No Last Known Location!");
                }else{
                    textView_NetworkLocation=("Network Location:\n" + lastKnownLocation_byNetwork.toString());

                    textView_MyLocation=(
                            "My Location is " + lastKnownLocation_byNetwork.getLatitude() +
                                    " : " + lastKnownLocation_byNetwork.getLongitude());
                    lat = lastKnownLocation_byNetwork.getLatitude();
                    lng = lastKnownLocation_byNetwork.getLongitude();
                }


            }else{
                textView_GpsLocation=("GPS Location:\n" + lastKnownLocation_byGps.toString());

                if(lastKnownLocation_byNetwork==null){
                    textView_NetworkLocation=("Network Last Location not available");
                    textView_MyLocation=(
                            "My Location is " + lastKnownLocation_byGps.getLatitude() +
                                    " : " + lastKnownLocation_byGps.getLongitude());
                    lat = lastKnownLocation_byGps.getLatitude();
                    lng = lastKnownLocation_byGps.getLongitude();
                }else{
                    textView_NetworkLocation=("Network Location:\n" + lastKnownLocation_byNetwork.toString());

                    //Both Location provider have last location
                    //decide location base on accuracy
                    if(lastKnownLocation_byGps.getAccuracy() <= lastKnownLocation_byNetwork.getAccuracy()){
                        textView_MyLocation=(
                                "My Location from GPS\n" + lastKnownLocation_byGps.getLatitude() +
                                        " : " + lastKnownLocation_byGps.getLongitude());
                        lat = lastKnownLocation_byGps.getLatitude();
                        lng = lastKnownLocation_byGps.getLongitude();
                    } else {
                        textView_MyLocation=(
                                "My Location from Network\n" + lastKnownLocation_byNetwork.getLatitude() +
                                        " : " + lastKnownLocation_byNetwork.getLongitude());
                        lat = lastKnownLocation_byNetwork.getLatitude();
                        lng = lastKnownLocation_byNetwork.getLongitude();
                    }

                }
            }
            ArrayClass.lat = lat;
            ArrayClass.lng = lng;
            //dialogMessage.ShowDialog("Location", textView_GpsLocation + " \n "+ textView_NetworkLocation+" \n"+ textView_MyLocation);
        }catch (Exception ex){
            dialogMessage.ShowDialog("Location", "Loi" +" --getMyLocation --" + ex);
        }
    }
    public String showAddress(double lat, double lng){
        String addressString = "";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                addressString = address.getAddressLine(0)+", "+ address.getAddressLine(1)+", "
                        + address.getSubAdminArea()+", " + address.getAdminArea() ;
            }
           // for(int i =1; i<addresses.size(); i++){
               // Address address = addresses.get(i);
               // String ad = address.getAddressLine(0)+"- "+ address.getAddressLine(1)+" - "
               //         + address.getSubAdminArea()+"-" + address.getAdminArea() ;
               // ArrayClass.loaction_address.add(ad);
            //}
            Log.e("Address from lat,long ;", addressString);
        } catch (IOException e) {
            dialogMessage.ShowDialog("Location", e.toString());
        }
      //  dialogMessage.ShowDialog("Count location address", ArrayClass.loaction_address.size() +"");
        return  addressString;
    }
}
