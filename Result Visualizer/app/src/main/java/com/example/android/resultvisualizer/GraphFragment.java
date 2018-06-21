package com.example.android.resultvisualizer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;

import static com.example.android.resultvisualizer.Utilities.AnimationUtils.onClickButton;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;

public class GraphFragment extends Fragment {

    private String rn;
    private static SparseBooleanArray expandState = new SparseBooleanArray();

    public GraphFragment() {
    }

    public static GraphFragment newInstance(String s) {
        GraphFragment fragment = new GraphFragment();
        fragment.setRetainInstance(true);
        Bundle bundle = new Bundle();
        bundle.putString("rn", s);
        fragment.setArguments(bundle);
        for (int i = 0; i < 4; i++) {
            expandState.append(i, false);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        rn = getArguments().getString("rn");
        JSONObject object = jsonObjFromFile(getContext()).optJSONObject(rn);
        {
            final LineChart graph= (LineChart) view.findViewById(R.id.graph_r);
            ((TextView) view.findViewById(R.id.r)).setText(("University Rank"));
            ArrayList<Entry> entries=new ArrayList<>();
            try {
                entries.add(new Entry(1,Integer.valueOf(object.getString("R0"))));
                entries.add(new Entry(2,Integer.valueOf(object.getString("R1"))));
                entries.add(new Entry(3,Integer.valueOf(object.getString("R0"))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LineDataSet dataSet=new LineDataSet(entries,"Rank");
            dataSet.setDrawFilled(true);
            dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(),R.drawable.line_chart_gradient));
            dataSet.setColor(Color.BLACK);
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setLineWidth(2f);
            dataSet.setCircleRadius(5f);
            dataSet.setValueTextSize(10f);
            dataSet.setHighLightColor(Color.BLACK);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            final String[] lab = new String[] { "Sem-I", "Sem-II", "Sem-III" };
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return lab[(int) value-1];
                }
            };
            IValueFormatter valueFormatter=new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + ((int) value);
                }
            };
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(dataSet);
            LineData lineData=new LineData(dataSets);
            lineData.setValueFormatter(valueFormatter);
            lineData.setValueTextSize(15f);
            graph.setData(lineData);
            graph.getDescription().setEnabled(false);
            XAxis xAxis = graph.getXAxis();
            xAxis.setAxisLineWidth(1f);
            xAxis.setTextSize(15f);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);
            graph.setTouchEnabled(true);
            graph.getAxisRight().setEnabled(false);
            graph.getAxisLeft().setAxisMinimum(0);
            graph.getAxisLeft().setAxisMaximum(1800);
            graph.getAxisLeft().setAxisLineWidth(1f);
            graph.getAxisLeft().enableGridDashedLine(20f,20f,0f);
            graph.getAxisLeft().setTextSize(12f);
            final View buttonLayout = (View) view.findViewById(R.id.button_r);
            final CardView cv = (CardView) view.findViewById(R.id.cv_r);
            final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.ex_r);
            final boolean isExpanded = expandState.get(0);
            expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(0) ? 180f : 0f);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(0, expandableLayout.getVisibility() != View.VISIBLE);
                    if(expandState.get(0))
                        graph.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        {
            final LineChart graph= (LineChart) view.findViewById(R.id.graph_dr);
            ((TextView) view.findViewById(R.id.dr)).setText(("Branch Rank"));
            ArrayList<Entry> entries=new ArrayList<>();
            try {
                entries.add(new Entry(1,Integer.valueOf(object.getString("DR0"))));
                entries.add(new Entry(2,Integer.valueOf(object.getString("DR1"))));
                entries.add(new Entry(3,Integer.valueOf(object.getString("DR0"))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LineDataSet dataSet=new LineDataSet(entries,"Rank");
            dataSet.setDrawFilled(true);
            dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(),R.drawable.line_chart_gradient));
            dataSet.setColor(Color.BLACK);
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setLineWidth(2f);
            dataSet.setCircleRadius(5f);
            dataSet.setValueTextSize(10f);
            dataSet.setHighLightColor(Color.BLACK);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            final String[] lab = new String[] { "Sem-I", "Sem-II", "Sem-III" };
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return lab[(int) value-1];
                }
            };
            IValueFormatter valueFormatter=new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + ((int) value);
                }
            };
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(dataSet);
            LineData lineData=new LineData(dataSets);
            lineData.setValueFormatter(valueFormatter);
            lineData.setValueTextSize(15f);
            graph.setData(lineData);
            graph.getDescription().setEnabled(false);
            XAxis xAxis = graph.getXAxis();
            xAxis.setAxisLineWidth(1f);
            xAxis.setTextSize(15f);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);
            graph.setTouchEnabled(true);
            graph.getAxisRight().setEnabled(false);
            graph.getAxisLeft().setAxisMinimum(0);
            graph.getAxisLeft().setAxisMaximum(100);
            graph.getAxisLeft().setAxisLineWidth(1f);
            graph.getAxisLeft().enableGridDashedLine(20f,20f,0f);
            graph.getAxisLeft().setTextSize(12f);
            final View buttonLayout = (View) view.findViewById(R.id.button_dr);
            final CardView cv = (CardView) view.findViewById(R.id.cv_dr);
            final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.ex_dr);
            final boolean isExpanded = expandState.get(1);
            expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(1) ? 180f : 0f);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(1, expandableLayout.getVisibility() != View.VISIBLE);
                    if(expandState.get(1))
                        graph.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        {
            final BarChart graph= (BarChart) view.findViewById(R.id.graph_gpa);
            ((TextView) view.findViewById(R.id.gpa)).setText(("GPA Distribution"));
            ArrayList<BarEntry> entries=new ArrayList<>();
            try {
                entries.add(new BarEntry(1,Float.valueOf(object.getString("SGPA(0)"))));
                entries.add(new BarEntry(2,Float.valueOf(object.getString("SGPA(1)"))));
                entries.add(new BarEntry(3,Float.valueOf(object.getString("SGPA(0)"))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            BarDataSet dataSet=new BarDataSet(entries,"GPA");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueTextSize(15f);
            BarData data=new BarData(dataSet);
            IValueFormatter valueFormatter=new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return ""+String.format("%.02f", value);
                }
            };
            data.setValueFormatter(valueFormatter);
            graph.setData(data);
            graph.getDescription().setEnabled(false);
            XAxis xAxis = graph.getXAxis();
            xAxis.setAxisLineWidth(1f);
            xAxis.setTextSize(15f);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            final String[] lab = new String[] { "Sem-I", "Sem-II", "Sem-III" };
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return lab[(int) value-1];
                }
            };
            xAxis.setValueFormatter(formatter);
            graph.getAxisRight().setEnabled(false);
            graph.getAxisLeft().setAxisMinimum(0);
            graph.getAxisLeft().setAxisMaximum(10);
            graph.getAxisLeft().setAxisLineWidth(1f);
            graph.getAxisLeft().enableGridDashedLine(20f,20f,0f);
            graph.getAxisLeft().setTextSize(12f);
            graph.getAxisLeft().setLabelCount(11,true);
            graph.setTouchEnabled(true);
            final View buttonLayout = (View) view.findViewById(R.id.button_gpa);
            final CardView cv = (CardView) view.findViewById(R.id.cv_gpa);
            final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.ex_gpa);
            final boolean isExpanded = expandState.get(2);
            expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(2) ? 180f : 0f);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(2, expandableLayout.getVisibility() != View.VISIBLE);
                    if(expandState.get(2))
                        graph.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        {
            final PieChart graph= (PieChart) view.findViewById(R.id.graph_cred);
            ((TextView) view.findViewById(R.id.cred)).setText(("Credit Distribution"));
            ArrayList<PieEntry> entries=new ArrayList<>();
            try {
                entries.add(new PieEntry(Integer.valueOf(object.getString("TC(0)")),"Sem - I"));
                entries.add(new PieEntry(Integer.valueOf(object.getString("TC(1)")),"Sem - II"));
                entries.add(new PieEntry(Integer.valueOf(object.getString("TC(0)")),"Sem - III"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PieDataSet dataSet=new PieDataSet(entries,"Credits");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueTextSize(15f);
            IValueFormatter valueFormatter=new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return "" + ((int) value);
                }
            };
            PieData data=new PieData(dataSet);
            data.setValueFormatter(valueFormatter);
            graph.setData(data);
            graph.getDescription().setEnabled(false);
            final View buttonLayout = (View) view.findViewById(R.id.button_cred);
            final CardView cv = (CardView) view.findViewById(R.id.cv_cred);
            final ConstraintLayout expandableLayout = (ConstraintLayout) view.findViewById(R.id.ex_cred);
            final boolean isExpanded = expandState.get(3);
            expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            buttonLayout.setRotation(expandState.get(3) ? 180f : 0f);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    expandState.put(3, expandableLayout.getVisibility() != View.VISIBLE);
                    if(expandState.get(3))
                        graph.animateY(1500, Easing.EasingOption.EaseInOutBack);
                    onClickButton(expandableLayout, buttonLayout);
                }
            });
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
