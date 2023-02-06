package ca.elina.recipesapp.view.activities

import android.Manifest
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.ActivityAddUpdateRecipeBinding
import ca.elina.recipesapp.databinding.DialogCustomImageSelectionBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

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

        // Assign the click for Camera.
        binding.tvCamera.setOnClickListener {

            // Ask for the permission while selecting the image from camera using Dexter Library.
            // Comment out or Remove the toast message.
            // Toast.makeText(this, "You have clicked on the Camera.", Toast.LENGTH_SHORT).show()
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        // Here after all the permission are granted launch the CAMERA to capture an image.
                        if (report!!.areAllPermissionsGranted()) {

                            // Show the Toast message for now just to know that we have the permission.
                            Toast.makeText(this@AddUpdateRecipeActivity,"You have the Camera permission now to capture image.",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        // Show the alert dialog
                        showRationalDialogForPermissions()

                    }
                }).onSameThread()
                .check()

            // dismiss the dialog in the end
            dialog.dismiss()
        }

        // Assign the click for Gallery.
        binding.tvGallery.setOnClickListener {

            // Ask for the permission while selecting the image from Gallery using Dexter Library.
            // Comment out or Remove the toast message.
            // Toast.makeText(this, "You have clicked on the Gallery.", Toast.LENGTH_SHORT).show()
            Dexter.withContext(this@AddUpdateRecipeActivity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(object : MultiplePermissionsListener {

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                        // Here after all the permission are granted launch the gallery to select and image.
                        if (report!!.areAllPermissionsGranted()) {
                            // Show the Toast message for now just to know that we have the permission.
                            Toast.makeText(this@AddUpdateRecipeActivity,"You have the Gallery permission now to select image.",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                    // If you want to receive permission listener callbacks on the same thread that fired the permission request,
                    // you just need to call onSameThread before checking for permissions:
                }).onSameThread()
                .check()

            // dismiss the dialog in the end
            dialog.dismiss()
        }

        //Start the dialog and display it on screen.
        dialog.show()
    }

    /**
     * A function used to show the alert dialog when the permissions are denied and need to allow it from settings app info.
     */
    //Create a function to show the alert message that the permission is necessary to proceed further if user deny it. And ask him to allow it from setting.
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}