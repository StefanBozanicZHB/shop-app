package com.zhbcompany.shop.presentation.shop_list.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import com.zhbcompany.shop.util.ShopListStrings
import com.zhbcompany.shop.domain.util.SortingDirection
import com.zhbcompany.shop.domain.util.ShopItemOrder

@Composable
fun SortingDrawerOptions(
    shopItemOrder: ShopItemOrder,
    onOrderChange: (ShopItemOrder) -> Unit
) {
    val titleSelected = shopItemOrder::class == ShopItemOrder.Title::class
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ShopListStrings.TITLE,
                isChecked = titleSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(
                ShopItemOrder.Title(shopItemOrder.sortingDirection)
            )
        }
    )

    val storeSelected = shopItemOrder::class == ShopItemOrder.Store::class
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ShopListStrings.STORE,
                isChecked = storeSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(
                ShopItemOrder.Store(shopItemOrder.sortingDirection)
            )
        }
    )

    val completedSelected = shopItemOrder::class == ShopItemOrder.Completed::class
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ShopListStrings.COMPLETED,
                isChecked = completedSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(
                ShopItemOrder.Completed(shopItemOrder.sortingDirection)
            )
        }
    )

    HorizontalDivider()

    val sortDownSelected = shopItemOrder.sortingDirection == SortingDirection.Down
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ShopListStrings.SORT_DOWN,
                isChecked = sortDownSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(
                shopItemOrder.copy(SortingDirection.Down)
            )
        }
    )

    val sortUpSelected = shopItemOrder.sortingDirection == SortingDirection.Up
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ShopListStrings.SORT_UP,
                isChecked = sortUpSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(
                shopItemOrder.copy(SortingDirection.Up)
            )
        }
    )
}