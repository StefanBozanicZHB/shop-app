package com.zhbcompany.shop.presentation.shop_list

import com.zhbcompany.shop.domain.model.ShopItem
import com.zhbcompany.shop.domain.util.SortingDirection
import com.zhbcompany.shop.domain.util.ShopItemOrder

data class ShopListState(
    val shopItems: List<ShopItem> = emptyList(),
    val shopItemOrder: ShopItemOrder = ShopItemOrder.Time(SortingDirection.Down, true),
    val isLoading: Boolean = true,
    val error: String? = null
)