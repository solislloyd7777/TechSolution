package com.mh.myapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

public class Add_student extends AppCompatActivity {

    EditText fname,mname,lname,student_num,password,c_pass;
    TextView qrcode;
    Button getqr,save;
    DatabaseHelper dh;
    ImageButton back;
    String created,created_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

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

        getqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final IntentIntegrator intentIntegrator=new IntentIntegrator(Add_student.this);
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
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            created = format1.format(Calendar.getInstance().getTime());
                            created_by=fname.getText().toString()+" "+mname.getText().toString()+" "+lname.getText().toString();
                            dh = new DatabaseHelper(Add_student.this);
                            if (qrcode.getText().toString() != null) {
                                boolean check_student_num=dh.check_student_num(student_num.getText().toString());
                                if(check_student_num) {


                                    boolean checkqr = dh.checkqr(qrcode.getText().toString());
                                    if (checkqr) {
                                        dh.insertToStudent_info(fname.getText().toString(), mname.getText().toString(), lname.getText().toString(), student_num.getText().toString(), password.getText().toString(), qrcode.getText().toString(),created,created_by);
                                        Toast.makeText(Add_student.this, "Student successfully added", Toast.LENGTH_LONG).show();
                                        fname.setText("");
                                        mname.setText("");
                                        lname.setText("");
                                        student_num.setText("");
                                        password.setText("");
                                        c_pass.setText("");
                                        qrcode.setText("");
                                    } else {
                                        Toast.makeText(Add_student.this, "QR code already taken", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(Add_student.this, "Student no. already exist", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                boolean check_student_num=dh.check_student_num(student_num.getText().toString());
                                if(check_student_num) {
                                    dh.insertToStudent_info(fname.getText().toString(), mname.getText().toString(), lname.getText().toString(), student_num.getText().toString(), password.getText().toString(), qrcode.getText().toString(),created,created_by);
                                Toast.makeText(Add_student.this, "Student successfully added", Toast.LENGTH_LONG).show();
                                    fname.setText("");
                                    mname.setText("");
                                    lname.setText("");
                                    student_num.setText("");
                                    password.setText("");
                                    c_pass.setText("");
                                    qrcode.setText("");


                                }else{
                                    Toast.makeText(Add_student.this, "Student no. already exist", Toast.LENGTH_LONG).show();
                                }
                            }


                        } else {
                            Toast.makeText(Add_student.this, "Password didn't match", Toast.LENGTH_LONG).show();

                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(Add_student.this,Home.class);
                startActivity(in);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            try {
                if (result.getContents() == null) {
                    Toast.makeText(Add_student.this, "No result found", Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        Intent in =new Intent(Add_student.this,Home.class);
        startActivity(in);
    }

}
