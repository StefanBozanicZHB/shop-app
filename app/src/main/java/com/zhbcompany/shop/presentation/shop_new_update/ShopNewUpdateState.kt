package com.zhbcompany.shop.presentation.shop_new_update

import com.zhbcompany.shop.domain.model.ShopItemDomain


data class ShopNewUpdateState(
    val isTitleHintVisible: Boolean = true,
    val isDescriptionHintVisible: Boolean = true,
    val shopItemDomain: ShopItemDomain = ShopItemDomain(
        title = "",
        description = "",
        store = "",
        completed = false,
        id = null
    ),
    val isLoading: Boolean = true,
    val error: String? = null,
)
