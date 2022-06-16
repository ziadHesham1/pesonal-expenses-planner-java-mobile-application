package com.example.mma7faztyv2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link prgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class prgFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String s_prgMax      = "prgMax";
    private static final String s_prgProgress = "prgProgress";
    //private static final String s_prgSecProgress = "prgSecProgress";
    private static final String TAG           = "prgFragment";
    ProgressBar prg;
    // TODO: Rename and change types of parameters
    Integer     prgMax;
    Integer     prgProgress;
   // Integer prgSecProgress;

    public prgFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment prgFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static prgFragment newInstance(Integer prgMax, Integer prgProgress) {
        prgFragment fragment = new prgFragment();
        Bundle args = new Bundle();
        args.putInt(s_prgMax, prgMax);
        args.putInt(s_prgProgress, prgProgress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prgMax = getArguments().getInt(s_prgMax);
            prgProgress = getArguments().getInt(s_prgProgress);
           // prgSecProgress = getArguments().getInt(s_prgSecProgress);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_prg, container, false);
        prg = v.findViewById(R.id.prg);
        Log.i(TAG, String.format("onCreateView: prgmax = %d, prgProg = %d", prgMax,prgProgress));
        if (prgMax==0)
            {prg.setMax((int) 1);
        prg.setSecondaryProgress((int)1);}
        else
        {
            prg.setMax((int) prgMax);
            prg.setProgress((int)prgProgress);
            prg.setSecondaryProgress((int)prgMax);
        }
        return v;
    }
}