package com.example.android.resultvisualizer.Utilities;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final String action = intent.getAction();
        if(action.equals("dismiss"))
            NotificationUtils.clearAllNotifications(this);
        else if(action.equals("view"))
            NotificationUtils.clearAllNotifications(this);
    }
}
