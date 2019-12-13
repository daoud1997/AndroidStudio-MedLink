package com.example.medlink_deliverable2;


import android.widget.EditText;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class Registration_EmployeeTest {

    @Rule
    public ActivityTestRule<Registration_Employee> mActivityTestRule
            = new ActivityTestRule<Registration_Employee>(Registration_Employee.class);
    private Registration_Employee mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkDateOfBirth() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.input_dob));
        EditText text = mActivity.findViewById(R.id.input_dob);
        text.setText("01/01/2000");
        String dob = text.getText().toString();
        assertEquals("01/01/2000", dob);
    }

}
