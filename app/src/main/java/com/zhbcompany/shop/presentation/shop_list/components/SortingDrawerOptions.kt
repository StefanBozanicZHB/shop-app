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
                ShopItemOrder.Title(
                    shopItemOrder.sortingDirection,
                    shopItemOrder.showArchived
                )
            )
        }
    )

    val timeSelected = shopItemOrder::class == ShopItemOrder.Time::class
    NavigationDrawerItem(
        label = {
            IconRow(
                text = ShopListStrings.TIME,
                isChecked = timeSelected
            )
        },
        selected = false,
        onClick = {
            onOrderChange(
                ShopItemOrder.Time(
                    shopItemOrder.sortingDirection,
                    shopItemOrder.showArchived
                )
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
                ShopItemOrder.Completed(
                    shopItemOrder.sortingDirection,
                    shopItemOrder.showArchived
                )
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
                shopItemOrder.copy(SortingDirection.Down, shopItemOrder.showArchived)
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
                shopItemOrder.copy(SortingDirection.Up, shopItemOrder.showArchived)
            )
        }
    )

    HorizontalDivider()

    NavigationDrawerItem(
        label = {
            IconRow(
                text = ShopListStrings.SHOW_ARCHIVED,
                isChecked = shopItemOrder.showArchived
            )
        },
        selected = false,
        onClick = {
            onOrderChange(
                shopItemOrder.copy(shopItemOrder.sortingDirection, !shopItemOrder.showArchived)
            )
        }
    )
}