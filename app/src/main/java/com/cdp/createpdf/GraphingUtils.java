package com.cdp.createpdf;


import android.graphics.Color;

import androidx.annotation.Keep;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;

@Keep
public class GraphingUtils {

    private GraphingUtils(){}

    public static void setBarGraphic(BarChart newBarChart, float[] dataValues, String[] dataLabels,
                                     int barsColor, String cornerDescription, int textColor,
                                     int backgroundColor, int animationTime, String label){
        //Set Bar Chart Basics
        newBarChart =(BarChart)getSameChart(newBarChart,cornerDescription, textColor,
                backgroundColor,animationTime);
        newBarChart.setDrawGridBackground(true);
        newBarChart.setGridBackgroundColor(Color.WHITE);
        newBarChart.setDrawBarShadow(false);

        //Ser Bar Chart Legend
        Legend legend=newBarChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entries = new ArrayList<>();
        LegendEntry entry = new LegendEntry();
        entry.formColor = Color.parseColor("#9CC773");
        entry.label = "Irradiación Global Horizontal [KWh/m2/d]";
        entries.add(entry);

        legend.setCustom(entries);

        //Set bar data and configuration
        BarDataSet barDataSet=
                (BarDataSet)getData(new BarDataSet(getBarEntries(dataLabels, dataValues),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.60f);
        newBarChart.setData(barData);
        newBarChart.invalidate();

        //Set X axis
        newBarChart.getXAxis().setGranularityEnabled(true);
        newBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        newBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dataLabels));
        newBarChart.getXAxis().setTextSize(6);
        newBarChart.setDrawValueAboveBar(true);


        //Set Y Axis
        newBarChart.getAxisLeft().setSpaceTop(40);
        newBarChart.getAxisLeft().setAxisMinimum(0);
        newBarChart.getAxisRight().setEnabled(false);
    }


    private static DataSet getData(DataSet dataSet){
        dataSet.setColor(Color.parseColor("#9CC773"));
        dataSet.setValueTextSize(12);
        return dataSet;
    }

    private static Chart getSameChart(Chart chart,String description,int textColor,
                                      int background,int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(10);
        chart.getDescription().setTextColor(Color.parseColor("#006400"));
        chart.setBackgroundColor(background);
        chart.animateY(3000);
        return chart;
    }

    private static ArrayList<BarEntry>getBarEntries(String[] dataLabels, float[] dataValues){
        ArrayList<BarEntry>entries=new ArrayList<>();
        for(int i = 0; i< dataLabels.length; i++)
            entries.add(new BarEntry(i, dataValues[i]));
         //   entries.add(new BarEntry(i, (float) dataValues.get(i)));
        // entries.add(new BarEntry(i, 7));
        return entries;
    }

}