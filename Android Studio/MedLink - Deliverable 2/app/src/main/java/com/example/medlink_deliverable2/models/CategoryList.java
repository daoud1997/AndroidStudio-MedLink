package com.example.medlink_deliverable2.models;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.medlink_deliverable2.R;

import java.util.List;


public class CategoryList extends ArrayAdapter<Category> {
    private Activity context;
    List<Category> categories;

    public CategoryList(Activity context, List<Category> categories) {
        super(context, R.layout.activity_category_layout, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_category_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole);

        Category category = categories.get(position);
        textViewName.setText(category.getName());
        textViewRole.setText(category.getRole());
        //textViewPrice.setText(String.valueOf(product.getPrice()));
        return listViewItem;
    }
}


