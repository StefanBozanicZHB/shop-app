package com.zhbcompany.shop.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class LocalShopItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val description: String,
    val store: String,
    val completed: Boolean,
)