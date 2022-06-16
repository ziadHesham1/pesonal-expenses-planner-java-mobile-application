package com.example.mma7faztyv2;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class recordsData {
    private double number;
    private String comment;
    private String title;

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public recordsData(String title,double number, String comment) {
        this.number = number;
        this.comment = comment;
        this.title = title;
    }

    public void dataArrayPrinter(String tag, String comment, ArrayList<recordsData> d)
    {
        Log.i(tag, String.format("dataArrayPrinter: %s ---------------------------------", comment));

        if (d!=null)
        {
            for (recordsData i:d)
            {
                i.printrecordData();
            }
        }
        else Log.i(TAG, "dataArrayPrinter: no array to print :"+d);
    }
    public void printrecordData()
    {
        Log.i(TAG, String.format("printrecordData: number=%s , comment = %s",number,comment));
    }
    public static void printrecordDataArray(ArrayList<recordsData> therecordsDataArray)
    {
        Log.i(TAG, String.format("printrecordDataArray list items:%s",therecordsDataArray.size() ));
        for (recordsData recordsData : therecordsDataArray) {
            recordsData.printrecordData();
        }
    }

    public static Double getNumberSum(ArrayList<recordsData> therecordsDataArray)
    {
        Double sum=0.0;
        for (recordsData recordsData : therecordsDataArray) {
            sum += recordsData.number;
        }
        return sum;
    }

}
