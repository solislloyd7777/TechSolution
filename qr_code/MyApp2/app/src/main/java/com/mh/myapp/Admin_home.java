package com.mh.myapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mh.myapp.Database.DatabaseHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Admin_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button present,late,enable;
    DatabaseHelper dh;
    String created,created_by="admin",updated,updated_by="admin";
    TextView qrcode;
    private DatePickerDialog.OnDateSetListener mydate, mydate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        enable=(Button)findViewById(R.id.enable);
        present=(Button)findViewById(R.id.present);
        late=(Button)findViewById(R.id.late);

        dh=new DatabaseHelper(this);

        String[] array=dh.getIdentifier();

        if(array[0].equals("N")){
            enable.setText("DISABLED");
            present.setEnabled(false);
            late.setEnabled(false);

        }else{
            enable.setText("ENABLED");
            present.setEnabled(true);
            late.setEnabled(true);
        }

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(enable.getText().toString().equals("DISABLED")){
                    try {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_home.this);
                        final View mview = getLayoutInflater().inflate(R.layout.choose_section_subject_dialog, null);
                        final Spinner subject=(Spinner)mview.findViewById(R.id.choose_subject);
                        final Spinner section=(Spinner)mview.findViewById(R.id.choose_section);
                        final ImageButton done=(ImageButton)mview.findViewById(R.id.done);

                        dh=new DatabaseHelper(Admin_home.this);
                        final ArrayList<String> list=dh.getSubjectlist();
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Admin_home.this,R.layout.spinner_layout,R.id.txt,list);
                        subject.setAdapter(adapter);
                        //temp_name.setSelection(getIndex(temp_name,template_name));
                        //int temp_id=dh.getTemp_id(temp_name.getSelectedItem().toString());

                        final ArrayList<String> list1=dh.getSection();
                        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(Admin_home.this,R.layout.spinner_layout,R.id.txt,list1);
                        section.setAdapter(adapter1);

                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try{
                                    int section_id=dh.getSection_id(section.getSelectedItem().toString());
                                    int subject_id=dh.getSubject_id(subject.getSelectedItem().toString());
                                    dh.updateidentifier(section_id,subject_id);
                                    Toast.makeText(Admin_home.this,"set Enabled",Toast.LENGTH_LONG).show();

                                    Intent in =new Intent(Admin_home.this,Admin_home.class);
                                    startActivity(in);
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

                }else{
                    dh.updateidentifier3();
                    Toast.makeText(Admin_home.this,"set Disabled",Toast.LENGTH_LONG).show();
                    Intent in =new Intent(Admin_home.this,Admin_home.class);
                    startActivity(in);
                }
            }
        });
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.updateidentifier1();
                Toast.makeText(Admin_home.this,"For On-time attendance",Toast.LENGTH_LONG).show();
            }
        });

        late.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.updateidentifier2();
                Toast.makeText(Admin_home.this,"For Late attendance",Toast.LENGTH_LONG).show();
            }
        });





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

            if (id == R.id.student_list) {

                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.choose_section_subject_dialog, null);
                    final Spinner subject=(Spinner)mview.findViewById(R.id.choose_subject);
                    final Spinner section=(Spinner)mview.findViewById(R.id.choose_section);
                    final ImageButton done=(ImageButton)mview.findViewById(R.id.done);

                    dh=new DatabaseHelper(this);
                    final ArrayList<String> list=dh.getSubjectlist();
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,list);
                    subject.setAdapter(adapter);
                    //temp_name.setSelection(getIndex(temp_name,template_name));
                    //int temp_id=dh.getTemp_id(temp_name.getSelectedItem().toString());

                    final ArrayList<String> list1=dh.getSection();
                    ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,list1);
                    section.setAdapter(adapter1);

                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                Intent in =new Intent(Admin_home.this,Student_list.class);
                                in.putExtra("subject",subject.getSelectedItem().toString());
                                in.putExtra("section",section.getSelectedItem().toString());
                                in.putExtra("position",String.valueOf(0));
                                startActivity(in);

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

            } else if (id == R.id.subject) {



                Intent in =new Intent(Admin_home.this,Subject_list.class);
                in.putExtra("position",String.valueOf(0));
                startActivity(in);


            } else if (id == R.id.course_year_section) {


                Intent in=new Intent(Admin_home.this,Section.class);
                in.putExtra("position",String.valueOf(0));
                startActivity(in);


            } else if (id == R.id.send) {
                try {
                    dh=new DatabaseHelper(this);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.send_records_layout, null);
                    final Spinner subject=(Spinner)mview.findViewById(R.id.choose_subject);
                    final Spinner section=(Spinner)mview.findViewById(R.id.choose_section);
                    final TextView fdate=(TextView)mview.findViewById(R.id.date_from);
                    final TextView tdate=(TextView)mview.findViewById(R.id.date_to);
                    final ImageButton from_date=(ImageButton)mview.findViewById(R.id.fdate);
                    ImageButton to_date=(ImageButton)mview.findViewById(R.id.tdate);
                    final ImageButton done=(ImageButton)mview.findViewById(R.id.done);

                    fdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar cal = Calendar.getInstance();
                            int year = cal.get(Calendar.YEAR);
                            int month = cal.get(Calendar.MONTH);
                            int day = cal.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog dialog = new DatePickerDialog(Admin_home.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mydate, year, month, day);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    });

                    mydate = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            int month1=month+1;
                            if(month1==13){
                                month1=1;
                            }
                            if (month1 < 10 && dayOfMonth < 10) {

                                String frm_date1 = "0" + month1 + "-0" + dayOfMonth+"-"+year;
                                fdate.setText(frm_date1);
                            } else if (month1 < 10 && dayOfMonth >= 10) {
                                String frm_date1 = "0" + month1 + "-" + dayOfMonth+"-"+year;
                                fdate.setText(frm_date1);
                            } else if (month1 >= 10 && dayOfMonth < 10) {
                                String frm_date1 =month1 + "-0" + dayOfMonth+"-"+year;
                                fdate.setText(frm_date1);
                            } else if (month1 >= 10 && dayOfMonth >= 10) {
                                String frm_date1 = month1 + "-" + dayOfMonth+"-"+year;
                                fdate.setText(frm_date1);
                            }


                        }

                    };


                    tdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar cal = Calendar.getInstance();
                            int year = cal.get(Calendar.YEAR);
                            int month = cal.get(Calendar.MONTH);
                            int day = cal.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog dialog = new DatePickerDialog(Admin_home.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mydate1, year, month, day);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    });

                    mydate1 = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            int month1=month+1;
                            if(month1==13){
                                month1=1;
                            }
                            if (month1 < 10 && dayOfMonth < 10) {

                                String t_date1 = "0" + month1 + "-0" + dayOfMonth+"-"+year;
                                tdate.setText(t_date1);
                            } else if (month1 < 10 && dayOfMonth >= 10) {
                                String t_date1 = "0" + month1 + "-" + dayOfMonth+"-"+year;
                                tdate.setText(t_date1);
                            } else if (month1 >= 10 && dayOfMonth < 10) {
                                String t_date1 =month1 + "-0" + dayOfMonth+"-"+year;
                                tdate.setText(t_date1);
                            } else if (month1 >= 10 && dayOfMonth >= 10) {
                                String t_date1 = month1 + "-" + dayOfMonth+"-"+year;
                                tdate.setText(t_date1);
                            }


                        }

                    };

                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(fdate.getText().toString().compareTo(tdate.getText().toString())>=0){
                                int section_id=dh.getSection_id(section.getSelectedItem().toString());
                                int subject_id=dh.getSubject_id(subject.getSelectedItem().toString());
                                exportDatabase(section_id,subject_id,fdate.getText().toString(),tdate.getText().toString(),section.getSelectedItem().toString(),subject.getSelectedItem().toString());
                            }else{
                                Toast.makeText(Admin_home.this,"Wrong date input",Toast.LENGTH_LONG).show();
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



            } else if (id == R.id.security) {

                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.admin_pass, null);
                    final EditText old=(EditText)mview.findViewById(R.id.old_pass);
                    final EditText new_pass=(EditText)mview.findViewById(R.id.new_pass);
                    final EditText c_pass=(EditText)mview.findViewById(R.id.c_pass);
                    qrcode=(TextView) mview.findViewById(R.id.qrcode);
                    final ImageButton save1=(ImageButton)mview.findViewById(R.id.save);
                    final ImageButton scan=(ImageButton)mview.findViewById(R.id.getcode);

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    updated = format1.format(Calendar.getInstance().getTime());
                    dh=new DatabaseHelper(this);

                    final IntentIntegrator intentIntegrator=new IntentIntegrator(this);
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setCameraId(0);

                    scan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intentIntegrator.initiateScan();
                        }
                    });

                    save1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                if(TextUtils.isEmpty(old.getText().toString())){
                                    Toast.makeText(Admin_home.this,"Password can't be saved",Toast.LENGTH_LONG).show();
                                }else if(TextUtils.isEmpty(new_pass.getText().toString())){
                                    Toast.makeText(Admin_home.this,"Password can't be saved",Toast.LENGTH_LONG).show();
                                }else if(TextUtils.isEmpty(c_pass.getText().toString())){
                                    Toast.makeText(Admin_home.this,"Password can't be saved",Toast.LENGTH_LONG).show();
                                }else {
                                    if(new_pass.getText().toString().equals(c_pass.getText().toString())){
                                        boolean check_admin=dh.check_admin(old.getText().toString());
                                        if(check_admin){
                                            dh.updateAdmin(new_pass.getText().toString(),qrcode.getText().toString(),updated,updated_by);
                                            Toast.makeText(Admin_home.this, "Success", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(Admin_home.this, "Wrong password", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(Admin_home.this, "Password didn't match", Toast.LENGTH_LONG).show();
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

            } else if (id == R.id.logout) {
                Intent in=new Intent(Admin_home.this,Home.class);
                startActivity(in);


            }else if(id == R.id.close){

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_admin_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        dh=new DatabaseHelper(this);
        if(result!=null){
            try {
                if (result.getContents() == null) {
                    Toast.makeText(Admin_home.this, "No result found", Toast.LENGTH_LONG).show();
                } else {
                        qrcode.setText(result.getContents());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




    public boolean exportDatabase(int section,int subject,String fdate,String tdate,String subject1,String section1 ) {


        dh = new DatabaseHelper(this);

            String state = Environment.getExternalStorageState();
            if (!Environment.MEDIA_MOUNTED.equals(state)) {
                return false;
            } else {
                //We use the Download directory for saving our .csv file.
                File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }

                File file;
                PrintWriter printWriter =null;
                try {
                    file = new File(exportDir, section1+"_"+subject1+"("+fdate+"_"+tdate+")"+".csv");
                    file.createNewFile();
                    printWriter = new PrintWriter(new FileWriter(file));
                    SQLiteDatabase db = dh.getReadableDatabase(); //open the database for reading

                    printWriter.println("NAME,SECTION,SUBJECT,DATE,STATUS");

                    Cursor curCSV = db.rawQuery("select student_id,status,date from time_log where section_id='"+section+"' and subject_id='"+subject+"' and my_date between '"+fdate+"' and '"+tdate+"'",null);
                    curCSV.moveToFirst();
                    try {
                        if (curCSV.getCount() > 0) {
                            do {

                                String[] array=dh.getStudent(curCSV.getInt(0));
                                String fullname=array[2]+" "+array[0]+" "+array[1].substring(0,1);

                                String record = fullname+ "," + section1+ "," + subject1 + "," + curCSV.getString(2) + "," +curCSV.getString(1);
                                printWriter.println(record);

                            } while (curCSV.moveToNext());
                            Toast.makeText(Admin_home.this,"successfully compiled",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Admin_home.this,"Empty File",Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    curCSV.close();
                    db.close();
                } catch (Exception exc) {
                    exc.printStackTrace();

                    return false;
                } finally {
                    if (printWriter != null) printWriter.close();
                }

            }
           return true;
        }


    }

