package com.apexascent.todo.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apexascent.todo.util.UiEvent

@Composable
fun addEditTodo(
    onPopBackStack:()->Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()

){
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event->
            when(event){
                UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }

        }
    }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(AddEditTodoEvent.onSaveTodoClicked) }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
        }
    }) {padding->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(padding)){
            TextField(value = viewModel.title, onValueChange = {
                viewModel.onEvent(AddEditTodoEvent.onTitleChanged(it))
            }, placeholder = { Text(text = "Title")}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = viewModel.description, onValueChange = {
                viewModel.onEvent(AddEditTodoEvent.onDescriptionChanged(it))
            }, placeholder = { Text(text = "Description")}, modifier = Modifier.fillMaxWidth(), singleLine = false, maxLines = 5)
        }

    }



}