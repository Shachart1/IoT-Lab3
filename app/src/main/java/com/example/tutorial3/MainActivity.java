package com.example.tutorial3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class MainActivity extends AppCompatActivity {
    LineChart mpLineChart;
    int counter = 1;
    int val = ((int) Math.random() * 100);
    int val2 = ((int) Math.random() * 100);

    boolean isDeleting;
    private Handler mHandlar = new Handler();  //Handlar is used for delay definition in the loop

    private final CsvServiceClass csvService = new CsvServiceClass();


    public MainActivity() throws FileNotFoundException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        mpLineChart = (LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 =  new LineDataSet(dataValues1(), "Data Set 1");
        lineDataSet1.setColor(R.color.teal_200);
        LineDataSet lineDataSet2 = new LineDataSet(dataValues1(), "Data Set 2");
        lineDataSet2.setColor(R.color.black);

        ArrayList<ILineDataSet> dataSetsLine = new ArrayList<>();

        dataSetsLine.add(lineDataSet1);
        dataSetsLine.add(lineDataSet2);

        LineData data = new LineData(dataSetsLine);

        mpLineChart.setData(data);
        mpLineChart.invalidate();

        Button buttonClear = (Button) findViewById(R.id.button1);
        Button buttonCsvShow = (Button) findViewById(R.id.button2);

        buttonCsvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLoadCSV();

            }
        });

        LineDataSet finalLineDataSet = lineDataSet1;

        Runnable DataUpdate = new Runnable(){
            @Override
            public void run() {
                while (isDeleting) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        Log.w("Debugging", "Sleeping Interrupted");
                    }

                }

                data.addEntry(new Entry(counter,val),0);
                data.addEntry(new Entry(counter, val2), 1);
                finalLineDataSet.notifyDataSetChanged(); // let the data know a dataSet changed
                mpLineChart.notifyDataSetChanged(); // let the chart know it's data changed
                mpLineChart.invalidate(); // refresh
                val = (int) (Math.random() * 100);
                val2 = (int) (Math.random() * 100);

                csvService.saveToCsv("/sdcard/csv_dir/data1",String.valueOf(counter),String.valueOf(val), MainActivity.this);
                csvService.saveToCsv("/sdcard/csv_dir/data2",String.valueOf(counter),String.valueOf(val2), MainActivity.this);

                counter += 1;
                mHandlar.postDelayed(this,500);


            }
        };

        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isDeleting = true;
                Toast.makeText(getApplicationContext(),"Clear",Toast.LENGTH_SHORT).show();
                LineData data = mpLineChart.getData();
                ILineDataSet set1 = data.getDataSetByIndex(0);
                ILineDataSet set2 = data.getDataSetByIndex(1);
                while(set1.removeLast()) {}
                while(set2.removeLast()) {}
                val=40;
                val2=40;
                counter = 1;
                isDeleting = false;
            }

        });

        
        mHandlar.postDelayed(DataUpdate,500);
    }



    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0,0));
        return dataVals;
    }

   private void OpenLoadCSV(){
        Intent intent = new Intent(this,LoadCSV.class);
        startActivity(intent);
   }


}
