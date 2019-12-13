package com.example.medlink_ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class LoggedInActivity extends AppCompatActivity {

    TextView display_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        display_name = (TextView)findViewById(R.id.display_name);

        // create the get Intent object
        Intent intent = getIntent();
        String name = intent.getStringExtra("display_username");

        // display the string into textView
        display_name.setText(name);
    }
}