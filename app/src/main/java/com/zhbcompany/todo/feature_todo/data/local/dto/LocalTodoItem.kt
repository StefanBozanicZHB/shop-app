package com.zhbcompany.todo.feature_todo.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class LocalTodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val timestamp: Long,
    val completed: Boolean,
    val archived: Boolean
)