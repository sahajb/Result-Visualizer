package com.example.android.resultvisualizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.android.resultvisualizer.Utilities.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.resultvisualizer.Utilities.AnimationUtils.onClickButton;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;

public class SummaryFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String rn;

    public static boolean isExpanded = true;

    public static SharedPreferences preferences;

    private JSONObject object = null;

    SummaryAdapter adapter;

    public SummaryFragment() {
    }

    public static SummaryFragment newInstance(String s, Context context) {
        SummaryFragment fragment = new SummaryFragment();
        fragment.setRetainInstance(true);
        Bundle bundle = new Bundle();
        bundle.putString("rn", s);
        fragment.setArguments(bundle);
        isExpanded = true;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return fragment;
    }

    private void init(View rootView) {
        rn = getArguments().getString("rn");
        object = jsonObjFromFile().optJSONObject(rn);
        ArrayList<Summary> list = new ArrayList<Summary>();
        for (int i = 1; i <= 3; i++)
            list.add(new Summary(object, i));
        adapter = new SummaryAdapter(getActivity(), list, rn);
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
                null, true);
        ((ListView) rootView.findViewById(R.id.list)).setHeaderDividersEnabled(true);
        ((ListView) rootView.findViewById(R.id.list)).setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.summary_list, container, false);
        init(rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_result, menu);
        menu.findItem(R.id.action_settings).setTitle((preferences.getBoolean("highlight", true) ? "Disable" : "Enable") +
                " Highlighting");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                PrefUtils.setHighlight(getContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setTitle((preferences.getBoolean("highlight", true) ? "Disable" : "Enable") +
                " Highlighting");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}
