package com.example.mobileapp_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Submitted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted);
    }

    public void addRecord(View view) {
        Intent myActivity = new Intent(this, Submitted.class);
        startActivity(myActivity);
    }
}
