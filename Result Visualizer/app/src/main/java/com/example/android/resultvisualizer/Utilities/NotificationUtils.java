package com.example.android.resultvisualizer.Utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.resultvisualizer.NotificationActivity;
import com.example.android.resultvisualizer.R;
import com.example.android.resultvisualizer.ResultActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {

    private NotificationUtils() {
    }

    private static String rn;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void notification(Context context, String s) {
        rn=s;
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    "notification-channel",
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification-channel").
                setColor(ContextCompat.getColor(context, R.color.colorPrimary)).setSmallIcon(R.drawable.ic_launcher_result_visualizer)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_result_visualizer_round)).setContentTitle("Result Available")
                .setContentText("The result for " + s + " is now available").setStyle(new NotificationCompat.BigTextStyle().bigText("The result for " + s + " is now available")).setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context)).addAction(dismiss(context)).setAutoCancel(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
        manager.notify(1, builder.build());
    }

    private static NotificationCompat.Action dismiss(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction("dismiss");
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(0, "Dismiss", pendingIntent);
    }

    private static PendingIntent contentIntent(Context context) {
        return PendingIntent.getActivity(context, 3, new Intent(context, NotificationActivity.class).putExtra(Intent.EXTRA_TEXT,rn),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
