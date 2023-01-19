package com.ublox.BLE;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ublox.BLE.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ublox.BLE.EspressoExtensions.onEveryView;
import static com.ublox.BLE.Wait.waitFor;

@RunWith(AndroidJUnit4.class)
public class TestMainActivity {

    @Rule
    public ActivityTestRule<MainActivity> act = new MainWithBluetoothTestRule();

    @Before
    public void setup() {
        waitFor(500);
    }

    @Test
    public void tabsExists() {
        onEveryView(
            onView(withText(act.getActivity().getString(R.string.title_section1).toUpperCase())),
            onView(withText("SERVICES")),
            onView(withText("CHAT")),
            onView(withText("TEST"))
        ).check(matches(isDisplayed()));
    }

    @Test
    public void disconnectFromDevice() {
        onView(withId(R.id.menu_disconnect)).perform(click());
        onView(withId(R.id.menu_connect)).check(matches(isDisplayed()));
    }

    @Test
    public void reconnectToDevice() {
        onView(withId(R.id.menu_disconnect)).perform(click());
        onView(withId(R.id.menu_connect)).perform(click());
        onView(withId(R.id.menu_disconnect)).check(matches(isDisplayed()));
    }
}
