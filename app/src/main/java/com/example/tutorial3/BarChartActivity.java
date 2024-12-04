package com.example.tutorial3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    private final CsvServiceClass csvService = new CsvServiceClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        float meanSet1 = getMeanFromCsvSet("/sdcard/csv_dir/data1.csv");
        float stdSet1 = getStdFromCsvSet("/sdcard/csv_dir/data1.csv", meanSet1);

        float meanSet2 = getMeanFromCsvSet("/sdcard/csv_dir/data2.csv");
        float stdSet2 = getStdFromCsvSet("/sdcard/csv_dir/data2.csv", meanSet2);

        Log.d("DEBUGGING", "mean1 - " + meanSet1 + "std1 - " + stdSet1);
        Log.d("DEBUGGING", "mean2 - " + meanSet2 + "std2 - " + stdSet2);
        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);

        BarDataSet meanSet1Bar = this.createBar(0f, meanSet1, "Mean1", Color.RED);
        BarDataSet stdSet1Bar = this.createBar(1f, stdSet1, "Std1", Color.YELLOW);
        BarDataSet meanSet2Bar = this.createBar(3f, meanSet2, "Mean2", Color.BLUE);
        BarDataSet stdSet2Bar = this.createBar(4f, stdSet2, "Std2", Color.GREEN);

        BarData data = new BarData(meanSet1Bar, stdSet1Bar, meanSet2Bar, stdSet2Bar);
        data.setBarWidth(0.9f);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();

        Button buttonBack = findViewById(R.id.button_back);
        Button buttonCsvShow = (Button) findViewById(R.id.button2);
        buttonCsvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLoadCSV();

            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BarChartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private BarDataSet createBar(float x, float value, String label, int color) {
        List<BarEntry> barEntry = new ArrayList<>();
        barEntry.add(new BarEntry(x, value));
        BarDataSet bar = new BarDataSet(barEntry, label);
        bar.setColor(color);
        return bar;
    }

    private float getMeanFromCsvSet(String path) {
        List<Float> values = this.getYValues(path);
        float yValuesSum = 0f;

        for (float pointValue : values) {
            yValuesSum += pointValue;
        }

        return yValuesSum / values.size();
    }

    // TODO - Make sure this is the calculation we should use
    private float getStdFromCsvSet(String path, float mean) {
        List<Float> values = this.getYValues(path);
        float squareDiffFromMeanSum = 0f;

        for (float pointValue : values) {
            squareDiffFromMeanSum += Math.pow(pointValue - mean, 2);
        }

        return (float) Math.pow(squareDiffFromMeanSum / values.size(), 0.5);
    }

    private List<Float> getYValues(String path) {
        List<String[]> pointsFromCsv = csvService.CsvRead(path);
        List<Float> yValues = new ArrayList<>();

        for (String[] point : pointsFromCsv) {
            if (point.length > 0) {
                yValues.add(Float.parseFloat(point[1]));
            } else {
                Log.d("DEBUGGING", "Point doesn't have two values - " + point);
            }
        }

        return yValues;
    }
    private void OpenLoadCSV(){
        Intent intent = new Intent(this,LoadCSV.class);
        startActivity(intent);
    }
}