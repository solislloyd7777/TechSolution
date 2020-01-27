package com.mh.myapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mh.myapp.Module.Mystudent_list;
import com.mh.myapp.R;

import java.util.List;

public class Mystudent_list_adapter extends ArrayAdapter<Mystudent_list> {


    private static final String TAG="PersonListAdapter";
    private Context mContext;
    int mResource;

    public Mystudent_list_adapter(Context context, int resource, List<Mystudent_list> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            String fname=getItem(position).getFname();
            String mname=getItem(position).getMname();
            String lname=getItem(position).getLname();
            String student_num=getItem(position).getStudent_num();
            String password=getItem(position).getPassword();
            String code=getItem(position).getCode();
            String middle=mname.substring(0,1);
            String fullname=lname+", "+fname+" "+middle+".";
            int id=getItem(position).getId();

           Mystudent_list mylist=new Mystudent_list(fname,mname,lname,student_num,password,code,id);
            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(mResource,parent,false);

            TextView name=(TextView) convertView.findViewById(R.id.name);
            name.setText(fullname);

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }
}
