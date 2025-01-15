package com.apexascent.todo.todoList

import com.apexascent.todo.data.Todo

sealed class TodoListEvent {
    data class onDeleteTodo (val todo: Todo): TodoListEvent()
    data class onDoneChanged(val todo: Todo, val isDone:Boolean): TodoListEvent()
    data class onTodoClicked(val todo: Todo): TodoListEvent()
    object onAddTodoClicked: TodoListEvent()
    object onUndoDeletion: TodoListEvent()
}