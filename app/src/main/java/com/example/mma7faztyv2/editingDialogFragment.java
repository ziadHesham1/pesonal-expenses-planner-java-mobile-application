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
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editingDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editingDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG="diaglogFragment";
    private editFRagListener editFRagListener;
    private delFRagListener delFRagListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtNumber;
    private EditText edNumber;
    private TextView txtComment;
    private EditText edComment;
    private Button btDelete;
    private Button btSave;
    private Button btCancel;

    // TODO: Rename and change types of parameters
    private double recievedNum;
    private String recievedcomm;

    public editingDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

      * @return A new instance of fragment diaglogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static editingDialogFragment editNewInstance(double num, String comm) {
        editingDialogFragment fragment = new editingDialogFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, num);
        Log.i(TAG, "newInstance: "+num);
        args.putString(ARG_PARAM2, comm);
        Log.i(TAG, "newInstance: "+comm );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recievedNum = getArguments().getDouble(ARG_PARAM1);
            recievedcomm = getArguments().getString(ARG_PARAM2);
        }
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        editFRagListener= (editingDialogFragment.editFRagListener) context;
        delFRagListener= (editingDialogFragment.delFRagListener) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.editing_fragment_dialog, container, false);
        txtNumber = (TextView)v.findViewById(R.id.txt_number);
        edNumber = (EditText) v.findViewById(R.id.ed_number);
        txtComment = (TextView) v.findViewById(R.id.txt_comment);
        edComment = (EditText) v.findViewById(R.id.ed_comment);
        btDelete = (Button) v.findViewById(R.id.bt_delete);
        btSave = (Button) v.findViewById(R.id.bt_save);
        btCancel = (Button) v.findViewById(R.id.bt_cancel);
        edNumber.setText(recievedNum+"");
        edComment.setText(recievedcomm);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delFRagListener.onDelClickListener();
                dismiss();

            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 editFRagListener.onEditClickListener(Double.parseDouble(edNumber.getText().toString()),edComment.getText().toString());
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
    void onEditClickListener(double num, String comm);
}
    public interface delFRagListener{
        void onDelClickListener();
    }

}
