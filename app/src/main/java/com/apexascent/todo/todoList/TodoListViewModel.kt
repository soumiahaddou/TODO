package com.apexascent.todo.todoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apexascent.todo.data.Repository
import com.apexascent.todo.data.Todo
import com.apexascent.todo.util.Routes
import com.apexascent.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TodoListViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val todos = repository.getTodos()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var deletedTodo: Todo? = null
    fun onEvent(event: TodoListEvent){
        when(event){
            TodoListEvent.onAddTodoClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
                }
            }
            is TodoListEvent.onDeleteTodo -> {
                viewModelScope.launch{
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)

                }
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.ShowSnackBar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }
            is TodoListEvent.onDoneChanged -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is TodoListEvent.onTodoClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Routes.ADD_EDIT_TODO+"?todoId=${event.todo.key}"
                    ))
                }
            }
            TodoListEvent.onUndoDeletion -> {
                deletedTodo?.let {todo->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }

                }
            }
        }
    }
}