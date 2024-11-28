package com.zhbcompany.shop.data.mapper

import com.zhbcompany.shop.data.local.dto.LocalShopItem
import com.zhbcompany.shop.data.remote.dto.RemoteShopItem
import com.zhbcompany.shop.domain.model.ShopItem

fun ShopItem.toLocalShopItem(): LocalShopItem {
    return LocalShopItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun ShopItem.toRemoteShopItem(): RemoteShopItem {
    return RemoteShopItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun LocalShopItem.toShopItem(): ShopItem {
    return ShopItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun LocalShopItem.toRemoteShopItem(): RemoteShopItem {
    return RemoteShopItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun RemoteShopItem.toShopItem(): ShopItem {
    return ShopItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun RemoteShopItem.toLocalShopItem(): LocalShopItem {
    return LocalShopItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun List<ShopItem>.toLocalShopItems(): List<LocalShopItem> {
    return this.map { it.toLocalShopItem() }
}

fun List<ShopItem>.toRemoteShopItems(): List<RemoteShopItem> {
    return this.map { it.toRemoteShopItem() }
}

fun List<LocalShopItem>.toRemoteShopItemsFromLocal(): List<RemoteShopItem> {
    return this.map { it.toRemoteShopItem() }
}

fun List<LocalShopItem>.toShopItemsFromLocal(): List<ShopItem> {
    return this.map { it.toShopItem() }
}

fun List<RemoteShopItem>.toShopItemsFromRemote(): List<ShopItem> {
    return this.map { it.toShopItem() }
}

fun List<RemoteShopItem>.toLocalShopItemsFromRemote(): List<LocalShopItem> {
    return this.map { it.toLocalShopItem() }
}