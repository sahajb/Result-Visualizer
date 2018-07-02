package com.example.android.resultvisualizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTaskRoot()) {
            Intent startIntent = new Intent(this, MainActivity.class);
            if (getIntent().hasExtra(Intent.EXTRA_TEXT))
                startIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra(Intent.EXTRA_TEXT));
            startActivity(startIntent);
        } else {
            Intent reorderIntent = new Intent(this, ResultActivity.class);
            reorderIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            if (getIntent().hasExtra(Intent.EXTRA_TEXT))
                reorderIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra(Intent.EXTRA_TEXT));
            startActivity(reorderIntent);
        }
        finish();
    }
}
