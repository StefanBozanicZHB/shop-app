package com.zhbcompany.shop.presentation.shop_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhbcompany.shop.domain.model.ShopItemDomain
import com.zhbcompany.shop.presentation.components.CompleteButton
import com.zhbcompany.shop.presentation.components.DeleteButton
import com.zhbcompany.shop.presentation.components.getShopColors
import com.zhbcompany.shop.ui.theme.ShopTheme

@Composable
fun ShopItemCard(
    modifier: Modifier = Modifier,
    shopItemDomain: ShopItemDomain,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    val shopItemColors = getShopColors(shopItemDomain = shopItemDomain)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onCardClick,
        colors = CardDefaults.cardColors(containerColor = shopItemColors.backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            CompleteButton(onCompleteClick, shopItemColors.checkColor, shopItemDomain.completed)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = shopItemDomain.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = shopItemColors.textColor,
                    fontSize = 28.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (shopItemDomain.description.isNotBlank()) {
                    Text(
                        text = shopItemDomain.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = shopItemColors.textColor,
                        fontSize = 18.sp,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (shopItemDomain.store.isNotBlank()) {
                    Text(
                        text = shopItemDomain.store,
                        style = MaterialTheme.typography.bodyLarge,
                        color = shopItemColors.textColor,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            DeleteButton(onDeleteClick = onDeleteClick, color = shopItemColors.deleteColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemCardPreview() {
    ShopTheme {
        ShopItemCard(
            shopItemDomain = ShopItemDomain(
                title = "Subscribe to my channel & like this video ",
                description = "Keep learning Kotlin so that you can learn how to make really cool apps",
                store = "Store 01",
                completed = true,
                id = 1
            ),
            onDeleteClick = {},
            onCompleteClick = {},
            onCardClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemCardPreview2() {
    ShopTheme {
        ShopItemCard(
            shopItemDomain = ShopItemDomain(
                title = "Subscribe to my channel & like this video ",
                description = "",
                store = "Store 01",
                completed = true,
                id = 1
            ),
            onDeleteClick = {},
            onCompleteClick = {},
            onCardClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemCardPreview3() {
    ShopTheme {
        ShopItemCard(
            shopItemDomain = ShopItemDomain(
                title = "Subscribe to my channel & like this video ",
                description = "",
                store = "",
                completed = false,
                id = 1
            ),
            onDeleteClick = {},
            onCompleteClick = {},
            onCardClick = {},
        )
    }
}