package com.giant_monkey.dev.pushservicetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class MyService extends Service {
    NotificationManager notificationManager;
    ServiceThread thread;
    Notification notification;



    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        MyServiceHandler handler = new MyServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        thread.StopForever();
        thread = null;
    }

    class MyServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Content Title")
                    .setContentText("Content Text")
                    .setSmallIcon(R.drawable.logo)
                    .setTicker("알림!!!")
                    .setContentIntent(pendingIntent)
                    .build();
            notification.defaults = Notification.DEFAULT_SOUND;
            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(777, notification);
            Toast.makeText(MyService.this, "뜸?", Toast.LENGTH_SHORT).show();


        }
    }
}
