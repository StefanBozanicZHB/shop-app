package com.zhbcompany.shop.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zhbcompany.shop.domain.model.ShopItem

data class ShopItemColors(
    val backgroundColor: Color,
    val textColor: Color,
    val archiveIconColor: Color,
    val checkColor: Color
)

@Composable
fun getShopColors(shopItem: ShopItem): ShopItemColors {
    return ShopItemColors(
        backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
        textColor = MaterialTheme.colorScheme.onSecondary,
        archiveIconColor = MaterialTheme.colorScheme.onSecondary,
        checkColor = if (shopItem.completed) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.onSecondary
    )
}