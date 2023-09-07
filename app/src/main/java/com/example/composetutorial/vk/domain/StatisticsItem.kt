package com.example.composetutorial.vk.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatisticsItem(
    val type :  StatisticType,
    val count : Long
) : Parcelable

enum class StatisticType {
    VIEW,  REPLY, COMMENT, LIKE
}