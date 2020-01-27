package com.mh.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mh.myapp.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Admin_activity extends AppCompatActivity {

    Button subject,year,section,course;
    String created,created_by="admin";
    DatabaseHelper dh;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_activity);
        subject=(Button)findViewById(R.id.add_subject);
        year=(Button)findViewById(R.id.add_year);
        section=(Button)findViewById(R.id.add_section);
        course=(Button)findViewById(R.id.add_course);
        back=(ImageButton)findViewById(R.id.back);

        dh=new DatabaseHelper(this);

        subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_activity.this);
                    final View mview = getLayoutInflater().inflate(R.layout.add_subject, null);
                    final EditText code=(EditText)mview.findViewById(R.id.subject_code);
                    final EditText name=(EditText)mview.findViewById(R.id.subject_name);
                    final ImageButton save=(ImageButton)mview.findViewById(R.id.save);

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    created = format1.format(Calendar.getInstance().getTime());

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                if(TextUtils.isEmpty(code.getText().toString())){
                                    Toast.makeText(Admin_activity.this,"Subject can't be saved",Toast.LENGTH_LONG).show();
                                }else if(TextUtils.isEmpty(name.getText().toString())){
                                    Toast.makeText(Admin_activity.this,"Subject can't be saved",Toast.LENGTH_LONG).show();
                                }else{
                                    boolean checksubject=dh.checksubject(code.getText().toString(),name.getText().toString());
                                    if(checksubject){
                                        dh.insertSubject(code.getText().toString(),name.getText().toString(),created,created_by);
                                        Toast.makeText(Admin_activity.this,"Added Successfully",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(Admin_activity.this,"Subject Already Exist",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setCancelable(true);


                    builder.setView(mview);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_activity.this);
                    final View mview = getLayoutInflater().inflate(R.layout.add_year, null);
                    final EditText year=(EditText)mview.findViewById(R.id.year);
                    final ImageButton save=(ImageButton)mview.findViewById(R.id.save);

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    created = format1.format(Calendar.getInstance().getTime());

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                if(TextUtils.isEmpty(year.getText().toString())){
                                    Toast.makeText(Admin_activity.this,"Year level can't be saved",Toast.LENGTH_LONG).show();
                                }else{
                                    boolean checkyear=dh.checkyear(year.getText().toString());
                                    if(checkyear){
                                        dh.insertYear(year.getText().toString(),created,created_by);
                                        Toast.makeText(Admin_activity.this,"Added Successfully",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(Admin_activity.this,"Year level Already Exist",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setCancelable(true);


                    builder.setView(mview);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

       section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_activity.this);
                    final View mview = getLayoutInflater().inflate(R.layout.add_section, null);
                    final EditText section=(EditText)mview.findViewById(R.id.section);
                    final ImageButton save=(ImageButton)mview.findViewById(R.id.save);

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    created = format1.format(Calendar.getInstance().getTime());

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                if(TextUtils.isEmpty(section.getText().toString())){
                                    Toast.makeText(Admin_activity.this,"Section can't be saved",Toast.LENGTH_LONG).show();
                                }else{
                                    boolean checksection=dh.checksection(section.getText().toString());
                                    if(checksection){
                                        dh.insertSection(section.getText().toString(),created,created_by);
                                        Toast.makeText(Admin_activity.this,"Added Successfully",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(Admin_activity.this,"Section Already Exist",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setCancelable(true);


                    builder.setView(mview);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_activity.this);
                    final View mview = getLayoutInflater().inflate(R.layout.add_course, null);
                    final EditText code=(EditText)mview.findViewById(R.id.course_code);
                    final EditText name=(EditText)mview.findViewById(R.id.course_name);
                    final ImageButton save=(ImageButton)mview.findViewById(R.id.save);

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    created = format1.format(Calendar.getInstance().getTime());

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                if(TextUtils.isEmpty(code.getText().toString())){
                                    Toast.makeText(Admin_activity.this,"Course can't be saved",Toast.LENGTH_LONG).show();
                                }else if(TextUtils.isEmpty(name.getText().toString())){
                                    Toast.makeText(Admin_activity.this,"Course can't be saved",Toast.LENGTH_LONG).show();
                                }else{
                                    boolean checkcourse=dh.checkcourse(code.getText().toString(),name.getText().toString());
                                    if(checkcourse){
                                        dh.insertCourse(code.getText().toString(),name.getText().toString(),created,created_by);
                                        Toast.makeText(Admin_activity.this,"Added Successfully",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(Admin_activity.this,"Course Already Exist",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setCancelable(true);


                    builder.setView(mview);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Admin_activity.this,Admin_home.class);
                startActivity(in);
            }
        });





    }
}
