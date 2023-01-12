package com.mandiri.gamesapp.data.base.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
open class BaseErrorResponse(
    @SerializedName("error")
    val error: String? = null
)