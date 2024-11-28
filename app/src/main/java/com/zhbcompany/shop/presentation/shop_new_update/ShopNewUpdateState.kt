package com.zhbcompany.shop.presentation.shop_new_update

import com.zhbcompany.shop.domain.model.ShopItem


data class ShopNewUpdateState(
    val isTitleHintVisible: Boolean = true,
    val isDescriptionHintVisible: Boolean = true,
    val shopItem: ShopItem = ShopItem(
        title = "",
        description = "",
        timestamp = 0,
        completed = false,
        archived = false,
        id = null
    ),
    val isLoading: Boolean = true,
    val error: String? = null,
)
