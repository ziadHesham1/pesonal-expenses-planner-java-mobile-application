package com.example.mma7faztyv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mma7faztyv2.R;
import com.example.mma7faztyv2.recordsData;

import java.util.ArrayList;

public class listRecordAdapter extends BaseAdapter {
    Context                context;
    ArrayList<recordsData> array;
    int                    resource;

    public listRecordAdapter(Context c, int resource, ArrayList<recordsData> array)
    {
        this.context=c;
        this.array=array;
        this.resource=resource;
    }
    @Override
    public int getCount() {
          return array.size();
    }

    @Override
    public recordsData getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null)
        {
            v= LayoutInflater.from(context).inflate(resource,null,false);
        }

            TextView tv=v.findViewById(R.id.txt1);
            TextView tv2=v.findViewById(R.id.txt2);
            recordsData s=array.get(position);
            tv.setText(s.getNumber()+"");
            tv2.setText(s.getComment());

        return v;
    }

}
