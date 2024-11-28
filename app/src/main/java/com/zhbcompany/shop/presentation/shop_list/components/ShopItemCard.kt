package com.zhbcompany.shop.presentation.shop_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.zhbcompany.shop.domain.model.ShopItem
import com.zhbcompany.shop.presentation.components.CompleteButton
import com.zhbcompany.shop.presentation.components.DeleteButton
import com.zhbcompany.shop.presentation.components.getShopColors
import com.zhbcompany.shop.ui.theme.ShopTheme

@Composable
fun ShopItemCard(
    modifier: Modifier = Modifier,
    shopItem: ShopItem,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    val shopItemColors = getShopColors(shopItem = shopItem)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        onClick = onCardClick,
        colors = CardDefaults.cardColors(containerColor = shopItemColors.backgroundColor)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompleteButton(onCompleteClick, shopItemColors.checkColor, shopItem.completed)
            Text(
                text = shopItem.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = shopItemColors.textColor,
                fontSize = 32.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = shopItem.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = shopItemColors.textColor,
                    fontSize = 24.sp,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
                    .padding(end = 4.dp)
            ) {
                DeleteButton(onDeleteClick = onDeleteClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemCardPreview() {
    ShopTheme {
        ShopItemCard(
            shopItem = ShopItem(
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