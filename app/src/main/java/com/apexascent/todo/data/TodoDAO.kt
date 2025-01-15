package com.apexascent.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface TodoDAO {
    @Upsert
    suspend fun insertTodo(todo: Todo)
    @Delete
    suspend fun deleteTodo(todo: Todo)
    @Query("SELECT * FROM Todo WHERE 'key' =:key" )
    suspend fun getTodoByKey(key: Int): Todo?
    @Query("SELECT * FROM Todo")
    fun getTodos():Flow<List<Todo>>
}