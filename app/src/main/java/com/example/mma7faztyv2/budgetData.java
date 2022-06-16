package com.example.mma7faztyv2;

import android.util.Log;

import java.util.ArrayList;

public class budgetData extends calcData {
    private long id;
    private String title;
    private String date;
    private String time;
    private calcData thecalcData;

    public calcData getThecalcData() {
        return thecalcData;
    }

    public budgetData(long id, String title, dateTime dateTime, calcData calcData) {
        super(calcData.getMax(),calcData.getSpent(),calcData.getRemain(),calcData.getAv_no());
        this.id = id;
        this.title = title;
        this.date = dateTime.getDate();
        this.time = dateTime.getTime();
        this.thecalcData=calcData;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void recyclerPrinter(String tag,String comment)
    {
        Log.i(tag, String.format("recyclerPrinter: %s ---------------------------------", comment));

        Integer theid=-1;
        String thetitle="",  thedate="",  thetime="";
        if ((int)id!=-1) theid=(int)id;
        else Log.i(tag, "recyclerPrinter: error in the ID:"+(int)id );

        if (title!=null)thetitle=title;
        else Log.i(tag, "recyclerPrinter: error in the title: "+title );

        if (date!=null)thedate=date;
        else Log.i(tag, "recyclerPrinter: error in the date: "+ date );

        if (time!=null)thetime=time;
        else Log.i(tag, "recyclerPrinter: error in the time: "+ time );

        Log.i(tag, String.format(" recyclerPrinter: ID:%s " + ", title:%s , date:%s , time:%s\n total:%s ,spent:%s ,remain:%s ,av_no:%s",
                theid,thetitle,thedate,thetime,thecalcData.getMax(),thecalcData.getSpent(),thecalcData.getRemain(),thecalcData.getAv_no()));

    }

    public static void  recyclerArrayPrinter(String tag, String comment, ArrayList<budgetData> therecyclerDataArray)
    {
        for (budgetData i:therecyclerDataArray)
            i.recyclerPrinter(tag,comment);
    }

}


