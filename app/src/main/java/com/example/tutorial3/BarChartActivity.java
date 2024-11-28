package com.example.tutorial3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        if(ContextCompat.checkSelfPermission(BarChartActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(BarChartActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        float meanSet1 = getMeanFromCsvSet("/sdcard/csv_dir/data1");
        float stdSet1 = getStdFromCsvSet("/sdcard/csv_dir/data1");

        float meanSet2 = getMeanFromCsvSet("/sdcard/csv_dir/data1");
        float stdSet2 = getStdFromCsvSet("/sdcard/csv_dir/data1");

        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);

        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0f, meanSet1));
        barEntries.add(new BarEntry(1f, stdSet1));
        barEntries.add(new BarEntry(2f, meanSet2));
        barEntries.add(new BarEntry(3f, stdSet2));

    }
}