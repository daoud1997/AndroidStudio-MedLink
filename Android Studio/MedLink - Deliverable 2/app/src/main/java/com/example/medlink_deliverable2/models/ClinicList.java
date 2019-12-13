package com.example.medlink_deliverable2.models;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.medlink_deliverable2.R;

import java.util.List;

public class ClinicList extends ArrayAdapter<Employee> {
    private Activity context;
    List<Employee> employees;

    public ClinicList(Activity context, List<Employee> employees) {
        super(context, R.layout.activity_cliniclist_layout, employees);
        this.context = context;
        this.employees = employees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_cliniclist_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        // TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Employee employee = employees.get(position);
        textViewName.setText(employee.getClinicName());
        //textViewPrice.setText(String.valueOf(product.getPrice()));
        return listViewItem;
    }
}
