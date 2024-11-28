package com.zhbcompany.shop.domain.use_case

import com.zhbcompany.shop.util.ShopUseCasesStrings
import com.zhbcompany.shop.domain.model.ShopItemDomain
import com.zhbcompany.shop.domain.repo.ShopRepository
import com.zhbcompany.shop.domain.util.InvalidShopItemException
import com.zhbcompany.shop.domain.util.SortingDirection
import com.zhbcompany.shop.domain.util.ShopItemOrder

// todo separate in separate class
class ShopUseCases(
    private val repo: ShopRepository
) {
    suspend fun addShopItem(shopItemDomain: ShopItemDomain) {
        if (shopItemDomain.title.isBlank() || shopItemDomain.description.isBlank()) {
            throw InvalidShopItemException(ShopUseCasesStrings.EMPTY_TITLE_OR_DESCRIPTION)
        }
        repo.addShopItem(shopItemDomain)
    }

    suspend fun updateShopItem(shopItemDomain: ShopItemDomain) {
        if (shopItemDomain.title.isBlank() || shopItemDomain.description.isBlank()) {
            throw InvalidShopItemException(ShopUseCasesStrings.EMPTY_TITLE_OR_DESCRIPTION)
        }
        repo.updateShopItem(shopItemDomain)
    }

    suspend fun deleteShopItem(shopItemDomain: ShopItemDomain) {
        repo.deleteShopItem(shopItemDomain)
    }

    suspend fun toggleCompleteShopItem(shopItemDomain: ShopItemDomain) {
        repo.updateShopItem(shopItemDomain.copy(completed = !shopItemDomain.completed))
    }

    suspend fun getShopItemById(id: Int): ShopItemDomain? {
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
    data class Success(val shopItemDomains: List<ShopItemDomain>) : ShopUseCaseResult()
    data class Error(val message: String) : ShopUseCaseResult()
}