package com.example.mma7faztyv2;

import android.util.Log;

import java.util.ArrayList;

public class calcData {
    private double max,spent,remain,av_no;
    private static final String TAG = "calcData";
    public calcData(double max, double spent, double remain, double av_no) {
        this.max = max;
        this.spent = spent;
        this.remain = remain;
        this.av_no = av_no;
    }
    public double getMax() {
        return max;
    }
    public double getSpent() {
        return spent;
    }
    public double getRemain() {
        return remain;
    }
    public double getAv_no() {
        return av_no;
    }
    public void setMax(double max) {
        this.max = max;
    }
    public void setSpent(double spent) {
        this.spent = spent;
    }
    public void setRemain(double remain) {
        this.remain = remain;
    }
    public void setAv_no(double av_no) {
        this.av_no = av_no;
    }
    public void calcPrinter(String tag,String comment){
        Log.i(tag, String.format("calcPrinter: %s ---------------------------------", comment));
        Double a= (double) -1,b= (double) -1,c= (double) -1,d= (double) -1;
        if ((max!=-1))a=max;
        else Log.i(tag, "recyclerPrinter: error in the total: "+  max  );

        if (spent!=-1) b=spent;
        else Log.i(tag, "recyclerPrinter: error in the spent: "+  spent);

        if (remain!=-1)c=remain;
        else Log.i(tag, "recyclerPrinter: error in the remain: "+remain);

        if (av_no!=-1)d=av_no;
        else Log.i(tag, "recyclerPrinter: error in the av-no : "+ av_no);

        Log.i(tag, String.format(" calcPrinter: total:%s ,spent:%s ,remain:%s ,av_no:%s ", a, b, c, d));
    }
    public static calcData getCalcSum(ArrayList<budgetData> thecalcDataArray)
    {
        calcData c;
        Double summax=0.0,sumspent=0.0,sumremain=0.0,sumav_no=0.0;
        for (budgetData r : thecalcDataArray) {
            summax+=   r.getThecalcData().getMax();
            sumspent+= r.getThecalcData().getSpent();
            sumremain+=r.getThecalcData().getRemain();
            sumav_no+= r.getThecalcData().getAv_no();
        }
        c=new calcData(summax,sumspent,sumremain,sumav_no);
        c.calcPrinter(TAG,"sum");
        return c ;
    }
}
