package com.ublox.BLE;

import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.net.Uri;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ublox.BLE.activities.AboutActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class TestAboutActivity {

    @Rule
    public IntentsTestRule<AboutActivity> act = new IntentsTestRule<>(AboutActivity.class);

    @Test
    public void clickLink_firesIntent() {
        Matcher<Intent> matchingIntent = allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData(Uri.parse("http://u-blox.com"))
        );

        intending(matchingIntent).respondWith(new ActivityResult(RESULT_OK, null));
        onView(withId(R.id.bVisit)).perform(click());
        intended(matchingIntent);
    }
}
