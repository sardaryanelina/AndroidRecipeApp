package ca.elina.recipesapp.view.fragments.recipes

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import ca.elina.recipesapp.R
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.Espresso.onView

@RunWith(AndroidJUnit4::class)
@LargeTest
class RecipesFragmentTest {
    @Test
    fun checkFloatButtonDisplayed() {
        // Check the No Data Image is displayed when there is no favorite recipe is Displayed

        onView(withId(R.id.recipes_fab))
            .check(matches(isDisplayed()))
    }
}