package com.boilerplate.model

import com.google.gson.annotations.SerializedName

data class ResponseModel<T>(
    @SerializedName(value = "data", alternate = ["Data"]) val `data`: T,
    @SerializedName(value = "message", alternate = ["Message"]) val message: String,
    @SerializedName(value = "success", alternate = ["Success"]) val success: Boolean,
)