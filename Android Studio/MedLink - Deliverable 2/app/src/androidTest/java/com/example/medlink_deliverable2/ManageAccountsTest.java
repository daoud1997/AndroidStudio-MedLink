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


public class ManageAccountsTest {

    @Rule
    public ActivityTestRule<ManageAccounts> mActivityTestRule
            = new ActivityTestRule<ManageAccounts>(ManageAccounts.class);
    private ManageAccounts mActivity = null;
    private TextView test;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkUsername() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.input_username));
        EditText text = mActivity.findViewById(R.id.input_username);
        text.setText("user");
        String username = text.getText().toString();
        assertEquals("user", username);
    }

}
