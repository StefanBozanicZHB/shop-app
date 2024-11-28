package com.zhbcompany.shop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhbcompany.shop.data.local.dto.LocalShopItem

@Database(
    entities = [LocalShopItem::class],
    version = 1,
    exportSchema = false
)
abstract class ShopDatabase : RoomDatabase() {
    abstract val dao: ShopDao

    companion object {
        const val DATABASE_NAME = "shop_database"
    }
}