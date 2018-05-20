package com.ferayalin.sinavdorduncusoru;

/**
 * Created by Feray on 19.05.2018.
 */

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;


public class NotificationPublisher extends BroadcastReceiver {
    final int MIN = 60 * 1000;
    String adi,tipi;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Uygulamayı ayağa kaldır
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");

        // Wake lock al
        wl.acquire();

        adi = intent.getExtras().getString("adi");
        tipi = intent.getExtras().getString("tipi");

        //Wakelock'u bırak.
        wl.release();

        if (tipi.equals("Toast")) {

            Toast.makeText(context, adi, Toast.LENGTH_LONG).show();
        }
        else if (tipi.equals("Titreşim")) {

           Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
        }
        else if (tipi.equals("Notification")) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("ALARM")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(adi))
                    .setContentText(adi)
                    .setAutoCancel(true);

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                notificationBuilder.setContentIntent(pendingIntent);
            }


            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }


}

    public void alarmKur(Context context, Alarm alarm) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, alarm.getSaati());
        c.set(Calendar.MINUTE, alarm.getDakikasi());
        c.set(Calendar.SECOND, 0);

        int tekrarSuresi = 0;
        if (alarm.getTekrarZamani() == "Günlük") {
            tekrarSuresi = 1000 * 60 * 60 * 24;
        } else if (alarm.getTekrarZamani() == "Haftalık") {
            tekrarSuresi = 1000 * 60 * 60 * 24 * 30;
        } else if (alarm.getTekrarZamani() == "Aylık") {
            tekrarSuresi = 1000 * 60 * 60 * 24 * 30 * 365;
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationPublisher.class);
        intent.putExtra("tipi", alarm.getUyarıTipi());
        intent.putExtra("adi", alarm.getAdi());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,
                0, intent,
                0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), tekrarSuresi, alarmIntent);

    }
}
