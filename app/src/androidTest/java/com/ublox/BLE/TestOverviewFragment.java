package com.ublox.BLE;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ublox.BLE.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ublox.BLE.Wait.waitFor;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class TestOverviewFragment {

    @Rule
    public ActivityTestRule<MainActivity> act = new MainWithBluetoothTestRule();

    @Test
    public void readRSSI() {
        waitFor(2000);
        onView(withId(R.id.tvRSSI)).check(matches(not(withText("-"))));
    }
}
