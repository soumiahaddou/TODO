package com.apexascent.todo.data

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun getTodoByKey(key: Int): Todo?
    fun getTodos(): Flow<List<Todo>>

}