package com.onesimply.sonnv.androidtransportgcm;

/**
 * Created by N on 17/02/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Random;

public class ServerTask {
    public static  final String NAMESPACE="http://tempuri.org/";
    public static final String SERVER_URL="http://onesimply.somee.com/WebService1.asmx?WSDL";
    public static final String SENDER_ID = "376815504183";
    public static final String TAG = "OnesimolyGCM_LOG";
    public static final String DISPLAY_MESSAGE_ACTION = "com.onesimply.sonnv.androidtransportgcm.DISPLAY_MESSAGE";
    public static final String EXTRA_MESSAGE = "message";

    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    public  static final int MAX_ATTEMPTS = 5;
    public  static final int BACKOFF_MILLI_SECONDS = 2000;
    public  static final Random random = new Random();
    public static boolean post(final Context context, final String regId)
    {
        try{
            final String METHOD_NAME="insertRegID";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;
            SoapObject request=new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("regId", regId);
            request.addProperty("emailLogin",MainActivity.emaiLogin);
            SoapSerializationEnvelope envelope=
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshal=new MarshalFloat();
            marshal.register(envelope);
            HttpTransportSE androidHttpTransport=
                    new HttpTransportSE(SERVER_URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response=(SoapPrimitive) envelope.getResponse();
            String s=response.toString();
            Toast.makeText(context, "kết quả =" + s, Toast.LENGTH_LONG).show();
            Log.i(TAG, "Ghi Registration lên Server:\n" + s);
            return true;
        }
        catch(Exception e)
        {
            Toast.makeText(context, "kết quả =\n"+e.toString(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "Lỗi post:\n"+e.toString());
        }
        return false;
    }
    public static boolean post_unregister(final Context context, final String regId)
    {
        try{
            final String METHOD_NAME="deleteRegid";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;
            SoapObject request=new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("regId", regId);
            SoapSerializationEnvelope envelope=
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshal=new MarshalFloat();
            marshal.register(envelope);
            HttpTransportSE androidHttpTransport=
                    new HttpTransportSE(SERVER_URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response=(SoapPrimitive) envelope.getResponse();
            String s=response.toString();
//Toast.makeText(context, "kết quả ="+s, Toast.LENGTH_LONG).show();
            Log.i(TAG, "Hủy Registration lên Server:\n"+s);
            return true;
        }
        catch(Exception e)
        {
//Toast.makeText(context, "kết quả =\n"+e.toString(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "Lỗi post2_unregister:\n"+e.toString());
        }
        return false;
    }
    public static boolean register(final Context context, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
// Once GCM returns a registration id, we need to register it in the
// demo server. As the server might be down, we will retry it a couple
// times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                boolean b=post(context,regId);
                GCMRegistrar.setRegisteredOnServer(context, true);

                String message = context.getString(R.string.server_registered);

                displayMessage(context, message);
                if(b)
                    return true;
            } catch (Exception e) {
// Here we are simplifying and retrying on any error; in a real
// application, it should retry only on unrecoverable errors
// (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i, e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + "ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
// Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
// increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        displayMessage(context, message);
        return false;
    }

}
