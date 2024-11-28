package com.zhbcompany.shop.presentation.shop_list

import com.zhbcompany.shop.domain.model.ShopItemDomain
import com.zhbcompany.shop.domain.util.ShopItemOrder

sealed class ShopListEvent {
    data class Sort(val shopItemOrder: ShopItemOrder) : ShopListEvent()
    data class Delete(val item: ShopItemDomain) : ShopListEvent()
    data class ToggleCompleted(val item: ShopItemDomain) : ShopListEvent()
    object UndoDelete : ShopListEvent()
}