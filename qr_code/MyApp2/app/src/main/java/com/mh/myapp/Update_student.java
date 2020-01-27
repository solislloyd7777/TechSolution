package com.mh.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mh.myapp.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Update_student extends AppCompatActivity {

    EditText fname,mname,lname,student_num,password,c_pass;
    TextView qrcode;
    Button getqr,save;
    DatabaseHelper dh;
    ImageButton back;
    String updated,updated_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        fname=(EditText)findViewById(R.id.fname);
        mname=(EditText)findViewById(R.id.mname);
        lname=(EditText)findViewById(R.id.lname);
        student_num=(EditText)findViewById(R.id.student_num);
        password=(EditText)findViewById(R.id.student_password);
        c_pass=(EditText)findViewById(R.id.c_password);
        qrcode=(TextView)findViewById(R.id.qr_code);
        getqr=(Button)findViewById(R.id.get_qr);
        save=(Button)findViewById(R.id.save);
        back=(ImageButton)findViewById(R.id.back);
        String array[] = new String[7];
        dh=new DatabaseHelper(this);
        try {

            int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
            if(ident==0){
                array= dh.getMy_info(getIntent().getStringExtra("mycode"));
            }else if(ident==1){
                array= dh.getMy_info1(getIntent().getStringExtra("student_num"));
            }

            fname.setText(array[0]);
            mname.setText(array[1]);
            lname.setText(array[2]);
            student_num.setText(array[3]);
            password.setText(array[4]);
            qrcode.setText(array[5]);
            c_pass.setText(array[4]);
            final int student_id = Integer.parseInt(array[6]);

            getqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final IntentIntegrator intentIntegrator = new IntentIntegrator(Update_student.this);
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.initiateScan();
                }
            });


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (TextUtils.isEmpty(fname.getText().toString())) {

                            fname.setError("");
                        } else if (TextUtils.isEmpty(mname.getText().toString())) {
                            fname.setError("");

                        } else if (TextUtils.isEmpty(lname.getText().toString())) {
                            fname.setError("");

                        } else if (TextUtils.isEmpty(student_num.getText().toString())) {
                            fname.setError("");

                        } else if (TextUtils.isEmpty(password.getText().toString())) {
                            fname.setError("");

                        } else {
                            if (password.getText().toString().equals(c_pass.getText().toString())) {
                                dh = new DatabaseHelper(Update_student.this);
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                updated = format1.format(Calendar.getInstance().getTime());
                                updated_by = fname.getText().toString() + " " + mname.getText().toString() + " " + lname.getText().toString();
                                if (qrcode.getText().toString() != null) {
                                    boolean check_student_num = dh.check_student_num1(student_num.getText().toString(), student_id);
                                    if (check_student_num) {


                                        boolean checkqr = dh.checkqr1(qrcode.getText().toString());
                                        if (checkqr) {
                                            dh.updateStudent_info(fname.getText().toString(), mname.getText().toString(), lname.getText().toString(), student_num.getText().toString(), password.getText().toString(), qrcode.getText().toString(), updated, updated_by);
                                            Toast.makeText(Update_student.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                                            fname.setText("");
                                            mname.setText("");
                                            lname.setText("");
                                            student_num.setText("");
                                            password.setText("");
                                            c_pass.setText("");
                                            qrcode.setText("");
                                        } else {
                                            Toast.makeText(Update_student.this, "QR code already taken", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(Update_student.this, "Student number already exist", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    boolean check_student_num = dh.check_student_num(student_num.getText().toString());
                                    if (check_student_num) {
                                        dh.insertToStudent_info(fname.getText().toString(), mname.getText().toString(), lname.getText().toString(), student_num.getText().toString(), password.getText().toString(), qrcode.getText().toString(), updated, updated_by);
                                        Toast.makeText(Update_student.this, "Student successfully added", Toast.LENGTH_LONG).show();
                                        fname.setText("");
                                        mname.setText("");
                                        lname.setText("");
                                        student_num.setText("");
                                        password.setText("");
                                        c_pass.setText("");
                                        qrcode.setText("");


                                    } else {
                                        Toast.makeText(Update_student.this, "Student no. already exist", Toast.LENGTH_LONG).show();
                                    }
                                }


                            } else {
                                Toast.makeText(Update_student.this, "Password didn't match", Toast.LENGTH_LONG).show();

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ident = Integer.parseInt(getIntent().getStringExtra("identifier"));
                    if (ident == 1) {
                        Intent in = new Intent(Update_student.this, Student_list.class);
                        in.putExtra("subject", getIntent().getStringExtra("subject"));
                        in.putExtra("section", getIntent().getStringExtra("section"));
                        in.putExtra("position", getIntent().getStringExtra("position"));
                        startActivity(in);
                    } else {
                        Intent in = new Intent(Update_student.this, Student_home.class);
                        startActivity(in);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            try {
                if (result.getContents() == null) {
                    Toast.makeText(Update_student.this, "No result found", Toast.LENGTH_LONG).show();
                } else {
                    qrcode.setText(result.getFormatName());
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
        int ident=Integer.parseInt(getIntent().getStringExtra("identifier"));
        if(ident==1){
            Intent in =new Intent(Update_student.this,Student_list.class);
            in.putExtra("subject",getIntent().getStringExtra("subject"));
            in.putExtra("section",getIntent().getStringExtra("section"));
            in.putExtra("position",getIntent().getStringExtra("position"));
            startActivity(in);
        }else{
            int ident1=Integer.parseInt(getIntent().getStringExtra("ident"));
            Intent in =new Intent(Update_student.this,Student_home.class);
            if(ident1==0){
                in.putExtra("mycode",getIntent().getStringExtra("mycode"));
            }else if(ident1==1){
                in.putExtra("student_num",getIntent().getStringExtra("student_num"));
            }
            in.putExtra("ident",getIntent().getStringExtra("ident"));
            startActivity(in);
        }

    }

}
