package com.mh.myapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mh.myapp.Adapter.Mystudent_list_adapter;
import com.mh.myapp.Adapter.Section_adapter;
import com.mh.myapp.Database.DatabaseHelper;
import com.mh.myapp.Module.Mystudent_list;
import com.mh.myapp.Module.Section_module;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Section extends AppCompatActivity {
    ListView section_list;
    ImageButton back,add;
    DatabaseHelper dh;
    List<Section_module> list;
    String created,updated,created_by="admin",updated_by="admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        section_list = (ListView) findViewById(R.id.list_section);
        back = (ImageButton) findViewById(R.id.back);
        add=(ImageButton)findViewById(R.id.add_section);
        dh = new DatabaseHelper(this);
        try {
            int pos = Integer.parseInt(getIntent().getStringExtra("position"));


            list = dh.getSection_list();
            Section_adapter adapter1 = new Section_adapter(Section.this, R.layout.section_layout, list);
            section_list.setAdapter(adapter1);
            section_list.setSelection(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            section_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    try {

                        final Section_module section_module = list.get(position);

                        AlertDialog dialog = new AlertDialog.Builder(Section.this)
                                .setPositiveButton("Edit", null)
                                .setNegativeButton("Delete", null)
                                .setNeutralButton("Cancel", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                updated = format1.format(Calendar.getInstance().getTime());

                                try {


                                    final AlertDialog.Builder builder = new AlertDialog.Builder(Section.this);
                                    final View mview = getLayoutInflater().inflate(R.layout.edit_section, null);
                                    final EditText name = (EditText) mview.findViewById(R.id.section_name);
                                    final ImageButton save = (ImageButton) mview.findViewById(R.id.save);


                                    dh = new DatabaseHelper(Section.this);

                                    name.setText(section_module.getSection());


                                    save.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                if (TextUtils.isEmpty(name.getText().toString())) {
                                                    Toast.makeText(Section.this, "Empty field", Toast.LENGTH_LONG).show();
                                                } else {
                                                    boolean checksection = dh.checkSection(name.getText().toString(), section_module.getSection_id());
                                                    if (checksection) {
                                                        dh.updateSection(name.getText().toString(), section_module.getSection_id(), updated, updated_by);
                                                        Toast.makeText(Section.this, "Updated successfully", Toast.LENGTH_LONG).show();
                                                        Intent in = new Intent(Section.this, Section.class);
                                                        in.putExtra("position", String.valueOf(position));
                                                        startActivity(in);
                                                    } else {
                                                        Toast.makeText(Section.this, "Subject already exist", Toast.LENGTH_LONG).show();
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


                                } catch (Exception ex) {
                                    ex.printStackTrace();

                                }

                            }
                        });
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {

                                    dh.deleteSection(section_module.getSection_id());
                                    Toast.makeText(Section.this, "Deleted successfully", Toast.LENGTH_LONG).show();
                                    Intent in = new Intent(Section.this, Section.class);

                                    in.putExtra("position", String.valueOf(0));
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

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    created = format1.format(Calendar.getInstance().getTime());
                    try {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(Section.this);
                        final View mview = getLayoutInflater().inflate(R.layout.add_section, null);
                        final EditText name = (EditText) mview.findViewById(R.id.section_name);
                        final ImageButton save = (ImageButton) mview.findViewById(R.id.save);


                        dh = new DatabaseHelper(Section.this);

                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if (TextUtils.isEmpty(name.getText().toString())) {
                                        Toast.makeText(Section.this, "Empty field", Toast.LENGTH_LONG).show();
                                    } else {
                                        boolean checksection = dh.checkSection2(name.getText().toString());
                                        if (checksection) {
                                            dh.insertSection(name.getText().toString(), created, created_by);
                                            Toast.makeText(Section.this, "Inserted successfully", Toast.LENGTH_LONG).show();
                                            Intent in = new Intent(Section.this, Section.class);
                                            in.putExtra("position", String.valueOf(0));
                                            startActivity(in);
                                        } else {
                                            Toast.makeText(Section.this, "Section already exist", Toast.LENGTH_LONG).show();
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


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(Section.this,Admin_home.class);
        in.putExtra("mycode", getIntent().getStringExtra("mycode"));
        startActivity(in);
    }
}
