package ca.elina.recipesapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.ActivityAddUpdateRecipeBinding

class AddUpdateRecipeActivity : AppCompatActivity() {

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
        setSupportActionBar(mBinding.toolbarAddDishActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back button in toolbar

        // click listener to back button in toolbar
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener { onBackPressed() }
    }
}