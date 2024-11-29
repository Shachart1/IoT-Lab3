package com.example.tutorial3;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvServiceClass {

    public CsvServiceClass() {
    }

    public ArrayList<String[]> CsvRead(String path){
        ArrayList<String[]> CsvData = new ArrayList<>();
        try {
            File file = new File(path);
            CSVReader reader = new CSVReader(new FileReader(file));
            String[]nextline;
            while((nextline = reader.readNext())!= null){
                if(nextline != null){
                    CsvData.add(nextline);

                }
            }

        }catch (Exception e){}
        return CsvData;
    }

    public void saveToCsv(String path, String str1, String str2, AppCompatActivity callingActivity){
        try{
            File file = new File(path);
            file.mkdirs();
            String csv = path + ".csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv,true));
            String row[]= new String[]{str1,str2};
            csvWriter.writeNext(row);
            csvWriter.close();
        } catch (IOException e) {
            Toast.makeText(callingActivity,"ERROR",Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }

}
