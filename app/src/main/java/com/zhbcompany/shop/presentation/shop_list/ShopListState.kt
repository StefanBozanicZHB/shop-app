package com.zhbcompany.shop.presentation.shop_list

import com.zhbcompany.shop.domain.model.ShopItemDomain
import com.zhbcompany.shop.domain.util.SortingDirection
import com.zhbcompany.shop.domain.util.ShopItemOrder

data class ShopListState(
    val shopItemDomains: List<ShopItemDomain> = emptyList(),
    val shopItemOrder: ShopItemOrder = ShopItemOrder.Completed(SortingDirection.Up),
    val isLoading: Boolean = true,
    val error: String? = null
)