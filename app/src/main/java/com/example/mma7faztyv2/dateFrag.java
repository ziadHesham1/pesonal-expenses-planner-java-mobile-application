package com.example.mma7faztyv2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dateFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dateFrag extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match


    // TODO: Rename and change types of parameters
    private CalendarView calendarView;
    private getCalenderRead getCalenderread;

    public dateFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment dateFrag.
     */
    // TODO: Rename and change types and number of parameters
   /* public static dateFrag newInstance(String param1, String param2) {
        dateFrag fragment = new dateFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getCalenderread= (getCalenderRead) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_date, container, false);
        calendarView = v.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                getCalenderread.onPickDateListener(String.format("%s/%s/%s",i2,i1+1,i));
                dismiss();
            }
        });
        return v;
    }
    public interface getCalenderRead
    {
        void onPickDateListener(String date);
    }
}