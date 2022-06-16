package com.example.mma7faztyv2;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;


public class typrfrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG        = "typrfrag" ;
    AutoCompleteTextView inputNumbertv, inputCommenttv;
    private double sendingnum;
    String sendingcomm;
    public static String titlereceiveCode="titlefrag";
    public String theTitleHere;

    private OnFragmentClickListener_type listener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public typrfrag() {
        // Required empty public constructor
    }

    public static typrfrag newtypeInstance(String theTitle) {
        typrfrag fragment = new typrfrag();
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
        View view = inflater.inflate(R.layout.type_frag, container, false);
        inputNumbertv = view.findViewById(R.id.editNumber);
        inputCommenttv = view.findViewById(R.id.input_comment);

        inputNumbertv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.i(TAG, "beforeTextChanged: beforeTextChanged"+start+"--"+count+"--"+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.i(TAG, "beforeTextChanged: onTextChanged"+start+"--"+before+"--"+count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                sendingnum = get_editnumber();
                Log.i(TAG, "onCreateView: " + sendingnum);
                listener.type_number_listener(sendingnum);
            }
        });
        inputCommenttv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.i(TAG, "beforeTextChanged: beforeTextChanged"+start+"--"+count+"--"+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.i(TAG, "beforeTextChanged: onTextChanged"+start+"--"+before+"--"+count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                sendingcomm = inputCommenttv.getText().toString();
                Log.i(TAG, "onCreateView: " + sendingcomm);
                listener.type_comment_listener(sendingcomm);
            }
        });
        return view;
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener= (OnFragmentClickListener_type) context;
    }

    public double get_editnumber() //bta5od
    {
        double no=0;
        try {
            no= Double.parseDouble(inputNumbertv.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return no;
    }
    public  void tospinner()
    {
        ma7fazaDatabase caller_ma7fazaDatabase=new ma7fazaDatabase(getContext());
        ArrayList<recordsData> listOne = caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.add_table);
        ArrayList<recordsData> listTwo = caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.sub_table);
        listOne.addAll(listTwo);

        ArrayList<String> numList = new ArrayList<>();
        ArrayList<String> comList = new ArrayList<>();
        numList.add("choose from old operations: ");
        Double num;
        for (recordsData i :listOne)
        {
            num=i.getNumber();
            numList.add(num.toString());
            comList.add(i.getComment());
        }
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(numList);
        ArrayList<String> numberListNoDub = new ArrayList<>(hashSet);
        ArrayList<String> commentListNoDub = new ArrayList<>(hashSet);
        inputNumbertv.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, numberListNoDub));
        inputCommenttv.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, commentListNoDub));
    }
    public interface OnFragmentClickListener_type
    {
        void type_number_listener(double number);
        void type_comment_listener(String comment);
    }
}
