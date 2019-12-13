package com.example.medlink_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button Next;
    private EditText FullName, Email, Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullName = (EditText) findViewById(R.id.input_fullname);
        final String fullName_str = FullName.getText().toString();
        Email = (EditText) findViewById(R.id.input_email);
        final String email_str = Email.getText().toString();
        Username = (EditText) findViewById(R.id.input_username);
        final String username_str = Username.getText().toString();
        Password = (EditText) findViewById(R.id.input_password);
        final String password_str = Password.getText().toString();

        // Sign Up Activity switch
        Next = (Button) findViewById(R.id.btn_next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                intent.putExtra("FullName", fullName_str);
                intent.putExtra("Email", email_str);
                intent.putExtra("Username", username_str);
                intent.putExtra("Password", password_str);
                RegisterActivity.this.startActivity(intent);
            }
        });


    }
}



