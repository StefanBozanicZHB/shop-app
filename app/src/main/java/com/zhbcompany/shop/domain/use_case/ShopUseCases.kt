package com.zhbcompany.shop.domain.use_case

import com.zhbcompany.shop.util.ShopUseCasesStrings
import com.zhbcompany.shop.domain.model.ShopItem
import com.zhbcompany.shop.domain.repo.ShopRepository
import com.zhbcompany.shop.domain.util.InvalidShopItemException
import com.zhbcompany.shop.domain.util.SortingDirection
import com.zhbcompany.shop.domain.util.ShopItemOrder

// todo separate in separate class
class ShopUseCases(
    private val repo: ShopRepository
) {
    suspend fun addShopItem(shopItem: ShopItem) {
        if (shopItem.title.isBlank() || shopItem.description.isBlank()) {
            throw InvalidShopItemException(ShopUseCasesStrings.EMPTY_TITLE_OR_DESCRIPTION)
        }
        repo.addShopItem(shopItem)
    }

    suspend fun updateShopItem(shopItem: ShopItem) {
        if (shopItem.title.isBlank() || shopItem.description.isBlank()) {
            throw InvalidShopItemException(ShopUseCasesStrings.EMPTY_TITLE_OR_DESCRIPTION)
        }
        repo.updateShopItem(shopItem)
    }

    suspend fun deleteShopItem(shopItem: ShopItem) {
        repo.deleteShopItem(shopItem)
    }

    suspend fun toggleCompleteShopItem(shopItem: ShopItem) {
        repo.updateShopItem(shopItem.copy(completed = !shopItem.completed))
    }

    suspend fun getShopItemById(id: Int): ShopItem? {
        return repo.getShopItemById(id)
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
    data class Success(val shopItems: List<ShopItem>) : ShopUseCaseResult()
    data class Error(val message: String) : ShopUseCaseResult()
}