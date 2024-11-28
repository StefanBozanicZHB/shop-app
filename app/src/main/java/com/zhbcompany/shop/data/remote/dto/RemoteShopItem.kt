package com.zhbcompany.shop.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RemoteShopItem(
    @SerializedName("ID")
    val id: Int?,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("Timestamp")
    val timestamp: Long,

    @SerializedName("Completed")
    val completed: Boolean,

    @SerializedName("Archived")
    val archived: Boolean
)