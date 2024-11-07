package com.zhbcompany.todo.feature_todo.presentation.util

sealed class Screen(val route: String) {
    object TodoItemListScreen: Screen("todoItemList_screen")
    object TodoItemUpdateScreen: Screen("todoNewUpdate_screen")
}