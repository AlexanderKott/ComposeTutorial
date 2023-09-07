package com.example.composetutorial.vk.domain

import android.os.Parcelable
 import kotlinx.android.parcel.IgnoredOnParcel
 import kotlinx.android.parcel.Parcelize


 @Parcelize
data class PostItem(
    val id : Long,
    val communityid : Long,
    val name : String,
    val time : String,
    val postAva : String,
    val text : String,
    val imageAttach : String,
    val statistics: List<StatisticsItem>,
    val isLiked: Boolean
    )  : Parcelable
