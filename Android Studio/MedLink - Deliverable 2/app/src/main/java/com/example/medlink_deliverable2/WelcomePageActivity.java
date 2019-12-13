package com.example.medlink_deliverable2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomePageActivity extends AppCompatActivity {

    private String id;
    private String roleRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        id = getIntent().getExtras().getString("id");


        final Button btn_signout = findViewById(R.id.btn_signout);
        btn_signout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnSignOutButton(v);
            }
        });

        final Button btn_account = findViewById(R.id.btn_account);
        btn_account.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                roleRep = getIntent().getExtras().getString("userType");
                OnAccountButton(v, roleRep);
            }
        });


        TextView name = (TextView) findViewById(R.id.loggedin_name);
        name.setText(getIntent().getExtras().getString("firstName"));

        TextView role = (TextView) findViewById(R.id.loggedin_role);
        role.setText(getIntent().getExtras().getString("userType"));


    }


    public void OnSignOutButton(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult (intent,0);
    }

    public void OnAccountButton(View view, String roleRep){
        if(roleRep.equals("employee")){
            Intent intent = new Intent(getApplicationContext(), EmployeeAccountActivity.class);
            intent.putExtra("id",id);
            startActivityForResult (intent,0);
        } else if(roleRep.equals("patient")){
            Intent intent = new Intent(getApplicationContext(), PatientAccountActivity.class);
            intent.putExtra("id",id);
            startActivityForResult (intent,0);
        }
    }

    //HERE TO DISABLE THE HARDWARE BACK BOTTON SINCE THERE IS A BUTTON CALLED SIGN OUT TO BE PRESSED
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
