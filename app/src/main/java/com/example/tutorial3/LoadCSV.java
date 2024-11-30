package com.example.tutorial3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import java.util.List;


public class LoadCSV extends AppCompatActivity {

    private final CsvServiceClass csvService = new CsvServiceClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_csv);
        Button BackButton = (Button) findViewById(R.id.button_back);
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);

        ArrayList<String[]> csvData1 = new ArrayList<>();
        ArrayList<String[]> csvData2 = new ArrayList<>();

        csvData1 = csvService.CsvRead("/sdcard/csv_dir/data1.csv");
        csvData2 = csvService.CsvRead("/sdcard/csv_dir/data2.csv");

        LineDataSet lineDataSet1 =  new LineDataSet(DataValues(csvData1),"Data Set 1");
        lineDataSet1.setColor(R.color.teal_200);
        lineDataSet1.setCircleColor(R.color.teal_200);
        LineDataSet lineDataSet2 =  new LineDataSet(DataValues(csvData2),"Data Set 2");
        lineDataSet2.setColor(R.color.black);
        lineDataSet2.setCircleColor(R.color.black);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBack();
            }
        });

        Button clearButton = findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineChart.getData() != null) {
                    lineChart.clear();
                    lineDataSet2.clear();
                    lineDataSet1.clear();
                } else {
                    Toast.makeText(LoadCSV.this, "The chart is already clear.", Toast.LENGTH_SHORT).show();
                }
                lineChart.invalidate();
            }
        });
    }

    private void ClickBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    private ArrayList<Entry> DataValues(ArrayList<String[]> csvData){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        for (int i = 0; i < csvData.size(); i++){

            dataVals.add(new Entry(i,Integer.parseInt(csvData.get(i)[1])));


        }

            return dataVals;
    }

}