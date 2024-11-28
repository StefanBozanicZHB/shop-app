package com.zhbcompany.shop.presentation.shop_list

import com.zhbcompany.shop.domain.model.ShopItem
import com.zhbcompany.shop.domain.util.ShopItemOrder

sealed class ShopListEvent {
    data class Sort(val shopItemOrder: ShopItemOrder) : ShopListEvent()
    data class Delete(val item: ShopItem) : ShopListEvent()
    data class ToggleCompleted(val item: ShopItem) : ShopListEvent()
    object UndoDelete : ShopListEvent()
}