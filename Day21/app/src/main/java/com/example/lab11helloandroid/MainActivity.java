package com.example.lab11helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleButton(View view){
        final String MsTag = "MainActivity.Dd";
        Log.d(MsTag, "button was pressed....");

        TextView tv = findViewById(R.id.outputInfoID);
        tv.setText("Welcome to CS 6011");
    }


}