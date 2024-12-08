package com.zhbcompany.shop.data.repo

import android.util.Log
import com.zhbcompany.shop.data.cache.ShopCache
import com.zhbcompany.shop.data.local.ShopDao
import com.zhbcompany.shop.data.mapper.toLocalShopItem
import com.zhbcompany.shop.data.mapper.toLocalShopItemsFromRemote
import com.zhbcompany.shop.data.mapper.toRemoteShopItem
import com.zhbcompany.shop.data.mapper.toShopItem
import com.zhbcompany.shop.data.mapper.toShopItemsFromLocal
import com.zhbcompany.shop.data.mapper.toShopItemsFromRemote
import com.zhbcompany.shop.data.remote.ShopApi
import com.zhbcompany.shop.data.remote.dto.ShopItemRemote
import com.zhbcompany.shop.domain.model.ShopItemDomain
import com.zhbcompany.shop.domain.repo.ShopRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class ShopRepositoryImpl(
    private val dao: ShopDao,
    private val api: ShopApi,
    private val cache: ShopCache,
    private val dispatcher: CoroutineDispatcher
) : ShopRepository {

    override suspend fun getAllShopItems(forceUpdate: Boolean): List<ShopItemDomain> {
        return if (forceUpdate || cache.isEmpty()) {
            try {
                getAllShopItemsFromRemote()
                cache.getItems()
            } catch (exception: Exception) {
                val localItems = dao.getAllShopItems().toShopItemsFromLocal()
                cache.saveItems(localItems)
                cache.getItems()
            }
        } else {
            cache.getItems()
        }
    }

    override suspend fun getAllShopItemsFromLocal(): List<ShopItemDomain> {
        return dao.getAllShopItems().toShopItemsFromLocal()
    }

    private suspend fun getAllShopItemsFromRemote() {
        return withContext(dispatcher) {
            try {
                // When working with Firebase Realtime Database, the structure of the response changes based on the number of items
                val shopItems = try {
                    api.getAllShopItem()
                } catch (exception: Exception) {
                    api.getAllShopItems().values.toList()
                }

                dao.addAllShopItems(shopItems.toLocalShopItemsFromRemote())
                cache.saveItems(shopItems.toShopItemsFromRemote())
            } catch (e: Exception) {
                Log.e("HTTP", "Error: $e")
                when (e) {
                    is UnknownHostException, is ConnectException, is HttpException -> {
                        Log.e("HTTP", "Error: No data from Remote")
                        val localTaskItems = dao.getAllShopItems()
                        if (localTaskItems.isNotEmpty()) {
                            cache.saveItems(localTaskItems.toShopItemsFromLocal())
                        } else {
                            Log.e("Cache", "Error: No data from local Room cache")
                            throw Exception("Error: Device offline and\nno data from local Room cache")
                        }
                    }

                    else -> cache.saveItems(emptyList())
                }
            }
        }
    }

    override suspend fun getShopItemById(id: Int): ShopItemDomain? {
        return dao.getShopItemById(id)?.toShopItem()
    }

    override suspend fun addShopItem(item: ShopItemDomain) {
        val id = dao.addShopItem(item.toLocalShopItem()).toInt()
        val newItem = item.copy(id = id)
        try {
            val url = "shop/$id.json"
            api.addShopItem(url, newItem.toRemoteShopItem())
            cache.saveItem(newItem)
        } catch (e: Exception) {
            Log.e("HTTP", "Error: Could not add item | error: $e")
        }
    }

    override suspend fun updateShopItem(item: ShopItemDomain) {
        cache.saveItem(item)
        dao.addShopItem(item.toLocalShopItem())
        api.updateShopItem(item.id, item.toRemoteShopItem())
    }

    override suspend fun deleteShopItem(item: ShopItemDomain) {
        try {
            val response = api.deleteShopItem(item.id)
            if (response.isSuccessful) {
                dao.deleteShopItem(item.toLocalShopItem())
                cache.deleteItem(item)
                Log.i("API_DELETE", "Response Successful")
            } else {
                Log.i("API_DELETE", "Response Unsuccessful")
                Log.i("API_DELETE", response.message())
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException, is HttpException -> {
                    Log.e("HTTP", "Error: Could not delete")
                }

                else -> throw e
            }
        }
    }
}