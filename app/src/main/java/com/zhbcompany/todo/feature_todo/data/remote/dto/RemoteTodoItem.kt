package com.zhbcompany.todo.feature_todo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RemoteTodoItem(
    @SerializedName("ID")
    val id: Int,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("Timestamp")
    val timestamp: Long,

    @SerializedName("Completed")
    val completed: Boolean,

    @SerializedName("Archived")
    val archived: Boolean
)