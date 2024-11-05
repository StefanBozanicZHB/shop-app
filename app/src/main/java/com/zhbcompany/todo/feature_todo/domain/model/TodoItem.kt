package com.zhbcompany.todo.feature_todo.domain.model

data class TodoItem(
    val id: Int,
    val title: String,
    val description: String,
    val timestamp: Long,
    val completed: Boolean,
    val archived: Boolean
)