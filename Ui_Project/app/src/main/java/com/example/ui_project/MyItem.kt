package com.example.ui_project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyItem(
    val aIcon: Int,
    val aName: String,
    val aNote: String,
    val aSale: String,
    val aPrice: Int,
    val aAddress: String,
    val aLike: Int,
    val aChat: Int,
    var isLike: Boolean
) : Parcelable