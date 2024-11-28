package com.zhbcompany.shop.domain.repo

import com.zhbcompany.shop.domain.model.ShopItem

interface ShopRepository {
    suspend fun getAllShopItems(): List<ShopItem>
    suspend fun getAllShopItemsFromLocal(): List<ShopItem>
    suspend fun getAllShopItemsFromRemote()
    suspend fun getShopItemById(id: Int): ShopItem?
    suspend fun addShopItem(item: ShopItem)
    suspend fun updateShopItem(item: ShopItem)
    suspend fun deleteShopItem(item: ShopItem)
}