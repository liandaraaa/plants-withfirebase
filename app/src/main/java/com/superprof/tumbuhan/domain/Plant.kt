package com.superprof.tumbuhan.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Plant(
    val name:String = "",
    val imageUrl:String = "",
    val description:String = ""
) : Parcelable