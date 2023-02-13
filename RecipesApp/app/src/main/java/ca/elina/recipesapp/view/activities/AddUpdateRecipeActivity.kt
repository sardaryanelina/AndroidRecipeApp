package ca.elina.recipesapp.view.activities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.ActivityAddUpdateRecipeBinding
import ca.elina.recipesapp.databinding.DialogCustomImageSelectionBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddUpdateRecipeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityAddUpdateRecipeBinding

    // A global variable for stored image path.
    private var mImagePath: String = ""

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
                    // This is not required anymore in the latest API levels.
                    // If you read this code on your older device or on a device that uses
                    // an older Android version, this will work.
                    // Comment out or delete the line below, because it was for older devices below API 30
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        // Here after all the permission are granted launch the CAMERA to capture an image.
                        // Check that report is not null then implement the code
                        report?.let {
                            if (report.areAllPermissionsGranted()) {

                                // Hide or delete the Toast message for now just to know that we have the permission.
                                // Toast.makeText(this@AddUpdateRecipeActivity,"You have the Camera permission now to capture image.",Toast.LENGTH_SHORT).show()

                                // Start camera using the Image capture action.
                                // Get the result in the onActivityResult method as we are using startActivityForResult.
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, CAMERA)
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        // Show the alert dialog
                        showRationalDialogForPermissions()
                    }
                    // If you want to receive permission listener callbacks on the same thread that fired the permission request,
                    // you just need to call onSameThread before checking for permissions:
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
                .withPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    // We deleted Manifest.permission.WRITE_EXTERNAL_STORAGE
                    // because we are only listening for one permission.
                )
                .withListener(object : PermissionListener {

                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                        // Hide or Delete the Toast message for now just to know that we have the permission.
                        //Toast.makeText(this@AddUpdateRecipeActivity,"You have the Gallery permission now to select image.",Toast.LENGTH_SHORT).show()

                        // Here after all the permission are granted launch the gallery to select and image.
                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )

                        startActivityForResult(galleryIntent, GALLERY)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@AddUpdateRecipeActivity,
                            "You have denied the storage permission to select image.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
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

    // Override the onActivityResult method.
    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {

                // Get Image from Camera and set it to the ImageView
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap // Bitmap from camera
                mBinding.ivDishImage.setImageBitmap(thumbnail) // Set to the imageView.


                // Replace the add icon with edit icon once the image is selected.
                mBinding.ivAddRecipeImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@AddUpdateRecipeActivity,
                        R.drawable.ic_vector_edit
                    )
                )
            }else if (requestCode == GALLERY) {

                data?.let {
                    // Here we will get the select image URI.
                    val selectedPhotoUri = data.data

                    // Set Selected Image URI to the imageView using Glide
                    Glide.with(this@AddUpdateRecipeActivity)
                        .load(selectedPhotoUri)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                @Nullable e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                // log exception
                                Log.e("TAG", "Error loading image", e)
                                return false // important to return false so the error placeholder can be placed
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: com.bumptech.glide.load.DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                val bitmap: Bitmap = resource.toBitmap()
                                mImagePath = saveImageToInternalStorage(bitmap)
                                Log.i("ImagePath", mImagePath)
                                return false
                            }
                        })
                        .into(mBinding.ivDishImage)

                    // Replace the add icon with edit icon once the image is selected.
                    mBinding.ivAddRecipeImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@AddUpdateRecipeActivity,
                            R.drawable.ic_vector_edit
                        )
                    )
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "User cancelled image selection")
        }
    }

    /**
     * A function to save a copy of an image to internal storage for FavDishApp to use.
     *
     * @param bitmap
     */
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {

        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        /**
         * The Mode Private here is
         * File creation mode: the default mode, where the created file can only
         * be accessed by the calling application (or all applications sharing the
         * same user ID).
         */
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Mention a file name to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image absolute path
        return file.absolutePath
    }

    // Define the Companion Object to define the constants used in the class.
    // We will define the constant for camera and gallery, as well as favorite recipes.
    companion object {
        private const val CAMERA = 1
        private const val GALLERY = 2
        private const val IMAGE_DIRECTORY = "FavRecipeImages"
    }
}