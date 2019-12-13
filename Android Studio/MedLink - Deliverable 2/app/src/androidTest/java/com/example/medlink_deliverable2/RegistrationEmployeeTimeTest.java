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


public class RegistrationEmployeeTimeTest {

    @Rule
    public ActivityTestRule<Registration_Employee2> mActivityTestRule
            = new ActivityTestRule<Registration_Employee2>(Registration_Employee2.class);
    private Registration_Employee2 mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkRate() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.input_startTime));
        EditText text = mActivity.findViewById(R.id.input_startTime);
        text.setText("8:30");
        String testTime = text.getText().toString();
        assertEquals("8:30", testTime);
    }
}
