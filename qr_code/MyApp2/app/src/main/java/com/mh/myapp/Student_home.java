package com.mh.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mh.myapp.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Student_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    DatabaseHelper dh;
    TextView myname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        myname=(TextView)findViewById(R.id.name);
        String name="";

        dh=new DatabaseHelper(Student_home.this);

        try{
            int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
            if(ident==0){
                name = dh.getStudent_name1(getIntent().getStringExtra("mycode"));
            }else if(ident==1){
                name = dh.getStudent_name2(getIntent().getStringExtra("student_num"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        myname.setText("Hi! "+name);
        /*select_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Student_home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.course_year_section, null);
                    final Spinner section=(Spinner)mview.findViewById(R.id.choose_section);
                    final ImageButton save=(ImageButton)mview.findViewById(R.id.save);


                    //temp_name.setSelection(getIndex(temp_name,template_name));
                    //int temp_id=dh.getTemp_id(temp_name.getSelectedItem().toString());

                    final ArrayList<String> list1=dh.getYear();

                    final ArrayList<String> list2=dh.getSection();
                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(Student_home.this,R.layout.spinner_layout,R.id.txt,list2);
                    section.setAdapter(adapter2);

                    final int section_id=dh.getSection_id(section.getSelectedItem().toString());

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                dh.UpdateStudent(section_id,getIntent().getStringExtra("mycode"));
                                Toast.makeText(Student_home.this,"Success",Toast.LENGTH_LONG).show();

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

        select_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = dh.getStudent_id(getIntent().getStringExtra("mycode"));

                    Intent in = new Intent(Student_home.this, Select_subject.class);
                    in.putExtra("user_id", String.valueOf(id));
                    in.putExtra("my_code", getIntent().getStringExtra("mycode"));
                    in.putExtra("position", String.valueOf(0));
                    startActivity(in);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });

        select_acct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Student_home.this,Update_student.class);
                in.putExtra("mycode",getIntent().getStringExtra("mycode"));
                startActivity(in);
            }
        });*/


    }
    @Override
    public void onBackPressed() {
        Intent in =new Intent(Student_home.this,Home.class);
        startActivity(in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();

            if (id == R.id.subject) {
                try {

                    int id1=0;
                    int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
                    if(ident==0){
                        id1 = dh.getStudent_id(getIntent().getStringExtra("mycode"));
                    }else if(ident==1){
                        id1 = dh.getStudent_id1(getIntent().getStringExtra("student_num"));
                    }
                    Intent in = new Intent(Student_home.this, Select_subject.class);
                    in.putExtra("user_id", String.valueOf(id1));
                    if(ident==0){
                        in.putExtra("mycode",getIntent().getStringExtra("mycode"));
                    }else if(ident==1){
                        in.putExtra("student_num",getIntent().getStringExtra("student_num"));
                    }
                    in.putExtra("ident",getIntent().getStringExtra("ident"));
                    in.putExtra("position", String.valueOf(0));
                    startActivity(in);
                }catch (Exception e){
                    e.printStackTrace();
                }

            } else if (id == R.id.section) {

                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Student_home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.course_year_section, null);
                    final Spinner section=(Spinner)mview.findViewById(R.id.choose_section);
                    final ImageButton save=(ImageButton)mview.findViewById(R.id.save);

                    dh=new DatabaseHelper(this);

                    //temp_name.setSelection(getIndex(temp_name,template_name));
                    //int temp_id=dh.getTemp_id(temp_name.getSelectedItem().toString());


                    final ArrayList<String> list2=dh.getSection();
                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(Student_home.this,R.layout.spinner_layout,R.id.txt,list2);
                    section.setAdapter(adapter2);

                    final int section_id=dh.getSection_id(section.getSelectedItem().toString());
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{

                                int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
                                if(ident==0){
                                    dh.UpdateStudent(section_id,getIntent().getStringExtra("mycode"));
                                }else if(ident==1){
                                    dh.UpdateStudent1(section_id,getIntent().getStringExtra("student_num"));
                                }


                                Toast.makeText(Student_home.this,"Success",Toast.LENGTH_LONG).show();

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

            } else if (id == R.id.my_acct) {
                int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
                Intent in = new Intent(Student_home.this, Update_student.class);
                if(ident==0){
                    in.putExtra("mycode",getIntent().getStringExtra("mycode"));
                }else if(ident==1) {
                    in.putExtra("student_num", getIntent().getStringExtra("student_num"));
                }
                in.putExtra("ident",getIntent().getStringExtra("ident"));
                startActivity(in);


            }else if(id == R.id.logout){

            }else if(id == R.id.close){

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_student_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
