package com.apexascent.todo.add_edit_todo

sealed class AddEditTodoEvent {
    data class onTitleChanged(val title: String): AddEditTodoEvent()
    data class onDescriptionChanged (val description: String): AddEditTodoEvent()
    object onSaveTodoClicked: AddEditTodoEvent()


}