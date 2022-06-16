package com.example.mma7faztyv2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class pickFragment extends Fragment {
     private NumberPicker numberpicker1,numberpicker2,numberpicker3,numberpicker4,numberpicker5;
     Spinner spinner;
    ArrayAdapter<String> adapter;
    public String theTitleHere;
    private TextView np_textView;
    private int      np_selected1=0,np_selected2=0,np_selected3=0,np_selected4=0,np_selected5=0;
    private double theNumber,num_spinner;
    public static String titlereceiveCode="titlefrag";
    ArrayList<Double> a =new ArrayList<>();
    private static final String                       TAG ="pickFragment";
    private              OnFragmentClickListener_pick listener;
    private              double                       np_selected;
    public pickFragment() {
        // Required empty  public constructor
    }

    public static pickFragment newpickInstance(String theTitle) {
        pickFragment fragment = new pickFragment();
        Bundle args = new Bundle();
        args.putString(titlereceiveCode, theTitle);
        Log.i(TAG, "newInstance: "+theTitle );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            theTitleHere = getArguments().getString(titlereceiveCode);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.pick_frag, container, false);
        //numberrpicker part------------------------------
        numberpicker1 = view.findViewById(R.id.numberPicker1);
        numberpicker2 = view.findViewById(R.id.numberPicker2);
        numberpicker3 = view.findViewById(R.id.numberPicker3);
        numberpicker4 = view.findViewById(R.id.numberPicker4);
        numberpicker5 = view.findViewById(R.id.numberPicker5);
        np_textView=view.findViewById(R.id.np_textView);
        spinner = (Spinner) view.findViewById(R.id.numspinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num_spinner = (Double) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        setRange(numberpicker1);
        setRange(numberpicker2);
        setRange(numberpicker3);
        setRange(numberpicker4);
        setRange(numberpicker5);
        tospinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0)
                {
                    num_spinner = Double.parseDouble( parent.getItemAtPosition(position).toString());
                    listener.onFragmentIntereaction_pick(num_spinner);
                    np_textView.setText(num_spinner+" EG");

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        printNumber();
        Log.i(TAG, "onCreateView: "+np_selected);
        listener.onFragmentIntereaction_pick(theNumber);

        return view;
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener= (OnFragmentClickListener_pick) context;
    }
    public void setRange(NumberPicker np)
    {
        np.setMinValue(0);
        np.setMaxValue(10);
    }
    public void printNumber()
    {
        numberpicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                np_selected=getNumber();
            }
        });
        numberpicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                np_selected=getNumber();
            }
        });
        numberpicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                np_selected=getNumber();

            }
        });
        numberpicker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                np_selected=getNumber();
            }
        });
        numberpicker5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                np_selected=getNumber();
            }
        });

    }
    public double getNumber()
    {
        np_selected1=numberpicker1.getValue();
        np_selected2=numberpicker2.getValue();
        np_selected3=numberpicker3.getValue();
        np_selected4=numberpicker4.getValue();
        np_selected5=numberpicker5.getValue();
        String s= String.format("%d%d%d%d.%d", np_selected1, np_selected2, np_selected3, np_selected4,np_selected5);
        theNumber= Double.parseDouble(s.trim());
        np_textView.setText(theNumber+" EG");

        a.add(theNumber);
        listener.onFragmentIntereaction_pick(theNumber);
        return theNumber;
    }

    public  void tospinner()
    {
        ma7fazaDatabase caller_ma7fazaDatabase=new ma7fazaDatabase(getContext());
        ArrayList<recordsData> listOne = caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.add_table);
        ArrayList<recordsData> listTwo = caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.sub_table);
        listOne.addAll(listTwo);

        ArrayList<String> numList = new ArrayList<>();
        numList.add("choose from old operations: ");
        Double num;
        for (recordsData i :listOne)
        {
            num=i.getNumber();
           numList.add(num.toString());
        }
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(numList);
        ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
        adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listWithoutDuplicates);
        spinner.setAdapter(adapter);
    }
    public interface OnFragmentClickListener_pick
    {
        void onFragmentIntereaction_pick(double s);
    }


}
