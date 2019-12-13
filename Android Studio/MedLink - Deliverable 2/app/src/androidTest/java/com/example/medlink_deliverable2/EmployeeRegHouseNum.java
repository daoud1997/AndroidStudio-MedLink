package com.example.medlink_deliverable2;

import android.widget.EditText;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class EmployeeRegHouseNum {

    @Rule
    public ActivityTestRule<Registration_Employee> RegEmployeeActivityTestRule
            = new ActivityTestRule<Registration_Employee>(Registration_Employee.class);
    private Registration_Employee RegActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        RegActivity = RegEmployeeActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkHouseNum() throws Exception {
        assertNotNull(RegActivity.findViewById(R.id.input_housenum));
        EditText text = RegActivity.findViewById(R.id.input_housenum);
        text.setText("dfsd");
        String houseNum = text.getText().toString();
        assertNotEquals("55", houseNum);
    }


}
