package ca.elina.recipesapp.view.activities

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import ca.elina.recipesapp.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(SplashActivity::class.java)


    @Test
    fun checkAppLogoDisplayed() {
        // Check the splash activity app logo is displayed
        Espresso.onView(ViewMatchers.withId(R.id.iv_logo))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}