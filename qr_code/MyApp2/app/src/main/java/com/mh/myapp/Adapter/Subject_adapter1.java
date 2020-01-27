package com.mh.myapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.myapp.Database.DatabaseHelper;
import com.mh.myapp.Module.Subject_module;
import com.mh.myapp.Module.Subject_module1;
import com.mh.myapp.R;

import java.util.List;

public class Subject_adapter1 extends ArrayAdapter<Subject_module1> {
    private Context mContext;
    int mResource;
    DatabaseHelper dh;

    public Subject_adapter1(Context context, int resource, List<Subject_module1> objects) {
        super(context, resource,objects);
        mContext = context;
        mResource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{

            int id=getItem(position).getSubject_id();
            String code=getItem(position).getCode();
            String name=getItem(position).getName();

            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(mResource,parent,false);

            TextView subject_code=(TextView) convertView.findViewById(R.id.subject_code);
            TextView subject_name=(TextView)convertView.findViewById(R.id.subject_name);

            subject_name.setText(name);
            subject_code.setText(code);



            if(subject_name.getText().toString().equals("No Subject Yet")){
                subject_code.setEnabled(false);
                subject_name.setEnabled(false);

            }else{
                subject_code.setEnabled(true);
                subject_name.setEnabled(true);
            }





            /*if(choose1.isChecked()){
                getItem(position).setChoosed("Y");
            }else{
                getItem(position).setChoosed("N");
            }*/
//#5AD55F//#DA3838





        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }
}

