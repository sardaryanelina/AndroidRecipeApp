package ca.elina.recipesapp.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.ActivityAddUpdateRecipeBinding
import ca.elina.recipesapp.databinding.DialogCustomImageSelectionBinding

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

        mBinding.ivAddRecipeImage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_add_recipe_image -> {
                    // Toast.makeText( this,"You have clicked on the ImageView.",Toast.LENGTH_SHORT).show()
                    customImageSelectionDialog()
                    return
                }
            }
        }
    }

    private fun customImageSelectionDialog() {
        val dialog = Dialog(this)

        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)

        /* Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        dialog.setContentView(binding.root)

        // Assign the click for Camera and Gallery. Show the Toast message for now.
        binding.tvCamera.setOnClickListener {
            Toast.makeText(this, "You have clicked on the Camera.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            Toast.makeText(this, "You have clicked on the Gallery.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        //Start the dialog and display it on screen.
        dialog.show()
    }
}