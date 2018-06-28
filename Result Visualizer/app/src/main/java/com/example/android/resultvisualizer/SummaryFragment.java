package com.example.android.resultvisualizer;

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

public class SummaryFragment extends Fragment {

    private String rn;

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
        rn = getArguments().getString("rn");
        JSONObject object = jsonObjFromFile().optJSONObject(rn);
        final ArrayList<Summary> list = new ArrayList<Summary>();
        for (int i = 1; i <= 3; i++)
            list.add(new Summary(object, i));
        SummaryAdapter adapter = new SummaryAdapter(getActivity(), list, rn);
        View view = getLayoutInflater().inflate(R.layout.summary2, null);
        ((TextView) view.findViewById(R.id.t)).setText(("Student Details"));
        try {
            ((TextView) view.findViewById(R.id.name)).setText(object.getString("Name"));
            ((TextView) view.findViewById(R.id.rn2)).setText(object.getString("Roll No(2)"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView) view.findViewById(R.id.rn)).setText(rn);
        final View buttonLayout = (View) view.findViewById(R.id.buttonm);
        final CardView cv = (CardView) view.findViewById(R.id.cvm);
        final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.expandableLayoutm);
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
