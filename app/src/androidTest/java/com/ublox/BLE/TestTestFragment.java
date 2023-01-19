package com.ublox.BLE;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ublox.BLE.activities.MainActivity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ublox.BLE.EspressoExtensions.onToast;
import static com.ublox.BLE.Wait.waitFor;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class TestTestFragment {

    @Rule
    public ActivityTestRule<MainActivity> act = new MainWithBluetoothTestRule();

    @Before
    public void setup() {
        waitFor(500);
        onView(withText("TEST")).perform(click());
    }

    @Test
    public void requestMTU_empty() {
        onView(withId(R.id.bMtu)).perform(click(), click());
        onToast(act.getActivity(), "You must enter a number").check(matches(isDisplayed()));
    }

    @Test
    public void requestMTU_tooSmall() {
        enterMTU("0");
        onView(withId(R.id.tvMtuSize)).check(matches(not(withText("0"))));
    }

    @Test
    public void requestMTU_min() {
        enterMTU("23");
        onView(withId(R.id.tvMtuSize)).check(matches(withText("23")));
    }

    @Test
    public void requestMTU_max() {
        enterMTU("247");
        onView(withId(R.id.tvMtuSize)).check(matches(withText("247")));
    }

    @Test
    public void requestMTU_tooLarge() {
        enterMTU("300");
        onView(withId(R.id.tvMtuSize)).check(matches(withText("247")));
    }

    @Test
    public void toggleCredits() {
        onView(withId(R.id.swCredits)).perform(click());
        onView(withId(R.id.swCredits)).check(matches(isChecked()));
    }

    @Ignore("Datapumping seems to lock ui here, might be badly mocked")
    @Test
    public void toggleCredits_ongoing() {
        onView(withId(R.id.swTxTest)).perform(click());
        onView(withId(R.id.swCredits)).perform(click());
        onView(withId(R.id.swCredits)).check(matches(isNotChecked()));
    }

    @Test
    public void sendOnePacket() {
        onView(withText("packet")).perform(click());
        onView(withId(R.id.swTxTest)).perform(click());
        onView(withId(R.id.tvTxCounter)).check(matches(withText("20 B")));
    }

    @Ignore("Datapumping seems to lock ui here, might be badly mocked")
    @Test
    public void sendContinuous() {
        onView(withId(R.id.continuous)).perform(click());
        onView(withId(R.id.swTxTest)).perform(click());
        onView(withId(R.id.tvTxCounter)).check(matches(not(withText("0 B"))));
    }

    @Test
    public void clearRx() {
        onView(withId(R.id.bRxReset)).perform(click());
        onView(withId(R.id.tvRxAvg)).check(matches(withText("0.00 kbps")));
    }

    @Test
    public void setPacketEmpty() {
        onView(withId(R.id.etPacketSize)).perform(click(), clearText(), closeSoftKeyboard());
        onToast(act.getActivity(), "The packet length number too big or not valid!").check(matches(isDisplayed()));
    }

    @Test
    public void toggleCredits_disconnectedNoChange() {
        onView(withId(R.id.menu_disconnect)).perform(click());
        onView(withId(R.id.swCredits)).perform(click());
        onView(withId(R.id.swCredits)).check(matches(isNotChecked()));
    }

    @Test
    public void requestMTU_disconnectedNoChange() {
        onView(withId(R.id.menu_disconnect)).perform(click());
        enterMTU("247");
        onView(withId(R.id.tvMtuSize)).check(matches(not(withText("247"))));
    }

    @Test
    public void startTxTest_disconnectedNoChange() {
        onView(withId(R.id.menu_disconnect)).perform(click());
        onView(withId(R.id.swTxTest)).perform(click());
        onView(withId(R.id.tvTxCounter)).check(matches(withText("0 B")));
    }

    @Test
    public void sendSinglePacketLargerThanMtuDisplaysCorrectly() {
        onView(withText("packet")).perform(click());
        onView(withId(R.id.etPacketSize)).perform(click(), clearText(), typeText("32"), closeSoftKeyboard());
        onView(withId(R.id.swTxTest)).perform(click());
        onView(withId(R.id.tvTxCounter)).check(matches(withText("32 B")));

    }

    private void enterMTU(String mtu) {
        onView(withId(R.id.etMtuSize)).perform(click(), typeText(mtu), closeSoftKeyboard());
        onView(withId(R.id.bMtu)).perform(click());
    }

}
