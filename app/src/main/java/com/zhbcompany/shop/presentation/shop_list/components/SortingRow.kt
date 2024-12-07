package com.zhbcompany.shop.presentation.shop_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.zhbcompany.shop.R

@Composable
fun IconRow(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            lineHeight = 30.sp
        )
        if (isChecked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(id = R.string.selected),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }

}