package com.zhbcompany.shop.domain.model

data class ShopItem(
    val id: Int?,
    val title: String,
    val description: String,
    val timestamp: Long,
    val completed: Boolean,
    val archived: Boolean
)