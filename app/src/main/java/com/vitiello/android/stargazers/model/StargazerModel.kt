package com.vitiello.android.stargazers.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Antonio Vitiello
 */
@Parcelize
class StargazerModel(
    var id: Int,
    var username: String,
    var avatarUrl: String? = null,
    var htmlUrl: String? = null,
) : Parcelable