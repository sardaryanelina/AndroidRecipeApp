package ca.elina.recipesapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.ActivityAddUpdateRecipeBinding

class AddUpdateRecipeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityAddUpdateRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAddUpdateRecipeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupActionBar()
    }

    /**
     * A function for ActionBar setup.
     */
    private fun setupActionBar() {
        // action bar in xml -> id is toolbar_add_recipe_activity
        // we are assigning this bar using default method setSupportActionBar
        setSupportActionBar(mBinding.toolbarAddDishActivity)

        // this will allow us to have back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back button in toolbar

        // Replace the back button that we have generated.
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        // add click listener to back button
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}