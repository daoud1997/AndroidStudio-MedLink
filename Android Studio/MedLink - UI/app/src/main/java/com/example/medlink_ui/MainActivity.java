package com.example.medlink_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private EditText Username,Password;
    private Button SignIn, SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sign Up Activity switch
        SignUp = (Button)findViewById(R.id.btn_signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // Everything that has to do with Signing in
        SignIn = (Button)findViewById(R.id.btn_signin);
        Username = (EditText)findViewById(R.id.input_username);
        Password = (EditText)findViewById(R.id.input_password);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString(); // Converts username to string
                Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                intent.putExtra("display_username", username);
                MainActivity.this.startActivity(intent);
            }
        });


    }


}