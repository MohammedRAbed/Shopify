package com.moabed.myecommerceshop.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id : String? = "",
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
    val image : String = "",
    val gender : String = "",
    val mobile : String = "",
    val profileCompleted : Int = 0
) : Parcelable
