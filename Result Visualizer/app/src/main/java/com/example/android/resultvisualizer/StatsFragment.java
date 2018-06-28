package com.example.android.resultvisualizer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.resultvisualizer.Utilities.AnimationUtils.onClickButton;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;
import static com.example.android.resultvisualizer.Utilities.SubjectUtils.getStatus;

public class StatsFragment extends Fragment {

    private String rn;

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
        rn = getArguments().getString("rn");
        JSONObject object = jsonObjFromFile().optJSONObject(rn);
        final ArrayList<Stats> list = new ArrayList<Stats>();
        int t = 0;
        for (int i = 1; i <= 3; i++) {
            int c = 6;
            for (int j = ((6 * i) - 5); j <= (6 * i); j++)
                if (getStatus(object.getString(String.valueOf(j))))
                    c -= 1;
            t += c;
            list.add(new Stats(object, i, c, i > 2 ? 23 : 21, 6));
        }
        StatsAdapter adapter = new StatsAdapter(getActivity(), list, rn);
        View view = getLayoutInflater().inflate(R.layout.stats, null);
        ((TextView) view.findViewById(R.id.sem)).setText(("Overall"));
        ((TextView) view.findViewById(R.id.clr)).setText(("Subjects cleared"));
        ((TextView) view.findViewById(R.id.clrn)).setText((String.valueOf(t) + "/18"));
        ((TextView) view.findViewById(R.id.clrn)).setTextColor(t != 18 ? Color.parseColor("#ff0000") :
                Color.parseColor("#00dd00"));
        ((TextView) view.findViewById(R.id.s1)).setText(("University Rank"));
        ((TextView) view.findViewById(R.id.s2)).setText(("Branch Rank"));
        ((TextView) view.findViewById(R.id.s3)).setText(("Total Credits"));
        ((TextView) view.findViewById(R.id.gpa)).setText(object.getString("SGPA"));
        ((TextView) view.findViewById(R.id.c1)).setText(object.getString("R"));
        ((TextView) view.findViewById(R.id.c2)).setText(object.getString("DR"));
        ((TextView) view.findViewById(R.id.c3)).setText((object.getString("TC") + "/65"));
        final View buttonLayout = (View) view.findViewById(R.id.button);
        final CardView cv = (CardView) view.findViewById(R.id.cv);
        final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.expandableLayout);
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
                null, false);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_result, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
