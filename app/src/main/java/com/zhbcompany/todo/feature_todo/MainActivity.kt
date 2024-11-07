package com.zhbcompany.todo.feature_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhbcompany.todo.feature_todo.presentation.todo_list.TodoListScreen
import com.zhbcompany.todo.feature_todo.presentation.todo_list.TodoListViewModel
import com.zhbcompany.todo.feature_todo.presentation.util.Screen
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

                        composable(route = Screen.TodoItemUpdateScreen.route) {
                            // todo
                        }
                    }
                }
            }
        }
    }
}