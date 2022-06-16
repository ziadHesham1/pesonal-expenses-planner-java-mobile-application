package com.example.mma7faztyv2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class budgetAdapter extends RecyclerView.Adapter<budgetAdapter.myHolder> {
    ArrayList<budgetData> mydata;
     LayoutInflater       inflater;
    final static String ideditRequestCode ="titleAdapter";
    private static final String TAG = "myRecyclerAdapter";
Context theContext;
recyclerItemDeleted r;
    public budgetAdapter(Context context, ArrayList<budgetData> cmydata) {
        this.mydata = cmydata;
        this.inflater = LayoutInflater.from(context);
        this.theContext=context;
    }
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,null,false);
        myHolder holder=new myHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        budgetData d=mydata.get(position);
        holder.nTitle.setText(d.getTitle());
        holder.nDate.setText(d.getDate());
        holder.nTime.setText(d.getTime());
        /*holder.nTotal.setText((String.valueOf(d.getMax())));
        holder.nSpent.setText((String.valueOf(d.getSpent())));
        holder.nRemain.setText((String.valueOf(d.getRemain())));*/
       holder.tvStatus.setText(String.format("%s of %s",d.getRemain(),d.getMax()));

        Log.i(TAG, "onBindViewHolder: "+d.getTitle());

    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{
        TextView nTitle,nDate,nTime;
        TextView tvStatus;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            /*nTotal=itemView.findViewById(R.id.c_maxValue);
            nSpent=itemView.findViewById(R.id.c_spentValue);
            nRemain=itemView.findViewById(R.id.c_remainVlaue);*/
            nTitle  = itemView.findViewById(R.id.nTitle);
            nDate   = itemView.findViewById(R.id.date);
            nTime   = itemView.findViewById(R.id.time);
            tvStatus=itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), recordsActivity.class);
                    i.putExtra("2",mydata.get(getAdapterPosition()).getTitle());
                    Log.i(TAG, String.format("onClick the item number:%d clicked ##################################",
                            mydata.get(getAdapterPosition()).getId()));
                    v.getContext().startActivity(i);

                    Toast.makeText(theContext,String.format(" this is long: %s",mydata.get(getAdapterPosition()).getMax()), Toast.LENGTH_LONG).show();

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MainActivity.caller_ma7fazaDatabase.delbudgetbytitle(mydata.get(getAdapterPosition()).getTitle());
                    r= (recyclerItemDeleted) theContext;
                    r.recyclerDelete(true);
                    Toast.makeText(theContext,String.format(" this is long: %s",mydata.get(getAdapterPosition()).getTitle()), Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }

        }

        public interface recyclerItemDeleted{
        void recyclerDelete(boolean b);
        }
    }

