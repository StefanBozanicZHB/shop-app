package com.zhbcompany.shop.domain.util

sealed class ShopItemOrder(
    val sortingDirection: SortingDirection,
    val showArchived: Boolean
) {
    class Title(sortingDirection: SortingDirection, showArchived: Boolean) :
        ShopItemOrder(sortingDirection, showArchived)

    class Time(sortingDirection: SortingDirection, showArchived: Boolean) :
        ShopItemOrder(sortingDirection, showArchived)

    class Completed(sortingDirection: SortingDirection, showArchived: Boolean) :
        ShopItemOrder(sortingDirection, showArchived)

    fun copy(sortingDirection: SortingDirection, showArchived: Boolean): ShopItemOrder {
        return when (this) {
            is Title -> Title(sortingDirection, showArchived)
            is Time -> Time(sortingDirection, showArchived)
            is Completed -> Completed(sortingDirection, showArchived)
        }
    }
}