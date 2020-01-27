package com.mh.myapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.mh.myapp.Adapter.Mystudent_list_adapter;
import com.mh.myapp.Adapter.Subject_adapter;
import com.mh.myapp.Database.DatabaseHelper;
import com.mh.myapp.Module.Mystudent_list;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Student_list extends AppCompatActivity {

    TextView section1;
    ListView student_list;
    ImageButton back;
    DatabaseHelper dh;
    List<Mystudent_list> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        section1 = (TextView) findViewById(R.id.section);
        student_list = (ListView) findViewById(R.id.student_list);
        back = (ImageButton) findViewById(R.id.back);

        try {

            String subject = getIntent().getStringExtra("subject");
            String section = getIntent().getStringExtra("section");
            final int pos = Integer.parseInt(getIntent().getStringExtra("position"));

            section1.setText(section);
            dh = new DatabaseHelper(this);


            int sub = dh.getSubject_id(subject);
            int sec = dh.getSection_id(section);


            try {
                list = dh.getStudent_list(sec, sub);
                Mystudent_list_adapter adapter1 = new Mystudent_list_adapter(Student_list.this, R.layout.student_list_layout, list);
                student_list.setAdapter(adapter1);
                student_list.setSelection(pos);
            } catch (Exception e) {
                e.printStackTrace();
            }

            student_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    try {

                        final Mystudent_list mystudent_list = list.get(position);

                        AlertDialog dialog = new AlertDialog.Builder(Student_list.this)
                                .setPositiveButton("Edit", null)
                                .setNegativeButton("Delete", null)
                                .setNeutralButton("Cancel", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {

                                    Intent in = new Intent(Student_list.this, Update_student.class);
                                    in.putExtra("mycode", mystudent_list.getCode());
                                    in.putExtra("subject", getIntent().getStringExtra("subject"));
                                    in.putExtra("section", getIntent().getStringExtra("section"));
                                    in.putExtra("position", String.valueOf(position));
                                    in.putExtra("identifier", String.valueOf(1));
                                    in.putExtra("ident",String.valueOf(0));
                                    startActivity(in);


                                } catch (Exception ex) {
                                    ex.printStackTrace();

                                }

                            }
                        });
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {

                                    dh.deleteStudent(mystudent_list.getId());
                                    Intent in = new Intent(Student_list.this, Student_list.class);
                                    in.putExtra("subject", getIntent().getStringExtra("subject"));
                                    in.putExtra("section", getIntent().getStringExtra("section"));
                                    in.putExtra("position",String.valueOf(position));
                                    in.putExtra("identifier", String.valueOf(1));
                                    startActivity(in);


                                } catch (Exception ex) {
                                    ex.printStackTrace();

                                }

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(Student_list.this,Admin_home.class);

                    startActivity(in);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
