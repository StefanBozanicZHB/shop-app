package com.zhbcompany.todo.data.mapper

import com.zhbcompany.todo.data.local.dto.LocalTodoItem
import com.zhbcompany.todo.data.remote.dto.RemoteTodoItem
import com.zhbcompany.todo.domain.model.TodoItem

fun TodoItem.toLocalTodoItem(): LocalTodoItem {
    return LocalTodoItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun TodoItem.toRemoteTodoItem(): RemoteTodoItem {
    return RemoteTodoItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun LocalTodoItem.toTodoItem(): TodoItem {
    return TodoItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun LocalTodoItem.toRemoteTodoItem(): RemoteTodoItem {
    return RemoteTodoItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun RemoteTodoItem.toTodoItem(): TodoItem {
    return TodoItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun RemoteTodoItem.toLocalTodoItem(): LocalTodoItem {
    return LocalTodoItem (
        title = title,
        description = description,
        timestamp = timestamp,
        archived = archived,
        id = id,
        completed = completed
    )
}

fun List<TodoItem>.toLocalTodoItems(): List<LocalTodoItem> {
    return this.map { it.toLocalTodoItem() }
}

fun List<TodoItem>.toRemoteTodoItems(): List<RemoteTodoItem> {
    return this.map { it.toRemoteTodoItem() }
}

fun List<LocalTodoItem>.toRemoteTodoItemsFromLocal(): List<RemoteTodoItem> {
    return this.map { it.toRemoteTodoItem() }
}

fun List<LocalTodoItem>.toTodoItemsFromLocal(): List<TodoItem> {
    return this.map { it.toTodoItem() }
}

fun List<RemoteTodoItem>.toTodoItemsFromRemote(): List<TodoItem> {
    return this.map { it.toTodoItem() }
}

fun List<RemoteTodoItem>.toLocalTodoItemsFromRemote(): List<LocalTodoItem> {
    return this.map { it.toLocalTodoItem() }
}