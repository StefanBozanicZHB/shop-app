package com.zhbcompany.todo.domain.util

sealed class SortingDirection {
    object Up : SortingDirection()
    object Down : SortingDirection()
}