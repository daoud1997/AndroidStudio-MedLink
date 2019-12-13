package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.medlink_deliverable2.models.Account;
import com.example.medlink_deliverable2.models.Category;
import com.example.medlink_deliverable2.models.CategoryList;
import com.example.medlink_deliverable2.models.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration_Employee2 extends AppCompatActivity {

    private DatabaseReference db;
    private String id;
    private List hours;
    private List daysOpen;
    private List insuranceAccepted;
    private List paymentsAccepted;

    EditText startTime, endTime;

    CheckBox sun, mon, tues, wed, thurs, fri, sat;
    CheckBox uhip, ohip, priv;
    CheckBox credit, debit, cash;

    boolean ifSun, ifMon, ifTues, ifWed, ifThurs, ifFri, ifSat = false;
    boolean ifuhip, ifohip, ifpriv = false;
    boolean ifCredit, ifDebit, ifCash = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__employee2);

        id = getIntent().getExtras().getString("id");

        startTime = findViewById(R.id.input_startTime);
        endTime = findViewById(R.id.input_endTime);

        sun = findViewById(R.id.cb_sun);
        mon = findViewById(R.id.cb_mon);
        tues = findViewById(R.id.cb_tue);
        wed = findViewById(R.id.cb_wed);
        thurs = findViewById(R.id.cb_thurs);
        fri = findViewById(R.id.cb_fri);
        sat = findViewById(R.id.cb_sat);

        uhip = findViewById(R.id.cb_uhip);
        ohip = findViewById(R.id.cb_ohip);
        priv = findViewById(R.id.cb_private);

        credit = findViewById(R.id.cb_credit);
        debit = findViewById(R.id.cb_debit);
        cash = findViewById(R.id.cb_cash);

        final Button tore3 = findViewById(R.id.btn_tore3);

        tore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String startTimeSTR = startTime.getText().toString().trim();
                final String endTimeSTR = endTime.getText().toString().trim();

                if(!validateTime(startTimeSTR)){
                    startTime.setError("Invalid time");
                }

                if(!validateTime(endTimeSTR)){
                    endTime.setError("Invalid time");
                }

                if (validateTime(startTimeSTR) && validateTime(endTimeSTR) && checkboxCheck(v)){
                    hours = new ArrayList();

                    hours.add(startTimeSTR);
                    hours.add(endTimeSTR);

                    List <Object> mon = new ArrayList<Object>();

                    mon.add("Monday");
                    mon.add(ifMon);
                    List <Object> tues = new ArrayList<Object>();
                    tues.add("Tuesday");
                    tues.add(ifTues);
                    List <Object> wed = new ArrayList<Object>();
                    wed.add("Wednesday");
                    wed.add(ifWed);
                    List <Object> thurs = new ArrayList<Object>();
                    thurs.add("Thursday");
                    thurs.add(ifThurs);
                    List <Object> fri = new ArrayList<Object>();
                    fri.add("Friday");
                    fri.add(ifFri);
                    List <Object> sat = new ArrayList<Object>();
                    sat.add("Saturday");
                    sat.add(ifSat);
                    List <Object> sun = new ArrayList<Object>();
                    sun.add("Sunday");
                    sun.add(ifSun);

                    daysOpen = new ArrayList();

                    daysOpen.add(mon);
                    daysOpen.add(tues);
                    daysOpen.add(wed);
                    daysOpen.add(thurs);
                    daysOpen.add(fri);
                    daysOpen.add(sat);
                    daysOpen.add(sun);


                    List <Object> uhip = new ArrayList<Object>();
                    uhip.add("UHIP");
                    uhip.add(ifuhip);
                    List <Object> ohip = new ArrayList<Object>();
                    ohip.add("OHIP");
                    ohip.add(ifohip);
                    List <Object> privat = new ArrayList<Object>();
                    privat.add("PRIVATE");
                    privat.add(ifpriv);

                    insuranceAccepted = new ArrayList();

                    insuranceAccepted.add(uhip);
                    insuranceAccepted.add(ohip);
                    insuranceAccepted.add(privat);


                    List credit = new ArrayList();
                    credit.add("Credit");
                    credit.add(ifCredit);
                    List debit = new ArrayList();
                    debit.add("Debit");
                    debit.add(ifDebit);
                    List cash = new ArrayList();
                    cash.add("Cash");
                    cash.add(ifCash);

                    paymentsAccepted = new ArrayList();

                    paymentsAccepted.add(credit);
                    paymentsAccepted.add(debit);
                    paymentsAccepted.add(cash);

                    OnNextButton(v);

                }

            }
        });


    }

    public void OnNextButton(View view){
        db = FirebaseDatabase.getInstance().getReference();
        db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Employee employee = dataSnapshot.child(id).getValue(Employee.class);
                employee.setHours(hours);
                employee.setDaysOpen(daysOpen);
                employee.setInsuranceTypes(insuranceAccepted);
                employee.setPaymentMethods(paymentsAccepted);
                db.child("Accounts").child("Users").child("Employees").child(id).setValue(employee);

                Intent intent = new Intent(getApplicationContext(), Registration_Employee3.class);
                intent.putExtra("id", id);
                startActivityForResult (intent,0);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Validating time
    public boolean validateTime(String time){
        if(TextUtils.isEmpty(time))
            return false;

        String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        Pattern pattern = Pattern.compile(TIME_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    //Checking if CheckBox checked
    public boolean checkboxCheck(View v)
    {
        if(sun.isChecked()) {
            ifSun = true;
        }
        if(mon.isChecked()) {
            ifMon = true;
        }
        if(tues.isChecked()) {
            ifTues = true;
        }
        if(wed.isChecked()) {
            ifWed = true;
        }
        if(thurs.isChecked()) {
            ifThurs = true;
        }
        if(fri.isChecked()) {
            ifFri = true;
        }
        if(sat.isChecked()) {
            ifSat = true;
        }

        // If no selection has been made
        if(!(sun.isChecked()) && !(mon.isChecked()) && !(tues.isChecked()) && !(wed.isChecked())
                && !(thurs.isChecked()) && !(fri.isChecked()) && !(sat.isChecked())){
            tues.setError("Choose at least one!");
            return false;
        }

        if(uhip.isChecked()) {
            ifuhip = true;
        }
        if(ohip.isChecked()) {
            ifohip = true;
        }
        if(priv.isChecked()) {
            ifpriv = true;
        }

        if(!(uhip.isChecked()) && !(ohip.isChecked()) && !(priv.isChecked())){
            priv.setError("Choose at least one!");
            return false;
        }

        if(credit.isChecked()) {
            ifCredit = true;
        }
        if(debit.isChecked()) {
            ifDebit = true;
        }
        if(cash.isChecked()) {
            ifCash = true;
        }

        if(!(credit.isChecked()) && !(debit.isChecked()) && !(cash.isChecked())){
            cash.setError("Choose at least one!");
            return false;
        }
        return true;
    }
}
