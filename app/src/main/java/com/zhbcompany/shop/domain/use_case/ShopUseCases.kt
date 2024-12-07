package com.zhbcompany.shop.domain.use_case

import com.zhbcompany.shop.domain.model.ShopItemDomain
import com.zhbcompany.shop.domain.repo.ShopRepository
import com.zhbcompany.shop.domain.util.InvalidShopItemEmptyTitleException
import com.zhbcompany.shop.domain.util.ShopItemOrder
import com.zhbcompany.shop.domain.util.SortingDirection

// todo separate in separate class
class ShopUseCases(
    private val repo: ShopRepository
) {
    suspend fun addShopItem(shopItemDomain: ShopItemDomain) {
        if (shopItemDomain.title.isBlank()) {
            throw InvalidShopItemEmptyTitleException()
        }
        repo.addShopItem(shopItemDomain)
    }

    suspend fun updateShopItem(shopItemDomain: ShopItemDomain) {
        if (shopItemDomain.title.isBlank()) {
            throw InvalidShopItemEmptyTitleException()
        }
        repo.updateShopItem(shopItemDomain)
    }

    suspend fun deleteShopItem(shopItemDomain: ShopItemDomain) {
        repo.deleteShopItem(shopItemDomain)
    }

    suspend fun toggleCompleteShopItem(shopItemDomain: ShopItemDomain) {
        repo.updateShopItem(shopItemDomain.copy(completed = !shopItemDomain.completed))
    }

    suspend fun getShopItems(
        shopItemOrder: ShopItemOrder = ShopItemOrder.Store(SortingDirection.Down)
    ): ShopUseCaseResult {
        // todo change this logic
        val shopItems = repo.getAllShopItems()

        return when (shopItemOrder.sortingDirection) {
            is SortingDirection.Down -> {
                when (shopItemOrder) {
                    is ShopItemOrder.Title -> ShopUseCaseResult.Success(shopItems.sortedByDescending { it.title.lowercase() })
                    is ShopItemOrder.Store -> ShopUseCaseResult.Success(shopItems.sortedByDescending { it.store })
                    is ShopItemOrder.Completed -> ShopUseCaseResult.Success(shopItems.sortedByDescending { it.completed })
                }
            }

            is SortingDirection.Up -> {
                when (shopItemOrder) {
                    is ShopItemOrder.Title -> ShopUseCaseResult.Success(shopItems.sortedBy { it.title.lowercase() })
                    is ShopItemOrder.Store -> ShopUseCaseResult.Success(shopItems.sortedBy { it.store })
                    is ShopItemOrder.Completed -> ShopUseCaseResult.Success(shopItems.sortedBy { it.completed })
                }
            }
        }

    }
}

sealed class ShopUseCaseResult {
    data class Success(val shopItemDomains: List<ShopItemDomain>) : ShopUseCaseResult()
    data class Error(val message: String) : ShopUseCaseResult()
}