package com.multazamgsd.storeez.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("error") var error: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("loginResult") var loginResult: LoginResult? = LoginResult()
)

data class LoginResult(
    @SerializedName("userId") var userId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("token") var token: String? = null
)