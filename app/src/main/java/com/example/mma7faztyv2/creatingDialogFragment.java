package com.example.mma7faztyv2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class creatingDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG="diaglogFragment";
    private editFRagListener editFRagListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtNumber;
    int budgetno;
    String theTitle;
    Double theNum;
    private EditText edTitle;
    private TextView txtComment;
    private EditText ednum;
    private Button btSave;
    private Button btCancel;

    // TODO: Rename and change types of parameters

    public creatingDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

      * @return A new instance of fragment diaglogFragment.
     */
    public static creatingDialogFragment creatingNewInstance(int num) {
        creatingDialogFragment fragment = new creatingDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, num);
        Log.i(TAG, "newInstance: "+num);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        editFRagListener= (creatingDialogFragment.editFRagListener) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.ceating_fragment_dialog, container, false);
        txtNumber = v.findViewById(R.id.txt_title);
        edTitle =    v.findViewById(R.id. ed_title);
        txtComment = v.findViewById(R.id.txt_sbudget);
        ednum =      v.findViewById(R.id.ed_sbudget);
        btSave =    v.findViewById(R.id.bt_save);
        btCancel =  v.findViewById(R.id.bt_cancel);
        budgetno= MainActivity.caller_ma7fazaDatabase.countDefaultTitle();
       if (budgetno==0)
           edTitle.setText(String.format(" new budget "));
       else
           edTitle.setText(String.format(" new budget %s",budgetno));
        ednum.setText("0");
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 editFRagListener.onCreatingClickListener(edTitle.getText().toString(), Double.parseDouble(ednum.getText().toString()));
                 dismiss();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }
public interface editFRagListener{
    void onCreatingClickListener(String title, double num );
}

}
