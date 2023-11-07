package com.example.lab11helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
   boolean firstclick_=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleButton(View view) {
        final String MsTag = "MainActivity.Dd";
        Log.d(MsTag, "button was pressed....");
        TextView tv = findViewById(R.id.outputInfoID);
        if (firstclick_) {
            tv.setText("Welcome to CS 6011");
            firstclick_ = false;
        } else {
            tv.setText("You clicked the button again");
        }
    }


}