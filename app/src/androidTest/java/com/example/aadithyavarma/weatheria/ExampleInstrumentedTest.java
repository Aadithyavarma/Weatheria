package com.example.aadithyavarma.weatheria;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /**
     * I'm using Espresso framework completely to do the instrumentation
     * testing. So the rules and other attributes is also base don that.
     */

    /**
     * Rule to access the elements in MainActivity class;
     */
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<MainActivity>(MainActivity.class,true,false);
    /**
     * Rule to access the elements of StoreToDatabase class;
     */
    @Rule
    public ActivityTestRule<StoreToDatabase> activityTestRule =
            new ActivityTestRule<StoreToDatabase>(StoreToDatabase.class,true,false);

    /**
     * Testing for valid input from search location;
     * The testcase is passed if the output contains a part of the weather;
     */
    @Test
    public void getWeatherFromInput(){
        Intent i = new Intent();
        rule.launchActivity(i);
        onView(withId(R.id.place)).perform(typeText("Paris"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.placeSearch)).perform(click());

        String ans = MainActivity.weatherReport.getText().toString();
        String res = "clouds";
        Pattern p = Pattern.compile("\\bclouds|drizzle|rain|mist|thunderstorm|sky|snow|haze\\b",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(ans);
        if(m.find()){
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
    }

    /**
     * Testing for valid input from spinner or saved locations. Shows the
     * note saved for the location;
     */
    @Test
    public void getWeatherFromSaved(){
        Intent i = new Intent();
        rule.launchActivity(i);
        onView(withId(R.id.savedLocations)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Paris"))).perform(click());
        onView(withId(R.id.savedPlaceSearch)).perform(click());

        String ans = "\nNote:\nThe city of passion and love";
        onView(withId(R.id.weatherReport)).check(matches(withText(containsString(ans))));
    }

    /**
     * Testing if the text given in search bar is placed to place name
     * in StoreToDatabase class;
     */
    @Test
    public void nextScreen(){
        Intent i = new Intent();
        i.putExtra("place","Hawaii");
        activityTestRule.launchActivity(i);
        onView(withId(R.id.placeName)).check(matches(withText("Hawaii")));
    }

    /**
     * Testing if the save is successfully performed the app will take
     * the user back to the home screen;
     */
    @Test
    public void mainScreen(){
        Intent i = new Intent();
        rule.launchActivity(i);
        onView(withId(R.id.title)).check(matches(withText("Weatheria")));
    }


}
