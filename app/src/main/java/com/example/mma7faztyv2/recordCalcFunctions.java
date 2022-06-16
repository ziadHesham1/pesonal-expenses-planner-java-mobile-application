/*
package com.example.mma7faztyv2;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import static com.example.mma7faztyv2.ma7fazaDatabase.addtable;
import static com.example.mma7faztyv2.ma7fazaDatabase.subtable;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class recordCalcFunctions {
    private static final String TAG = "recordCalcFunctions";
    public ma7fazaDatabase caller_ma7fazaDatabase =MainActivity.caller_ma7fazaDatabase;
    public double np_selected, max, spent, remain, av_no;
    public String comment;
    EditText             comm;
    Context context ;

    TextView max_value;
    TextView spent_value;
    TextView remain_value;

    private ArrayList<recordsData> addList = recordsActivity.addRecordArray;
    private ArrayList<recordsData> subList = recordsActivity.subRecordArray;
    calcData lastCalacVar;
    public static listRecordAdapter adapter_add;
    public static listRecordAdapter adapter_sub;
    ListView addListView;
    ListView subListView;
    String theTitlehere ;

    public recordCalcFunctions(Context context , TextView max_value, TextView spent_value
            , TextView remain_value, ListView addListView, ListView subListView,EditText comm, String theTitlehere) {
        this.comm = comm;
        this.context = context;
        this.max_value = max_value;
        this.spent_value = spent_value;
        this.remain_value = remain_value;
        this.addListView = addListView;
        this.subListView = subListView;
        this.theTitlehere = theTitlehere;
    }

    public calcData add(double np_selected) {
    calcDataPrinter();
    Log.i(TAG, String.format(" calcFunctions calcData add:np_selected %s %s ,%s ,%s ,%s  "
            ,np_selected,max,spent,remain,av_no));
        calcData addArray = null;
        int check1 = 0;
        try {
            if (np_selected > 0) {
                comment = comm.getText().toString();
                max = max + np_selected;
                av_no = av_no + np_selected;
                remain = max - spent;
                addArray=new calcData(max,spent,remain,av_no);
                max_value.setText(String.format("%s", max));
                remain_value.setText(String.format("%s", remain));
                spent_value.setText(String.format("%s", spent));
                //dol hi3mlo ellist
                String c;
                if (TextUtils.isEmpty(comm.getText().toString())) c="";
                else c=comment;
                recordsData d=new recordsData(np_selected, c);
                caller_ma7fazaDatabase.addRecord(theTitlehere,d, addtable);
                caller_ma7fazaDatabase.updatecalc(theTitlehere,addArray);
                tolistAdd();
                //----------------- ------------
                comm.setText("");

            } else {
                check1 = 1;
            }
        } catch (NumberFormatException e) {
            check1 = 1;
        }
        if (check1 == 0)
            return addArray;
        else {
            return null;
        }

    }

    public calcData sub(double np_selected) {
        calcDataPrinter();
        int check2 = 0;
        calcData subCalcData = null;
        try {
            String comment;
            comment = comm.getText().toString();
            if (av_no >= np_selected) {
                if (np_selected > 0) {
                    if (av_no >= np_selected && spent <= max && (spent + remain) >= max) {
                        spent = spent + np_selected;
                        remain = av_no - np_selected;
                        av_no = av_no - np_selected;
                        subCalcData=new calcData(max,spent,remain,av_no);

                        //dol hi3mlo ellist
                        String c;
                        if (TextUtils.isEmpty(comm.getText().toString())) c="";
                        else c=comment;
                        recordsData d=new recordsData(np_selected, c);
                        caller_ma7fazaDatabase.addRecord(theTitlehere,d, subtable);
                        caller_ma7fazaDatabase.updatecalc(theTitlehere,subCalcData);
                        tolistSub();
                        //-----------------------------
                    } else {
                        commonFN.toastMessage("you only1 have " + remain + av_no);
                        subCalcData=new calcData(max,spent,remain,av_no);
                        check2 = 1;
                    }
                } else {

                    commonFN.toastMessage("enter a real number");
                    check2 = 1;
                }
            } else {
                subCalcData=new calcData(max,spent,remain,av_no);
            }
            comm.setText("");
        } catch (NumberFormatException e) {
            commonFN.toastMessage("please enter a number");
        }
        if (check2 == 0)
            return subCalcData;
        else {
            commonFN.toastMessage("sub null");
            return null;
        }

    }
    public void calcDataPrinter() {
        calcData c=caller_ma7fazaDatabase.getcalc(theTitlehere);
        if (c!=null) {

            max_value.setText(  String.valueOf(c.getMax()));
            spent_value.setText(  String.valueOf(c.getSpent()));
            remain_value.setText(  String.valueOf(c.getRemain()));
        }
    }//byzhr el sum fel text views
    public void tolistAdd() {
        addList = caller_ma7fazaDatabase.getRecordWithTitle(theTitlehere,addtable);
        adapter_add = new listRecordAdapter(context, R.layout.layout, addList);
        addListView.setAdapter(adapter_add);
    }
    public void tolistSub() {
        subList = caller_ma7fazaDatabase.getRecordWithTitle(theTitlehere,subtable);
        adapter_sub = new listRecordAdapter(context, R.layout.layout_red, subList);
        subListView.setAdapter(adapter_sub);
    }
public void tvTest()
{
    max_value.setText("maxass");
    spent_value.setText("spentass");
    remain_value.setText("remainass");
}
}
*/
