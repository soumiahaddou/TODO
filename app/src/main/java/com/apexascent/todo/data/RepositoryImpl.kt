package com.apexascent.todo.data

import kotlinx.coroutines.flow.Flow

class RepositoryImpl(val dao: TodoDAO): Repository {
    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoByKey(key: Int): Todo? {
        return dao.getTodoByKey(key)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }
}