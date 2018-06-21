package com.example.android.resultvisualizer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.resultvisualizer.Utilities.AnimationUtils.onClickButton;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;

public class SummaryFragment extends Fragment {

    private String rn;

    public static boolean isExpanded = false;

    public SummaryFragment() {
    }

    public static SummaryFragment newInstance(String s) {
        SummaryFragment fragment = new SummaryFragment();
        fragment.setRetainInstance(true);
        Bundle bundle = new Bundle();
        bundle.putString("rn", s);
        fragment.setArguments(bundle);
        isExpanded = false;
        return fragment;
    }

    private void init(View rootView) throws JSONException {
        rn = getArguments().getString("rn");
        JSONObject object = jsonObjFromFile(getContext()).optJSONObject(rn);
        final ArrayList<Summary> list = new ArrayList<Summary>();
        for (int i = 1; i <= 3; i++)
            list.add(new Summary(object, i));
        SummaryAdapter adapter = new SummaryAdapter(getActivity(), list, rn);
        View view = getLayoutInflater().inflate(R.layout.summary2, null);
        ((TextView) view.findViewById(R.id.t)).setText(("Student Details"));
        try {
            ((TextView) view.findViewById(R.id.name)).setText(object.getString("Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView) view.findViewById(R.id.rn)).setText(rn);
        ((TextView) view.findViewById(R.id.br)).setText(rn.charAt(7) == '/' ? rn.substring(5, 7)
                : rn.substring(5, 8));
        final View buttonLayout = (View) view.findViewById(R.id.buttonm);
        final CardView cv = (CardView) view.findViewById(R.id.cvm);
        final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.expandableLayoutm);
        isExpanded = false;
        expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        buttonLayout.setRotation(isExpanded ? 180f : 0f);
        cv.setOnClickListener(new View.OnClickListener() {
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
        View rootView = inflater.inflate(R.layout.summary_list, container, false);
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
    }
}
