package com.zhbcompany.shop.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhbcompany.shop.data.local.dto.ShopItemLocal

@Dao
interface ShopDao {
    @Query("SELECT * FROM shop")
    fun getAllShopItems(): List<ShopItemLocal>

    @Query("SELECT * FROM shop WHERE id = :id")
    suspend fun getShopItemById(id: Int): ShopItemLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllShopItems(items: List<ShopItemLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(item: ShopItemLocal): Long

    @Delete
    suspend fun deleteShopItem(item: ShopItemLocal)
}