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

public class Registration_PatientTest {
    @Rule
    public ActivityTestRule<Registration_Patient> mActivityTestRule
            = new ActivityTestRule<Registration_Patient>(Registration_Patient.class);
    private Registration_Patient mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkEmail() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.input_email));
        EditText text = mActivity.findViewById(R.id.input_email);
        text.setText("ab@xyz.com");
        String email = text.getText().toString();
        assertEquals("ab@xyz.com", email);
    }
}
