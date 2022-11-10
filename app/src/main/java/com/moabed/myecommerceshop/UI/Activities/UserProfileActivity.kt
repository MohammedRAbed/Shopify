package com.moabed.myecommerceshop.UI.Activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.moabed.myecommerceshop.FireBaseFirestore.FireBaseFireStoreClass
import com.moabed.myecommerceshop.Models.User
import com.moabed.myecommerceshop.R
import com.moabed.myecommerceshop.Utils.Constants
import com.moabed.myecommerceshop.Utils.GlideLoader
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    lateinit var user : User
    private var selectedImageFileUri : Uri? = null
    private var selectedImageUrl : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setActionBar(user_profile_toolbar)

        getUserDetailsToComplete()
    }

    private fun getUserDetailsToComplete() {
        user = User()
        if (intent.hasExtra(Constants.EXTRA_USER_KEY)) {
            user = intent.getParcelableExtra(Constants.EXTRA_USER_KEY)!!
        }
        disableEditingOnValuedDetails(et_complete_first_name, user.firstName)
        disableEditingOnValuedDetails(et_complete_last_name, user.lastName)
        disableEditingOnValuedDetails(et_complete_email, user.email)
    }

    private fun disableEditingOnValuedDetails(detailEditText : EditText, detailText : String) {
        detailEditText.isEnabled = false
        detailEditText.setText(detailText)
    }

    private fun validateUserDetails() : Boolean{
        return if (et_complete_mobile_number.text.toString().length!=12) {
            showSnackBar("Please Enter you're mobile number with length = 12", true)
            false
        } else true
    }

    private fun updateProfileDetails() {
        val userHashMap = HashMap<String, Any>()

        userHashMap[Constants.USER_GENDER] =
            if (male_radio.isChecked)
                Constants.MALE_USER
            else
                Constants.FEMALE_USER

        userHashMap[Constants.USER_MOBILE] = et_complete_mobile_number.text.toString().trim()

        if (selectedImageUrl.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = selectedImageUrl
        }

        userHashMap[Constants.PROFILE_COMPLETED] = 1
        FireBaseFireStoreClass().updateUserDetails(this, userHashMap)
    }

    fun userProfileUpdated() {
        hideProgressDialog()
        Toast.makeText(this,"PROFILE UPDATED !", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,DashboardActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    fun uploadImageSuccess(imageUrl : String) {
        hideProgressDialog()
        selectedImageUrl = imageUrl
        Toast.makeText(this,"IMAGE UPLOADED !", Toast.LENGTH_SHORT).show()
        updateProfileDetails()
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.profile_pic_frame -> {
                if (ContextCompat.checkSelfPermission(
                        this,android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )==PackageManager.PERMISSION_GRANTED) {
                    Constants.showImageChooser(this)
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_EXTERNAL_STORAGE_REQUEST_CODE
                    )
                    //TODO onRequestPermissionResult()
                }
            }
            R.id.save_completed_user_details -> {
                if (validateUserDetails()) {
                    showProgressDialog("Please Wait")
                    if (selectedImageFileUri != null) {
                        FireBaseFireStoreClass().uploadImageToCloudStorage(this, selectedImageFileUri!!)
                    } else {
                        updateProfileDetails()
                    }
                }

            }
        }
    }

    /** WHEN PERMISSION IS ALLOWED */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this)
            } else {
                Toast.makeText(this,"Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode==Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data!=null) {
                    try {
                        selectedImageFileUri = data.data!!
                        //TODO Set the selected image as a profile pic.
                        GlideLoader(this).loadUserPicture(selectedImageFileUri!!,user_profile_img)
                    } catch (e:IOException) {
                        e.printStackTrace()
                        Toast.makeText(this,"Image Selection Failed",Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } else if (requestCode==Activity.RESULT_CANCELED) {
            Toast.makeText(this,"RESULT CANCELED", Toast.LENGTH_SHORT)
                .show()
        }
    }
}