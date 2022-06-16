package com.example.mma7faztyv2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "PlaceholderFragment";
    RecyclerView          rv;
    ArrayList<budgetData> recyclerArray =new ArrayList<>();
    ma7fazaDatabase       caller_ma7fazaDatabase = MainActivity.caller_ma7fazaDatabase;
    String thePeriod;
     sendTheSum     sendThesum;
    public static PlaceholderFragment newInstance(String index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendThesum = (sendTheSum) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null) {
            thePeriod =getArguments().getString(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rv);
        try {
            if (recyclerArray!=null&&thePeriod!=null) recyclerArray=caller_ma7fazaDatabase.getThisDateArray(thePeriod);
            displayList();
            sendThesum.sendingThesum(budgetData.getCalcSum(recyclerArray));
        } catch (Exception e) {
            Log.i(TAG, "onViewCreated: main activity onstart error");
        }
    }

        private void displayList() {
        budgetAdapter adapter;
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        if (recyclerArray!=null){
            adapter = new budgetAdapter(getContext(), recyclerArray);
            rv.setAdapter(adapter);
        }
    }//bytl3 el recycler 3la elshasha
    public interface sendTheSum
    {
         void sendingThesum(calcData c);
    }
}