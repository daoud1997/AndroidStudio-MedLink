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

public class SetServiceRateTest {



    @Rule
    public ActivityTestRule<Registration_Employee3> mActivityTestRule = new ActivityTestRule<Registration_Employee3>(Registration_Employee3.class);
    private Registration_Employee3 mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {


        mActivity = mActivityTestRule.getActivity();


    }




    @Test
    @UiThreadTest
    public void checkBookingTime() throws Exception {


        assertNotNull(mActivity.findViewById(R.id.editRate));
        EditText text = mActivity.findViewById(R.id.editRate);
        text.setText("125");
        String testRate = text.getText().toString();
        assertEquals("125", testRate);
    }


}
