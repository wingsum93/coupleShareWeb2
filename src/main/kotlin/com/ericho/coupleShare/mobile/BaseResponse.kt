package com.ericho.coupleShare.mobile

import com.google.gson.annotations.SerializedName

/**
 * Created by steve_000 on 15/4/2017.
 */
class BaseResponse<T> {
    @SerializedName("status")
    var isStatus = false

    @SerializedName("extra")
    var extra: List<T>? = null

    @SerializedName("error_message")
    var errorMessage: String? = null

    @SerializedName("other_message")
    var otherMessage //use when success;
            : String? = null

}