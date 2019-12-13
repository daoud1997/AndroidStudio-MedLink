package com.example.medlink_deliverable2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class WelcomePageActivityAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_admin);

        final Button btn_manageacc = findViewById(R.id.btn_manage_acc);
        final Button btn_manageserv = findViewById(R.id.btn_manage_serv);
        final Button btn_signout = findViewById(R.id.btn_signout);


        btn_manageacc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnManageAccountButton(v);
            }
        });

        btn_manageserv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnManageServicesButton(v);
            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnSignOutButton(v);
            }
        });
    }

    public void OnManageAccountButton(View view){
        Intent intent = new Intent(getApplicationContext(), ManageAccounts.class);
        startActivityForResult (intent,0);
    }

    public void OnManageServicesButton(View view){
        Intent intent = new Intent(getApplicationContext(), ManageServices.class);
        startActivityForResult (intent,0);
    }

    public void OnSignOutButton(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult (intent,0);
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
