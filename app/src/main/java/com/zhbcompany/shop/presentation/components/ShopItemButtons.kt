package com.zhbcompany.shop.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.zhbcompany.shop.util.ContentDescription

@Composable
fun CompleteButton(
    onCompleteClick: () -> Unit,
    color: Color,
    completed: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onCompleteClick,
        modifier = modifier
    ) {
        if (completed) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = ContentDescription.COMPLETE_SHOP_ITEM,
                tint = color,
                modifier = Modifier.size(48.dp)
            )
        } else {
            EmptyCircle(color = color)
        }
    }

}

@Composable
fun EmptyCircle(color: Color, strokeWidth: Float = 9f) {
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val radius = 39.0F
            drawCircle(
                color,
                center = center,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )
        }
    )
}

@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.secondary,
) {
    IconButton(
        onClick = onDeleteClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = ContentDescription.DELETE_SHOP_ITEM,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(32.dp)
        )
    }
}