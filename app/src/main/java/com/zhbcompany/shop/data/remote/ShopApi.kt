package com.zhbcompany.shop.data.remote

import com.zhbcompany.shop.data.remote.dto.RemoteShopItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShopApi {
    @GET("shop.json")
    suspend fun getAllShopItems(): List<RemoteShopItem>

//    @GET("shop.json?orderBy=\"ID\"")
//    suspend fun getShopItemById(@Query("equalTo") id: Int): Map<String, RemoteShopItem>

//    @POST("shop.json")
//    suspend fun addShopItem(@Body url: String, @Body updatedShopItem: RemoteShopItem): Response<Unit>

    @PUT
    suspend fun addShopItem(@Body url: String, @Body updatedShopItem: RemoteShopItem): Response<Unit>

    @DELETE("shop/{id}.json")
    suspend fun deleteShopItem(@Path("id") id: Int?): Response<Unit>

    @PUT("shop/{id}.json")
    suspend fun updateShopItem(@Path("id") id: Int?, @Body shopItem: RemoteShopItem): Response<Unit>
}