package com.zhbcompany.shop.data.remote

import com.zhbcompany.shop.data.remote.dto.ShopItemRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShopApi {
    @GET("shop.json")
    suspend fun getAllShopItem(): List<ShopItemRemote>

    @GET("shop.json")
    suspend fun getAllShopItems(): Map<String, ShopItemRemote>

    @PUT("{url}")
    suspend fun addShopItem(
        @Path("url") url: String,
        @Body updatedShopItem: ShopItemRemote
    ): Response<Unit>

    @DELETE("shop/{id}.json")
    suspend fun deleteShopItem(@Path("id") id: Int?): Response<Unit>

    @PUT("shop/{id}.json")
    suspend fun updateShopItem(@Path("id") id: Int?, @Body shopItem: ShopItemRemote): Response<Unit>
}