package com.zhbcompany.todo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zhbcompany.todo.presentation.todo_list.TodoListScreen
import com.zhbcompany.todo.presentation.todo_list.TodoListViewModel
import com.zhbcompany.todo.presentation.todo_new_update.TodoNewUpdateScreen
import com.zhbcompany.todo.presentation.todo_new_update.TodoNewUpdateViewModel
import com.zhbcompany.todo.presentation.util.Screen
import com.zhbcompany.todo.ui.theme.TodoTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val listViewModel : TodoListViewModel = getViewModel()
                    val newUpdateViewModel : TodoNewUpdateViewModel = getViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.TodoItemListScreen.route
                    ) {
                        composable(route = Screen.TodoItemListScreen.route) {
                            TodoListScreen(
                                navController = navController,
                                viewModel = listViewModel
                            )
                        }

                        composable(
                            route = Screen.TodoNewUpdateScreen.route + "?todoId={todoId}",
                            arguments = listOf(
                                navArgument(
                                    name = "todoId",
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            TodoNewUpdateScreen(
                                navController = navController,
                                viewModel = newUpdateViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}