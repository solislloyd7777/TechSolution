package com.mh.myapp;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mh.myapp.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dh=new DatabaseHelper(this);
        dh.StartWork();
        Intent in=new Intent(this,Home.class);
        startActivity(in);
    }
}
