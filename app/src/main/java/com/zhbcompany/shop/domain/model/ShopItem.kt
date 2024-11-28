package com.zhbcompany.shop.domain.model

data class ShopItem(
    val id: Int?,
    val title: String,
    val description: String,
    val store: String,
    val completed: Boolean,
)