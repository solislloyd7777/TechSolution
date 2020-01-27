package com.mh.myapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.myapp.Database.DatabaseHelper;
import com.mh.myapp.Module.Subject_module;
import com.mh.myapp.R;

import java.util.List;

public class Subject_adapter extends ArrayAdapter<Subject_module> {
    private Context mContext;
    int mResource;
    DatabaseHelper dh;

    public Subject_adapter(Context context, int resource, List<Subject_module> objects) {
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
            boolean status=getItem(position).isStatus();

            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(mResource,parent,false);

            TextView subject_code=(TextView) convertView.findViewById(R.id.subject_code);
            TextView subject_name=(TextView)convertView.findViewById(R.id.subject_name);
            ImageView choose=(ImageView) convertView.findViewById(R.id.choose);

            subject_name.setText(name);
            subject_code.setText(code);



            if(subject_name.getText().toString().equals("Subject List")){
                subject_code.setEnabled(false);
                subject_name.setEnabled(false);
                choose.setEnabled(false);
                choose.setBackgroundResource(R.drawable.ic_remove);

            }else{
                subject_code.setEnabled(true);
                subject_name.setEnabled(true);
                choose.setEnabled(true);
            }

            if(status){
                choose.setBackgroundResource(R.drawable.ic_done);

            }else{
                choose.setBackgroundResource(R.drawable.ic_remove);
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
