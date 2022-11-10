package com.moabed.myecommerceshop.FireBaseFirestore

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.moabed.myecommerceshop.Models.User
import com.moabed.myecommerceshop.UI.Activities.LoginActivity
import com.moabed.myecommerceshop.UI.Activities.RegisterActivity
import com.moabed.myecommerceshop.UI.Activities.UserProfileActivity
import com.moabed.myecommerceshop.Utils.Constants

class FireBaseFireStoreClass {
    private val fireBaseFireStore = Firebase.firestore

    fun registerUserDetails(activity : RegisterActivity, user: User) {
        fireBaseFireStore.collection(Constants.USERS_COLLECTION)
            .document(user.id!!)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                activity.registerUserSuccess()
            }
            .addOnFailureListener {
                Toast.makeText(activity,"Error registering the user", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun updateUserDetails(activity: UserProfileActivity, userHashMap: HashMap<String,Any>) {
        fireBaseFireStore.collection(Constants.USERS_COLLECTION)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                activity.userProfileUpdated()
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
                println(">------ Failed to Update User Details ------<")
            }
    }

    fun uploadImageToCloudStorage(activity: Activity,imageUri: Uri) {
        val sRcf = Firebase.storage.reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(activity,imageUri)
        )

        sRcf.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
            Log.e(
                "Firebase Image URL",
                taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
            )

            taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                Log.e("Downloadable Image URL", uri.toString())
                when(activity) {
                    is UserProfileActivity -> activity.uploadImageSuccess(uri.toString())
                }
            }
        } .addOnFailureListener{ exception ->
            when(activity) {
                is UserProfileActivity -> {
                    activity.hideProgressDialog()
                    Toast.makeText(activity,"Failed to Store Profile Image",Toast.LENGTH_SHORT).show()
                }
            }
            Log.e(activity.javaClass.simpleName,exception.message!!,exception)
        }
    }



    fun getUserDetails(activity: Activity) {
        fireBaseFireStore.collection(Constants.USERS_COLLECTION)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                val mSharedPreferences =
                    activity.getSharedPreferences(Constants.ESHOP_PREFERENCES,Context.MODE_PRIVATE)
                val editor = mSharedPreferences.edit()
                editor.putString(Constants.USERNAME_KEY,"${user!!.firstName} ${user.lastName}")
                editor.apply()
                when(activity) {
                    is LoginActivity -> {
                        activity.loginUserSuccess(user)
                    }
                }
            }
            .addOnFailureListener {
                when(activity) {
                    is LoginActivity -> {
                        println("<--------ERROR GETTING DETAILS------->")
                    }
                }
            }
    }

    fun getCurrentUserId() : String {
        val currentUser = Firebase.auth.currentUser
        var currentUserId = ""
        if (currentUser!=null) currentUserId = currentUser.uid
        return currentUserId
    }
}

