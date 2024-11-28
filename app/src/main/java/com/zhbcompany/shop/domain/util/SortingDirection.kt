package com.zhbcompany.shop.domain.util

sealed class SortingDirection {
    object Up : SortingDirection()
    object Down : SortingDirection()
}