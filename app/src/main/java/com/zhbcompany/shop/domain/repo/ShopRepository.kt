package com.zhbcompany.shop.domain.repo

import com.zhbcompany.shop.domain.model.ShopItemDomain

interface ShopRepository {
    suspend fun getAllShopItems(forceUpdate: Boolean = false): List<ShopItemDomain>
    suspend fun getAllShopItemsFromLocal(): List<ShopItemDomain>
    suspend fun getShopItemById(id: Int): ShopItemDomain?
    suspend fun addShopItem(item: ShopItemDomain)
    suspend fun updateShopItem(item: ShopItemDomain)
    suspend fun deleteShopItem(item: ShopItemDomain)
}