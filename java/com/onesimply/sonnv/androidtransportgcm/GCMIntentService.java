package com.onesimply.sonnv.androidtransportgcm;

/**
 * Created by N on 17/02/2016.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import static com.onesimply.sonnv.androidtransportgcm.ServerTask.SENDER_ID;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.displayMessage;
public class GCMIntentService extends GCMBaseIntentService {
    public String productId;
    public String emailSend;
    private static final String TAG = "GCMIntentService";
    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
//String message = getString(R.string.gcm_message);
        String message = intent.getStringExtra("message");
        displayMessage(context, message);
// notifies user
        if (message.contains("Time")) {
            String[] rs = message.split("@");
            generateNotification(context, rs[0].toString(), rs[5].toString(), rs[3].toString());
        } else if(message.contains(getString(R.string.gcm_new_post))){
            String[] rs = message.split("@");
            generateNotification(context, rs[0].toString(), rs[2].toString(), rs[1].toString());
        }
    }
    @Override
    protected void onError(Context context, String s) {
        Log.i(TAG, "Received error: " + s);
        displayMessage(context, getString(R.string.gcm_error, s));
    }

    @Override
    protected void onRegistered(Context context, String s) {
        Log.i(TAG, "Device registered: regId = " + s);
        displayMessage(context, getString(R.string.gcm_registered));
        ServerTask.register(context, s);
    }

    @Override
    protected void onUnregistered(Context context, String s) {
        Log.i(TAG, "hủy đăng ký unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        if (GCMRegistrar.isRegisteredOnServer(context)) {
//Call bỏ đăng ký ở đây
            GCMRegistrar.setRegisteredOnServer(context, false);
//ServerTask.post_unregister(context, registrationId);
        } else {
            Log.i(TAG, "Ignoring unregister callback");
        }
    }
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
// notifies user
        generateNotification(context, message, null, null);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }
    private static void generateNotification(Context context, String message, String id, String email) {

        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse("http://onesimply.vn"));

        Intent intent = new Intent(context, DetailActivity.class);
        if(!email.equals("")){
            intent.putExtra("id", id);
            intent.putExtra("email", email);
            intent.putExtra("note", true);
            intent.setAction("m_data");
        }
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
        //Random random = new Random();
        intent.setData(Uri.parse("m_data"));
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name) + ": Thông báo mới.")
                .setContentText(message)
                .setSmallIcon(R.drawable.notificationsicon)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
