package com.zhbcompany.shop.presentation.shop_new_update

import androidx.compose.ui.focus.FocusState

sealed class ShopNewUpdateEvent {
    data class EnteredTitle(val value: String) : ShopNewUpdateEvent()
    data class ChangedTitleFocus(val focusState: FocusState) : ShopNewUpdateEvent() // todo what is focus state
    data class EnteredDescription(val value: String) : ShopNewUpdateEvent()
    data class ChangedDescriptionFocus(val focusState: FocusState) : ShopNewUpdateEvent()
    object Delete: ShopNewUpdateEvent()
    object ToggleCompleted: ShopNewUpdateEvent()
    object ToggleArchived: ShopNewUpdateEvent()
    object SaveShop: ShopNewUpdateEvent()
    object Back: ShopNewUpdateEvent()
}