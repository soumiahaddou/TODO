package com.apexascent.todo.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apexascent.todo.data.Repository
import com.apexascent.todo.data.Todo
import com.apexascent.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {


        val todoKey: Int = checkNotNull(savedStateHandle["todoId"])
        if (todoKey!=-1){
            viewModelScope.launch {

                repository.getTodoByKey(todoKey)?.let { todo->
                    title = todo.title
                    description = todo.description ?: ""

                    this@AddEditTodoViewModel.todo = todo

                }
            }
        }

    }

    fun onEvent(event: AddEditTodoEvent){
        when(event){
            is AddEditTodoEvent.onDescriptionChanged -> {
                description = event.description
            }
            AddEditTodoEvent.onSaveTodoClicked -> {
                viewModelScope.launch {
                    if (title.isBlank()){
                        _uiEvent.send(UiEvent.ShowSnackBar(message = "The title can't be empty"))
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(title = title, description = description, isDone = todo?.isDone ?:false, key = todo?.key)
                    )
                    _uiEvent.send(UiEvent.PopBackStack)
                }
            }
            is AddEditTodoEvent.onTitleChanged -> {
                title = event.title
            }

        }
    }

}