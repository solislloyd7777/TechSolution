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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mh.myapp.Adapter.Subject_adapter;
import com.mh.myapp.Adapter.Subject_adapter1;
import com.mh.myapp.Database.DatabaseHelper;
import com.mh.myapp.Module.Subject_module;
import com.mh.myapp.Module.Subject_module1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Subject_list extends AppCompatActivity {

    List<Subject_module1> list;
    ListView subject_list;
    DatabaseHelper dh;
    String updated,updated_by="admin",created,created_by="admin";
    ImageButton back,add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        subject_list=(ListView)findViewById(R.id.list_subject);
        back=(ImageButton)findViewById(R.id.back);
        add=(ImageButton)findViewById(R.id.add_subject);


        int pos = Integer.parseInt(getIntent().getStringExtra("position"));
        dh=new DatabaseHelper(this);


        try {

            list = dh.getSubject1();

            Subject_adapter1 adapter1 = new Subject_adapter1(Subject_list.this, R.layout.subject_layout_list,list);
            subject_list.setAdapter(adapter1);
            subject_list.setSelection(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        subject_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Subject_module1 subject_module1 = list.get(position);
                if (!subject_module1.getName().equals("No Subject_yet")) {
                    try {
                        final int pos = subject_list.getFirstVisiblePosition();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        updated = format1.format(Calendar.getInstance().getTime());

                        AlertDialog dialog= new AlertDialog.Builder(Subject_list.this)
                                .setMessage(subject_module1.getName())
                                .setPositiveButton("Edit",null)
                                .setNegativeButton("Delete",null)
                                .setNeutralButton("Cancel",null)
                                .show();
                        Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button neutralButton=dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                        Button negativeButton=dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try{

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Subject_list.this);
                                        final View mview = getLayoutInflater().inflate(R.layout.edit_subject,null);
                                        final EditText name=(EditText)mview.findViewById(R.id.subject_name);
                                        final EditText code=(EditText)mview.findViewById(R.id.subject_code);
                                        final ImageButton save=(ImageButton) mview.findViewById(R.id.save);



                                        dh=new DatabaseHelper(Subject_list.this);

                                        name.setText(subject_module1.getName());
                                        code.setText(subject_module1.getCode());








                                        save.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                try{
                                                    if(TextUtils.isEmpty(name.getText().toString())){
                                                        Toast.makeText(Subject_list.this,"Empty field",Toast.LENGTH_LONG).show();
                                                    }else if(TextUtils.isEmpty(code.getText().toString())){
                                                        Toast.makeText(Subject_list.this,"Empty field",Toast.LENGTH_LONG).show();
                                                    }else{
                                                        boolean checksubject=dh.checksubject1(code.getText().toString(),name.getText().toString(),subject_module1.getSubject_id());
                                                        if(checksubject){
                                                            dh.updateSubjects(code.getText().toString(),name.getText().toString(),subject_module1.getSubject_id(),updated,updated_by);
                                                            Toast.makeText(Subject_list.this,"Updated successfully",Toast.LENGTH_LONG).show();
                                                            Intent in=new Intent(Subject_list.this,Subject_list.class);
                                                            in.putExtra("position", String.valueOf(pos));
                                                            startActivity(in);
                                                        }else{
                                                            Toast.makeText(Subject_list.this,"Subject already exist",Toast.LENGTH_LONG).show();
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
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try{

                                    dh.deleteSubject_taken1(subject_module1.getSubject_id());
                                    dh.deleteSubject(subject_module1.getSubject_id());

                                    Toast.makeText(Subject_list.this,"Removed successfully",Toast.LENGTH_LONG).show();
                                    Intent in=new Intent(Subject_list.this,Subject_list.class);
                                    in.putExtra("position", String.valueOf(pos));
                                    startActivity(in);



                                }catch (Exception ex){
                                    ex.printStackTrace();

                                }

                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Subject_list.this,Admin_home.class);
                //in.putExtra("mycode", getIntent().getStringExtra("mycode"));
                in.putExtra("my_code", getIntent().getStringExtra("mycode"));
                startActivity(in);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                created= format1.format(Calendar.getInstance().getTime());
                try {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(Subject_list.this);
                    final View mview = getLayoutInflater().inflate(R.layout.add_subject, null);
                    final EditText name = (EditText) mview.findViewById(R.id.subject_name);
                    final EditText code = (EditText) mview.findViewById(R.id.subject_code);
                    final ImageButton save = (ImageButton) mview.findViewById(R.id.save);


                    dh = new DatabaseHelper(Subject_list.this);

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (TextUtils.isEmpty(name.getText().toString())) {
                                    Toast.makeText(Subject_list.this, "Empty field", Toast.LENGTH_LONG).show();
                                } else if (TextUtils.isEmpty(code.getText().toString())) {
                                    Toast.makeText(Subject_list.this, "Empty field", Toast.LENGTH_LONG).show();
                                } else {
                                    boolean checksubject = dh.checksubject2(code.getText().toString(), name.getText().toString());
                                    if (checksubject) {
                                        dh.insertSubjects(code.getText().toString(), name.getText().toString(), created, created_by);
                                        Toast.makeText(Subject_list.this, "Inserted successfully", Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(Subject_list.this, Subject_list.class);
                                        in.putExtra("position", String.valueOf(0));
                                        startActivity(in);
                                    } else {
                                        Toast.makeText(Subject_list.this, "Subject already exist", Toast.LENGTH_LONG).show();
                                    }

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setCancelable(true);


                    builder.setView(mview);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(Subject_list.this,Admin_home.class);
        in.putExtra("mycode", getIntent().getStringExtra("mycode"));
        startActivity(in);
    }

}
