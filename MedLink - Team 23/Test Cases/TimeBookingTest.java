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

public class TimeBookingTest {
    @Rule
    public ActivityTestRule<ChooseServiceForAppointmentActivity> mActivityTestRule = new ActivityTestRule<ChooseServiceForAppointmentActivity>(ChooseServiceForAppointmentActivity.class);
    private ChooseServiceForAppointmentActivity mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkBookingTime() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.editTextTime));
        EditText text = mActivity.findViewById(R.id.editTextTime);
        text.setText("9:45");
        String testBookingTime = text.getText().toString();
        assertEquals("9:45", testBookingTime);
    }
}
