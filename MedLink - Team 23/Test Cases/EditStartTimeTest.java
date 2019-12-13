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

public class EditStartTimeTest {

    @Rule
    public ActivityTestRule<EmployeeAccountActivity> mActivityTestRule = new ActivityTestRule<EmployeeAccountActivity>(EmployeeAccountActivity.class);
    private EmployeeAccountActivity mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkBookingTime() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.editStartTime));
        EditText text = mActivity.findViewById(R.id.editStartTime);
        text.setText("9:00");
        String testRate = text.getText().toString();
        assertEquals("9:00", testRate);
    }

}
