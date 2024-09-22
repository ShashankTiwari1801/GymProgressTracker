package com.example.gymprogresstracker.util;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

public class GraphHelper {
    Context context;

    public GraphHelper(Context context) {
        this.context = context;
    }

    public LineData getLineData(List<Entry> data, int backgroundDrawable, int graphColor) {
        LineDataSet lineDataSet = new LineDataSet(data, "");
        customizeLineDataSet(lineDataSet, backgroundDrawable, graphColor);
        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    public void customizeLineDataSet(LineDataSet lineDataSet, int backgroundDrawable, int graphColor) {
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColor(graphColor);
        //lineDataSet.setColor(Color.parseColor("#41FFFFFF"));
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setLineWidth(3);
        //lineDataSet.setFillColor(graphColor);
        //lineDataSet.setGradientColor(Color.BLUE, Color.RED);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(context, backgroundDrawable));
        //lineDataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.gradient));
    }

    public void customizeLineChart(LineChart lineChart) {
        YAxis yAxisL = lineChart.getAxisLeft();
        YAxis yAxisR = lineChart.getAxisRight();
        XAxis xAxisU = lineChart.getXAxis();
        xAxisU.setDrawGridLines(false);
        yAxisR.setEnabled(false);
        yAxisL.setDrawGridLines(false);
        yAxisL.setTextColor(Color.WHITE);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDescription(null);
        //lineChart.setBackgroundColor(Color.BLACK);
        xAxisU.setEnabled(false);
    }
}
