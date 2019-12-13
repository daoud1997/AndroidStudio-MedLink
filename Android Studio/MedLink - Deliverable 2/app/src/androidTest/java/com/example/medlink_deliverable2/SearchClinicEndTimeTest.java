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


public class SearchClinicEndTimeTest {

    @Rule
    public ActivityTestRule<SearchForClinicsActivity> mActivityTestRule = new ActivityTestRule<SearchForClinicsActivity>(SearchForClinicsActivity.class);
    private SearchForClinicsActivity mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkSearchClinic() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.searchEndTime));
        EditText text = mActivity.findViewById(R.id.searchEndTime);
        text.setText("16:00");
        String endHET = text.getText().toString();
        assertEquals("16:00", endHET);
    }

}
