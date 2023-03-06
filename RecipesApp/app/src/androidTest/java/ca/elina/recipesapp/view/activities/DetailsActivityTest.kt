package ca.elina.recipesapp.view.activities

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import ca.elina.recipesapp.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailsActivityTest {
    // To got to Details Activity, we need to go to Main Activity and click on the recipe
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        // Click on the Recipes
        Espresso.onView(withId(R.id.navHostFragment))
            .perform(ViewActions.click())
    }

    @Test
    fun checkViewPagerDisplayed() {
        // Check details activity view pager is displayed
        Espresso.onView(withId(R.id.viewPager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkAppBarDisplayed() {
        // Check details activity app bar is displayed
        Espresso.onView(withId(R.id.appBar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity toolbar of app bar is displayed
        Espresso.onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity tab layout of app bar is displayed
        Espresso.onView(withId(R.id.tabLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkDetailsActivityOverviewDisplayed() {
        // Check details activity Overview image is displayed
        Espresso.onView(withId(R.id.main_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity Overview Time text is displayed
        Espresso.onView(withId(R.id.time_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity Overview Time image is displayed
        Espresso.onView(withId(R.id.time_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity Overview Likes text is displayed
        Espresso.onView(withId(R.id.likes_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity Overview Likes image is displayed
        Espresso.onView(withId(R.id.likes_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity Overview Summary is displayed
        Espresso.onView(withId(R.id.summary_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkDetailsActivityOverview_TypesDisplayed() {

        // Check details activity vegetarian type text is displayed
        Espresso.onView(withId(R.id.vegetarian_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity vegetarian type image is displayed
        Espresso.onView(withId(R.id.vegetarian_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity vegan type text is displayed
        Espresso.onView(withId(R.id.vegan_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity vegan type image is displayed
        Espresso.onView(withId(R.id.vegan_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity gluten free type text is displayed
        Espresso.onView(withId(R.id.gluten_free_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity gluten free type image is displayed
        Espresso.onView(withId(R.id.gluten_free_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity dairy free type text is displayed
        Espresso.onView(withId(R.id.dairy_free_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity dairy free type image is displayed
        Espresso.onView(withId(R.id.dairy_free_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity healthy type text is displayed
        Espresso.onView(withId(R.id.healthy_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity healthy type image is displayed
        Espresso.onView(withId(R.id.healthy_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity cheap type text is displayed
        Espresso.onView(withId(R.id.cheap_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity cheap type image is displayed
        Espresso.onView(withId(R.id.cheap_imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun checkShareRecipeDisplayed() {
        // Check details activity - share menu icon is displayed
        Espresso.onView(withId(R.id.action_share_recipe))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun checkSaveToFavoritesDisplayed() {
        // Check details activity - save to favorites menu icon is displayed
        Espresso.onView(withId(R.id.save_to_favorites_menu))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Check details activity - save to favorites menu icon is clicked
        Espresso.onView(withId(R.id.save_to_favorites_menu))
            .perform(ViewActions.click())

    }

    @Test
    fun checkSavedToFavorites() {
        // Check details activity - save to favorites menu icon is clicked
        Espresso.onView(withId(R.id.save_to_favorites_menu))
            .perform(ViewActions.click())

        // Check showSnackBar displays "Recipe saved."
        Espresso.onView(withText("Recipe saved."))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkRemovedFromFavorites() {
        // Check details activity - save to favorites menu icon is clicked
        Espresso.onView(withId(R.id.save_to_favorites_menu))
            .perform(ViewActions.click())

        // Check showSnackBar displays "Recipe saved."
        Espresso.onView(withText("Removed from Favorites."))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}