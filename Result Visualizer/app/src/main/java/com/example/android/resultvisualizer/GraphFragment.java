package com.example.android.resultvisualizer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.resultvisualizer.Utilities.PrefUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.android.resultvisualizer.Utilities.AnimationUtils.onClickButton;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;

public class GraphFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static SparseBooleanArray expandState = new SparseBooleanArray();

    private LineChart gd;

    private LineChart gr;

    private BarChart gg;

    private PieChart gc;

    public static SharedPreferences preferences;

    public GraphFragment() {
    }

    public static GraphFragment newInstance(String s, Context context, int q) {
        GraphFragment fragment = new GraphFragment();
        fragment.setRetainInstance(true);
        Bundle bundle = new Bundle();
        bundle.putString("rn", s);
        bundle.putInt("quality", q);
        fragment.setArguments(bundle);
        for (int i = 0; i < 4; i++)
            expandState.append(i, true);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        String rn = Objects.requireNonNull(getArguments()).getString("rn");
        JSONObject object = jsonObjFromFile().optJSONObject(rn);
        JSONObject info = jsonObjFromFile().optJSONObject("meta-data");
        {
            gr = view.findViewById(R.id.graph_r);
            ((TextView) view.findViewById(R.id.r)).setText(("University Rank"));
            ArrayList<Entry> entries = new ArrayList<>();
            int d = 0, l = 0;
            try {
                l = info.getInt("Sems");
                for (int i = 0; i < l; i++) {
                    entries.add(new Entry(i + 1, Integer.valueOf(object.getString("R" + String.valueOf(i)))));
                    d = Math.max(d, Integer.valueOf(object.getString("R" + String.valueOf(i))));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LineDataSet dataSet = new LineDataSet(entries, "Rank");
            dataSet.setDrawFilled(true);
            dataSet.setFillDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.line_chart_gradient));
            dataSet.setColor(Color.BLACK);
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setLineWidth(2f);
            dataSet.setCircleRadius(5f);
            dataSet.setValueTextSize(10f);
            dataSet.setHighLightColor(Color.BLACK);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            final String[] lab = new String[l];
            for (int i = 0; i < l; i++)
                lab[i] = "Sem-" + String.valueOf(i + 1);
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return lab[(int) value - 1];
                }
            };
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + ((int) value);
                }
            };
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);
            LineData lineData = new LineData(dataSets);
            lineData.setValueFormatter(valueFormatter);
            lineData.setValueTextSize(15f);
            gr.setData(lineData);
            gr.getDescription().setEnabled(false);
            XAxis xAxis = gr.getXAxis();
            xAxis.setAxisLineWidth(1f);
            xAxis.setTextSize(15f);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);
            gr.setTouchEnabled(preferences.getBoolean("touch", true));
            gr.getAxisRight().setEnabled(false);
            gr.getAxisLeft().setAxisMinimum(0);
            d = ((d + 20) / 100) + 1;
            gr.getAxisLeft().setAxisMaximum(Math.min(1800, d * 100));
            gr.getAxisLeft().setAxisLineWidth(1f);
            gr.getAxisLeft().enableGridDashedLine(20f, 20f, 0f);
            gr.getAxisLeft().setTextSize(12f);
            final View buttonLayout = view.findViewById(R.id.button_r);
            final ConstraintLayout expandableLayout = view.findViewById(R.id.ex_r);
            expandableLayout.setVisibility(expandState.get(0) ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(0) ? 180f : 0f);
            buttonLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(0, expandableLayout.getVisibility() != View.VISIBLE);
                    if (expandState.get(0))
                        gr.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        {
            gd = view.findViewById(R.id.graph_dr);
            ((TextView) view.findViewById(R.id.dr)).setText(("Branch Rank"));
            ArrayList<Entry> entries = new ArrayList<>();
            int d = 0, l = 0;
            try {
                l = info.getInt("Sems");
                for (int i = 0; i < l; i++) {
                    entries.add(new Entry(i + 1, Integer.valueOf(object.getString("DR" + String.valueOf(i)))));
                    d = Math.max(d, Integer.valueOf(object.getString("DR" + String.valueOf(i))));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LineDataSet dataSet = new LineDataSet(entries, "Rank");
            dataSet.setDrawFilled(true);
            dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_chart_gradient));
            dataSet.setColor(Color.BLACK);
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setLineWidth(2f);
            dataSet.setCircleRadius(5f);
            dataSet.setValueTextSize(10f);
            dataSet.setHighLightColor(Color.BLACK);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            final String[] lab = new String[l];
            for (int i = 0; i < l; i++)
                lab[i] = "Sem-" + String.valueOf(i + 1);
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return lab[(int) value - 1];
                }
            };
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + ((int) value);
                }
            };
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);
            LineData lineData = new LineData(dataSets);
            lineData.setValueFormatter(valueFormatter);
            lineData.setValueTextSize(15f);
            gd.setData(lineData);
            gd.getDescription().setEnabled(false);
            XAxis xAxis = gd.getXAxis();
            xAxis.setAxisLineWidth(1f);
            xAxis.setTextSize(15f);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);
            gd.setTouchEnabled(preferences.getBoolean("touch", true));
            gd.getAxisRight().setEnabled(false);
            gd.getAxisLeft().setAxisMinimum(0);
            d = ((d + 2) / 10) + 1;
            gd.getAxisLeft().setAxisMaximum(d * 10);
            gd.getAxisLeft().setAxisLineWidth(1f);
            gd.getAxisLeft().enableGridDashedLine(20f, 20f, 0f);
            gd.getAxisLeft().setTextSize(12f);
            final View buttonLayout = view.findViewById(R.id.button_dr);
            final ConstraintLayout expandableLayout = view.findViewById(R.id.ex_dr);
            expandableLayout.setVisibility(expandState.get(1) ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(1) ? 180f : 0f);
            buttonLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(1, expandableLayout.getVisibility() != View.VISIBLE);
                    if (expandState.get(1))
                        gd.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        {
            gg = view.findViewById(R.id.graph_gpa);
            ((TextView) view.findViewById(R.id.gpa)).setText(("GPA Distribution"));
            ArrayList<BarEntry> entries = new ArrayList<>();
            int l = 0;
            try {
                l = info.getInt("Sems");
                for (int i = 0; i < l; i++)
                    entries.add(new BarEntry(i + 1, Float.valueOf(object.getString("SGPA(" + String.valueOf(i) + ")"))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            BarDataSet dataSet = new BarDataSet(entries, "GPA");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueTextSize(15f);
            BarData data = new BarData(dataSet);
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + String.format("%.02f", value);
                }
            };
            data.setValueFormatter(valueFormatter);
            gg.setData(data);
            gg.getDescription().setEnabled(false);
            XAxis xAxis = gg.getXAxis();
            xAxis.setAxisLineWidth(1f);
            xAxis.setTextSize(15f);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            final String[] lab = new String[l];
            for (int i = 0; i < l; i++)
                lab[i] = "Sem-" + String.valueOf(i + 1);
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return lab[(int) value - 1];
                }
            };
            xAxis.setValueFormatter(formatter);
            gg.getAxisRight().setEnabled(false);
            gg.getAxisLeft().setAxisMinimum(0);
            gg.getAxisLeft().setAxisMaximum(10);
            gg.getAxisLeft().setAxisLineWidth(1f);
            gg.getAxisLeft().enableGridDashedLine(20f, 20f, 0f);
            gg.getAxisLeft().setTextSize(12f);
            gg.getAxisLeft().setLabelCount(11, true);
            gg.setTouchEnabled(preferences.getBoolean("touch", true));
            final View buttonLayout = view.findViewById(R.id.button_gpa);
            final ConstraintLayout expandableLayout = view.findViewById(R.id.ex_gpa);
            expandableLayout.setVisibility(expandState.get(2) ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(2) ? 180f : 0f);
            buttonLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(2, expandableLayout.getVisibility() != View.VISIBLE);
                    if (expandState.get(2))
                        gg.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        {
            gc = view.findViewById(R.id.graph_cred);
            ((TextView) view.findViewById(R.id.cred)).setText(("Credit Distribution"));
            ArrayList<PieEntry> entries = new ArrayList<>();
            try {
                int l = info.getInt("Sems");
                for (int i = 0; i < l; i++)
                    entries.add(new PieEntry(Float.valueOf(object.getString("TC(" + String.valueOf(i) + ")")),
                            "Sem - " + String.valueOf(i + 1)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PieDataSet dataSet = new PieDataSet(entries, "Credits");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueTextSize(15f);
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + ((int) value);
                }
            };
            PieData data = new PieData(dataSet);
            data.setValueFormatter(valueFormatter);
            gc.setData(data);
            gc.getDescription().setEnabled(false);
            gc.setTouchEnabled(preferences.getBoolean("touch", true));
            final View buttonLayout = view.findViewById(R.id.button_cred);
            final ConstraintLayout expandableLayout = view.findViewById(R.id.ex_cred);
            expandableLayout.setVisibility(expandState.get(3) ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(3) ? 180f : 0f);
            buttonLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(3, expandableLayout.getVisibility() != View.VISIBLE);
                    if (expandState.get(3))
                        gc.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        return view;
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
        menu.findItem(R.id.action_settings).setTitle((preferences.getBoolean("touch", true) ? "Disable" : "Enable") +
                " Graph Touch");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return true;

            case R.id.action_settings:
                PrefUtils.setTouch(getContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gc.saveToGallery("Credits Distribution.jpg", Objects.requireNonNull(getArguments()).getInt("quality", 50));
                    gd.saveToGallery("Branch Rank.jpg", getArguments().getInt("quality", 50));
                    gr.saveToGallery("University Rank.jpg", getArguments().getInt("quality", 50));
                    gg.saveToGallery("GPA Distribution.jpg", getArguments().getInt("quality", 50));
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Graphs saved in Gallery", Snackbar.LENGTH_LONG).
                            setAction("View", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setDataAndType(FileProvider.getUriForFile(Objects.requireNonNull(getContext()), getActivity().
                                            getApplicationContext().getPackageName() + ".provider", (new File(
                                            "storage/emulated/0/DCIM/GPA Distribution.jpg"))), "image/*");
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    startActivity(intent);
                                }
                            }).show();
                } else
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setTitle((preferences.getBoolean("touch", true) ? "Disable" : "Enable") +
                " Graph Touch");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("touch")) {
            gc.setTouchEnabled(sharedPreferences.getBoolean(key, true));
            gr.setTouchEnabled(sharedPreferences.getBoolean(key, true));
            gd.setTouchEnabled(sharedPreferences.getBoolean(key, true));
            gg.setTouchEnabled(sharedPreferences.getBoolean(key, true));
            Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        }
    }
}

