package com.example.android.resultvisualizer.Utilities;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.Objects;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final String action = Objects.requireNonNull(intent).getAction();
        if (Objects.requireNonNull(action).equals("dismiss"))
            NotificationUtils.clearAllNotifications(this);
    }
}
