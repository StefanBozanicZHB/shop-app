package com.zhbcompany.shop.data.cache

import com.zhbcompany.shop.domain.model.ShopItemDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShopCache {
    private var items: MutableList<ShopItemDomain> = mutableListOf()

    private val _dataCache = MutableStateFlow<List<ShopItemDomain>>(emptyList())
    val dataCache: StateFlow<List<ShopItemDomain>> get() = _dataCache

    private fun updateCache(newItems: List<ShopItemDomain>) {
        _dataCache.value = newItems
    }

    fun isEmpty(): Boolean = items.isEmpty()

    fun getItems(): List<ShopItemDomain> = items

    fun saveItems(items: List<ShopItemDomain>) {
        this.items.clear()
        this.items.addAll(items)
        updateCache(items)
    }

    fun saveItem(item: ShopItemDomain) {
        val existingTaskIndex = items.indexOfFirst { it.id == item.id }
        if (existingTaskIndex != -1) {
            items[existingTaskIndex] = item
        } else {
            items.add(item)
        }
        updateCache(items)
    }

    fun deleteItem(item: ShopItemDomain) {
        items.remove(item)
        updateCache(items)
    }
}