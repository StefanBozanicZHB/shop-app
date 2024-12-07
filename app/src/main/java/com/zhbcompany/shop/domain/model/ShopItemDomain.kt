package com.zhbcompany.shop.domain.model

data class ShopItemDomain(
    val id: Int?,
    val title: String,
    val description: String,
    val store: String,
    val completed: Boolean,
)

val emptyShopItem = ShopItemDomain(
    id = null,
    title = "",
    description = "",
    store = "",
    completed = false,
)