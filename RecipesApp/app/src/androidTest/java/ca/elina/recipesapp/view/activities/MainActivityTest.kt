package ca.elina.recipesapp.view.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ca.elina.recipesapp.R
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun checkMainActivityNavigationHostFragmentDisplayed() {
        // Check main activity navigation host fragment is displayed
        // All recipes are displayed
        onView(withId(R.id.navHostFragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkBottomNavigationDisplayed() {
        // Check the bottom navigation is displayed
        onView(withId(R.id.bottomNavigationView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkShimmerRVDisplayed() {
        // Check the shimmer recycler view is displayed
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkFloatButtonDisplayed() {
        // Check the floating button is displayed
        onView(withId(R.id.recipes_fab))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkFloatButtonClicked() {
        // Check floating button click
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())
    }


    @Test
    fun checkRecipesBottomSheetDialogTitlesDisplayed() {
        // Before: we need to click the floating button to see the dialog displayed
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())

        // Check the dialog of recipes bottom sheet meal type title is displayed
        onView(withId(R.id.mealType_txt))
            .check(matches(isDisplayed()))

        // Check the dialog of recipes bottom sheet diet type title is displayed
        onView(withId(R.id.dietType_txt))
            .check(matches(isDisplayed()))

        // Check the dialog of recipes bottom sheet diet type text "Diet Type" is displayed
        onView(withText("Diet Type"))
            .check(matches(isDisplayed()))

        // Check the dialog of recipes bottom sheet diet type text from string.xml is displayed
        onView(withText(R.string.diet_type))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkRecipesBottomSheetDialogChipGroupDisplayed() {
        // Before: we need to click the floating button to see the dialog displayed
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())

        // Check the dialog of recipes bottom sheet meal type Chip Group is displayed
        onView(withId(R.id.mealType_chipGroup))
            .check(matches(isDisplayed()))

        // Check the dialog of recipes bottom sheet diet type Chip Group is displayed
        onView(withId(R.id.dietType_chipGroup))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkRecipesBottomSheetDialogChipsChecked() {
        // Before: we need to click the floating button to see the dialog displayed
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())

        // Check the main course chip is checked
        onView(withId(R.id.main_course_chip))
            .check(matches(isChecked()))

        // Check the gluten free chip is checked
        onView(withId(R.id.gluten_free_chip))
            .check(matches(isChecked()))
    }

    @Test
    fun checkDialogApplyButtonClicked() {
        // Click the floating button to go to the dialog
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())

        // Click the apply button to go back to main recipes screen
        onView(withId(R.id.apply_btn))
            .perform(ViewActions.click())
    }

    @Test
    fun checkRecipesDisplayedAfterApplyClicked() {
        // Click the floating button to go to the dialog
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())

        // Click the apply button to go back to main recipes screen
        onView(withId(R.id.apply_btn))
            .perform(ViewActions.click())

        // Check the main recipes screen is displayed
        onView(withId(R.id.navHostFragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkRecipesBottomSheetDialogChipsChangesSaved() {
        // Before: we need to click the floating button to see the dialog displayed
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())

        // Click on dessert chip (this will uncheck the main course chip)
        onView(withId(R.id.dessert_chip))
            .perform(ViewActions.click())

        // Click the apply button to go back to main recipes screen
        onView(withId(R.id.apply_btn))
            .perform(ViewActions.click())

        // Check the main recipes screen is displayed
        onView(withId(R.id.navHostFragment))
            .check(matches(isDisplayed()))

        // Open the dialog again to check if the dessert chip is checked now

        // Click again the floating button to see the dialog changes displayed
        onView(withId(R.id.recipes_fab))
            .perform(ViewActions.click())

        // Check the dessert chip is checked now
        onView(withId(R.id.dessert_chip))
            .check(matches(isChecked()))
    }
}