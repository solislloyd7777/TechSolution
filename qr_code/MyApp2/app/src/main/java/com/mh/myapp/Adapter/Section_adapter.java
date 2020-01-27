package com.mh.myapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mh.myapp.Database.DatabaseHelper;
import com.mh.myapp.Module.Section_module;
import com.mh.myapp.R;

import java.util.List;

public class Section_adapter extends ArrayAdapter<Section_module> {
private Context mContext;
        int mResource;
        DatabaseHelper dh;

public Section_adapter(Context context, int resource, List<Section_module> objects) {
        super(context, resource,objects);
        mContext = context;
        mResource=resource;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        try{

        int id=getItem(position).getSection_id();
        String name=getItem(position).getSection();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        TextView name1=(TextView)convertView.findViewById(R.id.section_content);
        name1.setText(name);

        }catch (Exception e){
        e.printStackTrace();
        }
        return convertView;
        }
        }
