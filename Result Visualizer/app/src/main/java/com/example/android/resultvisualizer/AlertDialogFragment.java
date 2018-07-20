package com.example.android.resultvisualizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        startActivity(new Intent(getContext(), HelpActivity.class));
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setTitle("Roll No. Is Not Valid !").setMessage("You can try entering the Roll No. again. However , " +
                "if you face " + "the same issue please review our records policy.").setPositiveButton("Try Again",
                dialogClickListener).setNegativeButton("Help", dialogClickListener).create();
    }
}