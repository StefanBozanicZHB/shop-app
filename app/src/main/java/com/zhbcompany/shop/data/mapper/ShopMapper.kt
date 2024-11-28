package com.zhbcompany.shop.data.mapper

import com.zhbcompany.shop.data.local.dto.ShopItemLocal
import com.zhbcompany.shop.data.remote.dto.ShopItemRemote
import com.zhbcompany.shop.domain.model.ShopItemDomain

fun ShopItemDomain.toLocalShopItem(): ShopItemLocal {
    return ShopItemLocal (
        title = title,
        description = description,
        store = store,
        id = id,
        completed = completed
    )
}

fun ShopItemDomain.toRemoteShopItem(): ShopItemRemote {
    return ShopItemRemote (
        title = title,
        description = description,
        store = store,
        id = id,
        completed = completed
    )
}

fun ShopItemLocal.toShopItem(): ShopItemDomain {
    return ShopItemDomain (
        title = title,
        description = description,
        store = store,
        id = id,
        completed = completed
    )
}

fun ShopItemLocal.toRemoteShopItem(): ShopItemRemote {
    return ShopItemRemote (
        title = title,
        description = description,
        store = store,
        id = id,
        completed = completed
    )
}

fun ShopItemRemote.toShopItem(): ShopItemDomain {
    return ShopItemDomain (
        title = title,
        description = description,
        store = store,
        id = id,
        completed = completed
    )
}

fun ShopItemRemote.toLocalShopItem(): ShopItemLocal {
    return ShopItemLocal (
        title = title,
        description = description,
        store = store,
        id = id,
        completed = completed
    )
}

fun List<ShopItemDomain>.toLocalShopItems(): List<ShopItemLocal> {
    return this.map { it.toLocalShopItem() }
}

fun List<ShopItemDomain>.toRemoteShopItems(): List<ShopItemRemote> {
    return this.map { it.toRemoteShopItem() }
}

fun List<ShopItemLocal>.toRemoteShopItemsFromLocal(): List<ShopItemRemote> {
    return this.map { it.toRemoteShopItem() }
}

fun List<ShopItemLocal>.toShopItemsFromLocal(): List<ShopItemDomain> {
    return this.map { it.toShopItem() }
}

fun List<ShopItemRemote>.toShopItemsFromRemote(): List<ShopItemDomain> {
    return this.map { it.toShopItem() }
}

fun List<ShopItemRemote>.toLocalShopItemsFromRemote(): List<ShopItemLocal> {
    return this.map { it.toLocalShopItem() }
}