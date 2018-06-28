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

public class StatsAdapter extends ArrayAdapter<Stats> {

    private SparseBooleanArray expandState = new SparseBooleanArray();
    private String rn;

    public StatsAdapter(@NonNull Context context, @NonNull ArrayList<Stats> objects, String s) {
        super(context, 0, objects);
        rn = s;
        for (int i = 0; i < objects.size(); i++) {
            expandState.append(i, true);
        }
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stats stats = getItem(position);
        int j = stats.getPosition();
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.stats, parent, false);
        JSONObject object = stats.getObject();
        int c = stats.getClr();
        int s = stats.getSub();
        ((TextView) view.findViewById(R.id.sem)).setText(("Sem - " + String.valueOf(j)));
        ((TextView) view.findViewById(R.id.clr)).setText(("Subjects cleared"));
        ((TextView) view.findViewById(R.id.clrn)).setText((String.valueOf(c) + "/" + String.valueOf(s)));
        ((TextView) view.findViewById(R.id.clrn)).setTextColor(c != s ? Color.parseColor("#ff0000") :
                Color.parseColor("#00dd00"));
        ((TextView) view.findViewById(R.id.s1)).setText(("University Rank"));
        ((TextView) view.findViewById(R.id.s2)).setText(("Branch Rank"));
        ((TextView) view.findViewById(R.id.s3)).setText(("Total Credits"));
        try {
            ((TextView) view.findViewById(R.id.gpa)).setText(object.getString("SGPA(" + String.valueOf(j - 1) + ")"));
            ((TextView) view.findViewById(R.id.c1)).setText(object.getString("R" + String.valueOf(j - 1)));
            ((TextView) view.findViewById(R.id.c2)).setText(object.getString("DR" + String.valueOf(j - 1)));
            ((TextView) view.findViewById(R.id.c3)).setText((object.getString("TC(" + String.valueOf(j - 1) + ")")
                    + "/" + stats.getCr()));
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
        return view;
    }

}

