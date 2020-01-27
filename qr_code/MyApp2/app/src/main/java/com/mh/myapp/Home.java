package com.mh.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DatabaseHelper dh;

    Button scan,to_add,scan_admin,login_as_admin;
    TextView greeting;
    String created,created_by;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            scan = (Button) findViewById(R.id.scan);
            greeting = (TextView) findViewById(R.id.section);

            dh = new DatabaseHelper(this);

            try {
                String[] array = dh.getIds();
                int section_id = Integer.parseInt(array[0]);
                int subject_id = Integer.parseInt(array[1]);

                if (section_id != 0 && subject_id != 0) {
                    String section_name = dh.getSection_name(section_id);
                    String subject_name = dh.getSubject_name(subject_id);

                    greeting.setText(subject_name + " - " + section_name);
                } else {
                    greeting.setText("WELCOME!!");
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            final IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setCameraId(0);

            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentIntegrator.initiateScan();
                }
            });

        /*to_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        login_as_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.admin_login, null);
                    final EditText pass=(EditText)mview.findViewById(R.id.admin_pass);
                    final Button login=(Button)mview.findViewById(R.id.admin_log);

                    dh=new DatabaseHelper(Home.this);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{

                                        boolean check_admin=dh.check_admin(pass.getText().toString());
                                        if(check_admin){

                                            Intent in=new Intent(Home.this,Admin_home.class);
                                            startActivity(in);
                                        }else{
                                            Toast.makeText(Home.this, "Wrong password", Toast.LENGTH_LONG).show();
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
        });*/
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        dh=new DatabaseHelper(this);
        if(result!=null){
            try {
                if (result.getContents() == null) {
                    Toast.makeText(Home.this, "No result found", Toast.LENGTH_LONG).show();
                } else {
                    boolean check_admin_qr=dh.getadmin_qr(result.getContents());
                    if(check_admin_qr){
                        Intent in =new Intent(Home.this,Admin_home.class);
                        startActivity(in);
                    }else{
                        String[] array=dh.getIds();
                        if(array[2].equals("N")){
                            boolean checkmy_qr=dh.checkmy_qr(result.getContents());
                            if(checkmy_qr){
                                Intent in =new Intent(Home.this,Student_home.class);
                                in.putExtra("mycode",result.getContents());
                                in.putExtra("ident",String.valueOf(0));
                                startActivity(in);
                            }else{
                                Toast.makeText(Home.this, "No result found", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            boolean checkuser=dh.checkbelong(Integer.parseInt(array[0]),Integer.parseInt(array[1]));
                            if(checkuser){
                                int section_id=Integer.parseInt(array[0]);
                                int subject_id=Integer.parseInt(array[1]);
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                created = format1.format(Calendar.getInstance().getTime());
                                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                                String mydate = format.format(Calendar.getInstance().getTime());
                                int student_id=dh.getStudent_id(result.getContents());
                                String[] array2=dh.getMy_info(result.getContents());
                                String name=array2[2]+" "+array2[0];
                                String status=dh.getStatus();
                                dh.insertIntoTime_log(student_id,section_id,subject_id,mydate,status,created,name);
                                String thanks="Thank you "+array2[0];
                                Toast.makeText(Home.this, thanks, Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(Home.this, "No result found", Toast.LENGTH_LONG).show();
                            }
                        }
                    }




                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onBackPressed() {
        Intent in =new Intent(Home.this,Add_student.class);
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

            if (id == R.id.manual_user) {

                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.signin_user, null);
                    final EditText pass=(EditText)mview.findViewById(R.id.user_pass);
                    final EditText student_num=(EditText)mview.findViewById(R.id.student_num);
                    final Button login=(Button)mview.findViewById(R.id.user_log);

                    dh=new DatabaseHelper(Home.this);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {

                                boolean check_user_pass = dh.check_user_pass(pass.getText().toString(), student_num.getText().toString());

                                    if (check_user_pass) {
                                        boolean check_attendace_mode = dh.check_attendance_mode();
                                        if (check_attendace_mode) {
                                            String[] array = dh.getIds();

                                            boolean checkuser = dh.checkbelong(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
                                            if (checkuser) {
                                                int section_id = Integer.parseInt(array[0]);
                                                int subject_id = Integer.parseInt(array[1]);
                                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                created = format1.format(Calendar.getInstance().getTime());
                                                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                                                String mydate = format.format(Calendar.getInstance().getTime());
                                                int student_id = dh.getStudent_id1(student_num.getText().toString());
                                                String[] array2 = dh.getMy_info1(student_num.getText().toString());
                                                String name = array2[2] + " " + array2[0];
                                                String status = dh.getStatus();
                                                dh.insertIntoTime_log(student_id, section_id, subject_id, mydate, status, created, name);
                                                String thanks = "Thank you " + array2[0];
                                                Toast.makeText(Home.this, thanks, Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(Home.this, "No result found", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            try {
                                                Intent in = new Intent(Home.this, Student_home.class);
                                                in.putExtra("student_num", student_num.getText().toString());
                                                in.putExtra("ident", String.valueOf(1));
                                                startActivity(in);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            //Toast.makeText(Home.this, "Wrong password", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }catch(Exception e){
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

            } else if (id == R.id.manual_admin) {

                try {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    final View mview = getLayoutInflater().inflate(R.layout.admin_login, null);
                    final EditText pass=(EditText)mview.findViewById(R.id.admin_pass);
                    final Button login=(Button)mview.findViewById(R.id.admin_log);

                    dh=new DatabaseHelper(Home.this);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{

                                boolean check_admin=dh.check_admin(pass.getText().toString());
                                if(check_admin){

                                    Intent in=new Intent(Home.this,Admin_home.class);
                                    startActivity(in);
                                }else{
                                    Toast.makeText(Home.this, "Wrong password", Toast.LENGTH_LONG).show();
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

            } else if (id == R.id.add_student) {

                Intent in =new Intent(Home.this,Add_student.class);
                startActivity(in);

            }else if(id == R.id.close){

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_home_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
