package com.zhbcompany.shop.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhbcompany.shop.data.local.dto.LocalShopItem

@Dao
interface ShopDao {
    @Query("SELECT * FROM shop")
    fun getAllShopItems(): List<LocalShopItem>

    @Query("SELECT * FROM shop WHERE id = :id")
    suspend fun getShopItemById(id: Int): LocalShopItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllShopItems(items: List<LocalShopItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(item: LocalShopItem): Long

    @Delete
    suspend fun deleteShopItem(item: LocalShopItem)
}