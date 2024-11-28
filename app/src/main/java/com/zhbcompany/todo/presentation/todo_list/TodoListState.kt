package com.zhbcompany.todo.presentation.todo_list

import com.zhbcompany.todo.domain.model.TodoItem
import com.zhbcompany.todo.domain.util.SortingDirection
import com.zhbcompany.todo.domain.util.TodoItemOrder

data class TodoListState(
    val todoItems: List<TodoItem> = emptyList(),
    val todoItemOrder: TodoItemOrder = TodoItemOrder.Time(SortingDirection.Down, true),
    val isLoading: Boolean = true,
    val error: String? = null
)