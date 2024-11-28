package com.zhbcompany.shop.presentation.util

sealed class Screen(val route: String) {
    object ShopItemListScreen: Screen("shopItemList_screen")
    object ShopNewUpdateScreen: Screen("shopNewUpdate_screen")
}