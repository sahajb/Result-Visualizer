package com.example.android.resultvisualizer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.android.resultvisualizer.Utilities.AnimationUtils.onClickButton;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;
import static com.example.android.resultvisualizer.Utilities.SubjectUtils.getStatus;

public class StatsFragment extends Fragment {

    public static boolean isExpanded = true;

    public StatsFragment() {
    }

    public static StatsFragment newInstance(String s) {
        StatsFragment fragment = new StatsFragment();
        fragment.setRetainInstance(true);
        Bundle bundle = new Bundle();
        bundle.putString("rn", s);
        fragment.setArguments(bundle);
        isExpanded = true;
        return fragment;
    }

    private void init(View rootView) throws JSONException {
        String rn = Objects.requireNonNull(getArguments()).getString("rn");
        JSONObject object = jsonObjFromFile().optJSONObject(rn);
        JSONObject info = jsonObjFromFile().optJSONObject("meta-data");
        int l = info.getInt("Sems");
        final ArrayList<Stats> list = new ArrayList<>();
        int t = 0;
        for (int i = 1; i <= l; i++) {
            int c = 6;
            for (int j = ((6 * i) - 5); j <= (6 * i); j++)
                if (getStatus(object.getString(String.valueOf(j))))
                    c -= 1;
            t += c;
            list.add(new Stats(object, i, c, info.getInt("C" + String.valueOf(i)), 6));
        }
        StatsAdapter adapter = new StatsAdapter(Objects.requireNonNull(getActivity()), list);
        View view = getLayoutInflater().inflate(R.layout.stats, null);
        ((TextView) view.findViewById(R.id.sem)).setText(("Overall"));
        ((TextView) view.findViewById(R.id.clr)).setText(("Subjects cleared"));
        ((TextView) view.findViewById(R.id.clrn)).setText((String.valueOf(t) + "/" + String.valueOf(l * 6)));
        ((TextView) view.findViewById(R.id.clrn)).setTextColor(t != (l * 6) ? Color.parseColor("#ff0000") :
                Color.parseColor("#00dd00"));
        ((TextView) view.findViewById(R.id.s1)).setText(("University Rank"));
        ((TextView) view.findViewById(R.id.s2)).setText(("Branch Rank"));
        ((TextView) view.findViewById(R.id.s3)).setText(("Total Credits"));
        ((TextView) view.findViewById(R.id.gpa)).setText(object.getString("SGPA"));
        ((TextView) view.findViewById(R.id.c1)).setText(object.getString("R"));
        ((TextView) view.findViewById(R.id.c2)).setText(object.getString("DR"));
        ((TextView) view.findViewById(R.id.c3)).setText((object.getString("TC") + "/" + String.valueOf(info.getInt("TC"))));
        final View buttonLayout = view.findViewById(R.id.button);
        final ConstraintLayout expandableLayout = view.findViewById(R.id.expandableLayout);
        isExpanded = true;
        expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        buttonLayout.setRotation(isExpanded ? 180f : 0f);
        buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                isExpanded = expandableLayout.getVisibility() != View.VISIBLE;
                onClickButton(expandableLayout, buttonLayout);
            }
        });
        ((ListView) rootView.findViewById(R.id.list)).addHeaderView(view,
                null, true);
        ((ListView) rootView.findViewById(R.id.list)).setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stats_list, container, false);
        try {
            init(rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }

}
