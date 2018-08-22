package com.example.android.resultvisualizer;

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

public class SummaryFragment extends Fragment {

    public static boolean isExpanded = true;

    public SummaryFragment() {
    }

    public static SummaryFragment newInstance(String s) {
        SummaryFragment fragment = new SummaryFragment();
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
        ArrayList<Summary> list = new ArrayList<>();
        for (int i = 1; i <= l; i++)
            list.add(new Summary(object, i));
        SummaryAdapter adapter = new SummaryAdapter(Objects.requireNonNull(getActivity()), list, rn);
        View view = getLayoutInflater().inflate(R.layout.summary2, null);
        ((TextView) view.findViewById(R.id.t)).setText(("Student Details"));
        try {
            ((TextView) view.findViewById(R.id.name)).setText(object.getString("Name"));
            ((TextView) view.findViewById(R.id.rn2)).setText(object.getString("Roll No(2)"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView) view.findViewById(R.id.rn)).setText(rn);
        final View buttonLayout = view.findViewById(R.id.buttonm);
        final ConstraintLayout expandableLayout = view.findViewById(R.id.expandableLayoutm);
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
        ListView listView = rootView.findViewById(R.id.list);
        listView.addHeaderView(view,
                null, true);
        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.summary_list, container, false);
        try {
            init(rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }

}
