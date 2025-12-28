package com.example.quickapp.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    @SerializedName("username")
    val userName: String,
    val email: String,
    //TODO Address Object
    val phone: String,
    val website: String,
    // TODO: Company Object 
)
