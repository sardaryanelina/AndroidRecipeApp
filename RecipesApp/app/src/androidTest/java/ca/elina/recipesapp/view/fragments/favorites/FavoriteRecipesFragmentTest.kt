package ca.elina.recipesapp.view.fragments.favorites

import androidx.test.espresso.Espresso

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import ca.elina.recipesapp.R
import junit.framework.TestCase

@RunWith(AndroidJUnit4::class)
@LargeTest
class FavoriteRecipesFragmentTest : TestCase() {
    @Test
    fun checkNoDataImageDisplayed() {
        // Check the No Data Image is displayed when there is no favorite recipe is Displayed

        onView(withId(R.id.no_data_imageView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkNoRecipesTextDisplayed() {
        onView(withText("No Favorite Recipes.")).check(matches(isDisplayed()))
    }
}