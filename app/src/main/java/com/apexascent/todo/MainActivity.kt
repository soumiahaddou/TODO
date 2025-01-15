package com.apexascent.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.apexascent.todo.add_edit_todo.addEditTodo
import com.apexascent.todo.todoList.todoListScreen
import com.apexascent.todo.ui.theme.TODOTheme
import com.apexascent.todo.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.TODO_LIST ){
                        composable(route = Routes.TODO_LIST){
                            todoListScreen(onNavigate = {
                                navController.navigate(it.route)
                            })
                        }
                        composable(route = Routes.ADD_EDIT_TODO+"?todoId={todoId}", arguments = (
                            listOf(
                                navArgument(
                                    name= "todoId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        )){

                            addEditTodo(onPopBackStack = { navController.popBackStack()},


                                )


                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TODOTheme {
        Greeting("Android")
    }
}