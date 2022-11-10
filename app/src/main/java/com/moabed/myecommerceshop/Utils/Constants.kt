package com.moabed.myecommerceshop.Utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    const val BOARDING_ARGUMENT_KEY = "position"
    const val BOARDING_SP_KEY = "onBoarding"

    const val USERS_COLLECTION : String = "users"
    const val ESHOP_PREFERENCES : String = "EshopPreferences"
    const val USERNAME_KEY : String = "username"
    const val EXTRA_USER_KEY : String = "user_extra"
    const val READ_EXTERNAL_STORAGE_REQUEST_CODE  : Int = 1
    const val PICK_IMAGE_REQUEST_CODE : Int = 4

    const val MALE_USER : String = "Male"
    const val FEMALE_USER : String = "Female"

    const val USER_MOBILE : String = "mobile"
    const val USER_GENDER : String = "gender"

    const val IMAGE : String = "image"

    const val PROFILE_COMPLETED = "profileCompleted"

    const val USER_PROFILE_IMAGE : String = "Profile_Image"

    fun showImageChooser(activity : Activity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri:Uri?) : String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}