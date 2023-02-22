package ca.elina.recipesapp.view.activities

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import ca.elina.recipesapp.R
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.STARTED)

    }

    @Test
    fun checkFloatButtonDisplayed() {
        // Check the No Data Image is displayed when there is no favorite recipe is Displayed

        onView(withId(R.id.recipes_fab))
            .check(matches(isDisplayed()))
    }
    // recipes_fab
}