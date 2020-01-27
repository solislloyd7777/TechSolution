package com.mh.myapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mh.myapp.Adapter.Subject_adapter;
import com.mh.myapp.Database.DatabaseHelper;
import com.mh.myapp.Module.Subject_module;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Select_subject extends AppCompatActivity {
    List<Subject_module> list;
    ListView subject_list;
    DatabaseHelper dh;
    String updated,updated_by,created,created_by;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        subject_list=(ListView)findViewById(R.id.list_subject);
        back=(ImageButton)findViewById(R.id.back);




try {
    final int user_id = Integer.parseInt(getIntent().getStringExtra("user_id"));
    int pos = Integer.parseInt(getIntent().getStringExtra("position"));

        dh=new DatabaseHelper(this);

        final String[] array=dh.getStudent_name(user_id);

        try {

            list = dh.getSubject(user_id);

            Subject_adapter adapter1 = new Subject_adapter(Select_subject.this, R.layout.subject_content_layout, list);
            subject_list.setAdapter(adapter1);
            subject_list.setSelection(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            subject_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Subject_module subject_module = list.get(position);
                    if (!subject_module.getName().equals("No Subject_yet")) {
                        try {
                            final int pos = subject_list.getFirstVisiblePosition();
                            created_by = array[2] + ", " + array[0] + " " + array[1].substring(0, 1)+"." ;
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            created = format1.format(Calendar.getInstance().getTime());

                            AlertDialog dialog = new AlertDialog.Builder(Select_subject.this)
                                    .setMessage(subject_module.getName())
                                    .setPositiveButton("Take", null)
                                    .setNegativeButton("No", null)
                                    .show();
                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        dh.insertSubject_taken(user_id, subject_module.getSubject_id(), created, created_by);
                                        Toast.makeText(Select_subject.this, "Added successfully", Toast.LENGTH_LONG).show();
                                        int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
                                        Intent in = new Intent(Select_subject.this, Select_subject.class);
                                        if(ident==0){
                                            in.putExtra("mycode",getIntent().getStringExtra("mycode"));
                                        }else if(ident==1) {
                                            in.putExtra("student_num", getIntent().getStringExtra("student_num"));
                                        }
                                        in.putExtra("position", String.valueOf(pos));
                                        in.putExtra("user_id", getIntent().getStringExtra("user_id"));
                                        in.putExtra("ident",getIntent().getStringExtra("ident"));
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

                                        dh.deleteSubject_taken(user_id, subject_module.getSubject_id());
                                        Toast.makeText(Select_subject.this, "Removed successfully", Toast.LENGTH_LONG).show();

                                        int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
                                        Intent in = new Intent(Select_subject.this, Select_subject.class);
                                        in.putExtra("position", String.valueOf(pos));
                                        in.putExtra("user_id", getIntent().getStringExtra("user_id"));
                                        if(ident==0){
                                            in.putExtra("mycode",getIntent().getStringExtra("mycode"));
                                        }else if(ident==1) {
                                            in.putExtra("student_num", getIntent().getStringExtra("student_num"));
                                        }
                                        in.putExtra("ident",getIntent().getStringExtra("ident"));
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
                }


            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int ident = Integer.parseInt(getIntent().getStringExtra("ident"));
                        Intent in = new Intent(Select_subject.this, Student_home.class);
                        if (ident == 0) {
                            in.putExtra("mycode", getIntent().getStringExtra("mycode"));
                        } else if (ident == 1) {
                            in.putExtra("student_num", getIntent().getStringExtra("student_num"));
                        }
                        in.putExtra("ident", getIntent().getStringExtra("ident"));
                        startActivity(in);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
}catch (Exception e){
    e.printStackTrace();
}

}

    @Override
    public void onBackPressed() {
        int ident=Integer.parseInt(getIntent().getStringExtra("ident"));
        Intent in = new Intent(Select_subject.this, Student_home.class);
        if(ident==0){
            in.putExtra("mycode",getIntent().getStringExtra("mycode"));
        }else if(ident==1) {
            in.putExtra("student_num", getIntent().getStringExtra("student_num"));
        }
        in.putExtra("ident",getIntent().getStringExtra("ident"));
        startActivity(in);
    }
}
