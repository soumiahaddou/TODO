package com.apexascent.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val key: Int? = null,
    val title: String,
    val description: String?,
    val isDone: Boolean

)
