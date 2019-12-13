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

public class EditEndTimeTest {

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
        assertNotNull(mActivity.findViewById(R.id.editEndTime));
        EditText text = mActivity.findViewById(R.id.editEndTime);
        text.setText("22:00");
        String testRate = text.getText().toString();
        assertEquals("22:00", testRate);
    }

}
