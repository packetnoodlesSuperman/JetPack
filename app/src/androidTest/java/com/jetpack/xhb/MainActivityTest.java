package com.jetpack.xhb;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void textViewTest(){
        onView(withId(R.id.ac_main_tv))
                .check(matches(withText("UserModel001")));
    }
    @Test
    public void textViewClickTest(){
        onView(withId(R.id.ac_main_tv))
                .perform(click())
                .check(matches(withText("UserModel"+(System.currentTimeMillis())/10000)));
    }

    @Test
    public void rvScrollTest(){
        onView(withId(R.id.ac_main_rv))
                .perform(scrollTo());
    }

    @Test
    public void ensureTextChangesWork() throws InterruptedException {
        //输入文字, 并点击按钮
        onView(withId(R.id.ac_main_et))
                .perform(typeText("111"), closeSoftKeyboard());
        onView(withId(R.id.ac_main_et)).perform(click());
        Thread.sleep(1000);
        //检查输入框文字是否改变
        onView(withId(R.id.ac_main_et))
                .perform(typeText("222"), closeSoftKeyboard());
        onView(withId(R.id.ac_main_et)).check(matches(withText("111222")));
        
    }


}