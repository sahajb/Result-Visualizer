package com.example.android.resultvisualizer;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.resultvisualizer.Utilities.AnimationUtils.onClickButton;
import static com.example.android.resultvisualizer.Utilities.SubjectUtils.getId;
import static com.example.android.resultvisualizer.Utilities.SubjectUtils.getStatus;
import static com.example.android.resultvisualizer.Utilities.SubjectUtils.getSubCode;
import static com.example.android.resultvisualizer.Utilities.SubjectUtils.getSubDetails;


public class SummaryAdapter extends ArrayAdapter<Summary> {

    private SparseBooleanArray expandState = new SparseBooleanArray();

    private String rn;

    public SummaryAdapter(@NonNull Context context, @NonNull ArrayList<Summary> arrayList, String s) {
        super(context, 0, arrayList);
        rn = s;
        for (int i = 0; i < arrayList.size(); i++)
            expandState.append(i, true);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.summary, parent, false);
        }
        Summary summary = getItem(position);
        JSONObject object = summary.getObject();
        int j = summary.getPosition();
        String b = "";
        ((TextView) view.findViewById(R.id.sem)).setText(("Sem - " + String.valueOf(j)));
        try {
            ((TextView) view.findViewById(R.id.gpa)).setText(object.getString("SGPA(" + String.valueOf(j - 1) + ")"));
            b = object.getString("Roll No(2)").substring(5, 7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final View buttonLayout = (View) view.findViewById(R.id.button);
        final CardView cv = (CardView) view.findViewById(R.id.cv);
        final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.expandableLayout);
        final boolean isExpanded = expandState.get(position);
        expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expandState.put(position, expandableLayout.getVisibility() != View.VISIBLE);
                onClickButton(expandableLayout, buttonLayout);
            }
        });
        for (int k = ((6 * j) - 5); k <= (6 * j); k++) {
            int i = k % 6 == 0 ? 6 : k % 6;
            String t = getSubCode((j > 2 ? b : Character.toString(rn.charAt(5))) + String.valueOf(k));
            String[] a = getSubDetails(t);
            ((TextView) view.findViewById(getId((i - 1) * 4))).setText((t + " : " + a[0]));
            ((TextView) view.findViewById(getId(((i - 1) * 4) + 1))).setText(a[1]);
            String s = null;
            try {
                s = object.getString(String.valueOf(k));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ((TextView) view.findViewById(getId(((i - 1) * 4) + 2))).setText(s);
            ((TextView) view.findViewById(getId(((i - 1) * 4) + 2))).setTextColor(getStatus(s) ?
                    Color.parseColor("#ff0000") : Color.parseColor("#00dd00"));
            ((TextView) view.findViewById(getId(((i - 1) * 4) + 3))).
                    setText(getSubCode((j > 2 ? b : Character.toString(rn.charAt(5))) + String.valueOf(k)));
        }
        return view;
    }

}
