package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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

public class EmployeeAccountActivity extends AppCompatActivity {

    Button btn_editWorkingHours;
    private DatabaseReference db;
    private String id;

    CheckBox sun, mon, tues, wed, thurs, fri, sat;
    boolean ifSun, ifMon, ifTues, ifWed, ifThurs, ifFri, ifSat = false;
    private List hours;
    private List daysOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_account);


        db = FirebaseDatabase.getInstance().getReference();
        id = getIntent().getExtras().getString("id");


        final TextView startHr = (TextView) findViewById(R.id.view_startH);
        //startHr.setText(getIntent().getExtras().getString(""));

        final TextView endHr = (TextView) findViewById(R.id.view_endH);
        //endHr.setText(getIntent().getExtras().getString(""));

        final TextView days = (TextView) findViewById(R.id.view_days);
        // TODO: gotta somehow iterate through the ArrayList and if true, the day is displayed
        // TODO: we'll probably have to display as an array or something
        //days.setText(getIntent().getExtras().getString(""));

        final Button editServ = (Button) findViewById(R.id.btn_editServ);
        final TextView viewInsurance = findViewById(R.id.view_insurance);
        final TextView viewPaymentMethods = findViewById(R.id.view_paymentmeth);


        db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Employee employee = dataSnapshot.child(id).getValue(Employee.class);

                startHr.setText(employee.getHours().get(0));
                endHr.setText(employee.getHours().get(1));


                String daysOpen = " | ";
                for (int i=0;i<7;i++){
                    ArrayList temp = (ArrayList) employee.getDaysOpen().get(i);
                    if((Boolean)temp.get(1)){
                        daysOpen = daysOpen+((String) temp.get(0))+" | ";
                    }
                }
                days.setText(daysOpen);

                String insurance = "  ";
                for (int i=0;i<employee.getInsuranceTypes().size();i++){
                    ArrayList temp = (ArrayList) employee.getInsuranceTypes().get(i);
                    if((Boolean)temp.get(1)){
                         insurance = insurance + ((String) temp.get(0))+"  ";
                    }
                }
                viewInsurance.setText(insurance);

                String payment = "  ";
                for (int i=0;i<employee.getInsuranceTypes().size();i++){
                    ArrayList temp = (ArrayList) employee.getPaymentMethods().get(i);
                    if((Boolean)temp.get(1)){
                        payment = payment + ((String) temp.get(0)) + "  ";
                    }
                }
                viewPaymentMethods.setText(payment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //TODO: set up edit working hours btn functionality
        btn_editWorkingHours = findViewById(R.id.btn_editwh);
        btn_editWorkingHours.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showEditHoursDialogue();

            }
        });

        editServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageServicesClinic.class);
                intent.putExtra("id", id);
                startActivityForResult (intent,0);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    private void showEditHoursDialogue() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_edit_hours_dialogue, null);
        dialogBuilder.setView(dialogView);

        final EditText editStartTime = dialogView.findViewById(R.id.editStartTime);
        final EditText editEndTime = dialogView.findViewById(R.id.editEndTime);
        final Button button_submit = (Button) dialogView.findViewById(R.id.buttonUpdateTime);


        sun = dialogView.findViewById(R.id.edit_sun);
        mon = dialogView.findViewById(R.id.edit_mon);
        tues = dialogView.findViewById(R.id.edit_tue);
        wed = dialogView.findViewById(R.id.edit_wed);
        thurs = dialogView.findViewById(R.id.edit_thurs);
        fri = dialogView.findViewById(R.id.edit_fri);
        sat = dialogView.findViewById(R.id.edit_sat);


        dialogBuilder.setTitle("Working Hours");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String startStr = editStartTime.getText().toString().trim();
                String endStr = editEndTime.getText().toString().trim();

                if(!validateTime(startStr)){
                    editStartTime.setError("Invalid time");
                }

                if(!validateTime(endStr)){
                    editEndTime.setError("Invalid time");
                }

                if (validateTime(startStr) && validateTime(endStr) && checkboxCheck(v)){

                    hours = new ArrayList();

                    hours.add(startStr);
                    hours.add(endStr);

                    //TODO: so the check buttons, like mon tues wed etc, are all referencing the buttons in the registrationemployee 2 layouts. theyre also called the same thing, and when i try to change their id, it changes both. so fix

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

                    db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Employee employee = dataSnapshot.child(id).getValue(Employee.class);
                            employee.setHours(hours);
                            employee.setDaysOpen(daysOpen);
                            db.child("Accounts").child("Users").child("Employees").child(id).setValue(employee);
                            b.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


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
    public boolean checkboxCheck(View v) {
        if (sun.isChecked()) {
            ifSun = true;
        }
        if (mon.isChecked()) {
            ifMon = true;
        }
        if (tues.isChecked()) {
            ifTues = true;
        }
        if (wed.isChecked()) {
            ifWed = true;
        }
        if (thurs.isChecked()) {
            ifThurs = true;
        }
        if (fri.isChecked()) {
            ifFri = true;
        }
        if (sat.isChecked()) {
            ifSat = true;
        }

        // If no selection has been made
        if (!(sun.isChecked()) && !(mon.isChecked()) && !(tues.isChecked()) && !(wed.isChecked())
                && !(thurs.isChecked()) && !(fri.isChecked()) && !(sat.isChecked())) {
            tues.setError("Choose at least one!");
            return false;
        }
        return true;
    }

    }
