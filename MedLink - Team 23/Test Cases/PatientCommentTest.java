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

public class PatientCommentTest {

    @Rule
    public ActivityTestRule<ClinicViewPatient> mActivityTestRule = new ActivityTestRule<ClinicViewPatient>(ClinicViewPatient.class);
    private ClinicViewPatient mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkCommentClinic() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.input_comment));
        EditText text = mActivity.findViewById(R.id.input_comment);
        text.setText("Wait time was longer than expected");
        String clinicReview = text.getText().toString();
        assertEquals("Wait time was longer than expected", clinicReview);
    }


}
