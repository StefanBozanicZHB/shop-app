package com.zhbcompany.shop.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ShopItemRemote(
    @SerializedName("ID")
    val id: Int?,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("Store")
    val store: String,

    @SerializedName("Completed")
    val completed: Boolean,
)