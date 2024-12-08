package com.zhbcompany.shop.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zhbcompany.shop.domain.model.ShopItemDomain

data class ShopItemColors(
    val backgroundColor: Color,
    val textColor: Color,
    val archiveIconColor: Color,
    val checkColor: Color,
    val deleteColor: Color,
)

@Composable
fun getShopColors(shopItemDomain: ShopItemDomain): ShopItemColors {
    return ShopItemColors(
        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
        textColor = MaterialTheme.colorScheme.onTertiaryContainer,
        archiveIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
        checkColor = if (shopItemDomain.completed) MaterialTheme.colorScheme.onTertiaryContainer
        else MaterialTheme.colorScheme.onTertiaryContainer,
        deleteColor = MaterialTheme.colorScheme.onTertiaryContainer
    )
}