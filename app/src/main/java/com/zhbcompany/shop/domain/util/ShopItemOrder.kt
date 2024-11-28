package com.zhbcompany.shop.domain.util

sealed class ShopItemOrder(
    val sortingDirection: SortingDirection,
) {
    class Title(sortingDirection: SortingDirection) : ShopItemOrder(sortingDirection)

    class Store(sortingDirection: SortingDirection) : ShopItemOrder(sortingDirection)

    class Completed(sortingDirection: SortingDirection) : ShopItemOrder(sortingDirection)

    fun copy(sortingDirection: SortingDirection): ShopItemOrder {
        return when (this) {
            is Title -> Title(sortingDirection)
            is Store -> Store(sortingDirection)
            is Completed -> Completed(sortingDirection)
        }
    }
}