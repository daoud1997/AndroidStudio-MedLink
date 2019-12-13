package com.example.medlink_deliverable2;


import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class SearchByDayTest {

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
        assertNotNull(mActivity.findViewById(R.id.search_fri));
        CheckBox searchFri = mActivity.findViewById(R.id.search_fri);

        searchFri.setOnCheckedChangeListener();
        assertTrue(searchFri.isChecked());
    }

}
