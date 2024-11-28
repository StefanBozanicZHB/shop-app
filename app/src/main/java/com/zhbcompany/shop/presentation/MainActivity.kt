package com.zhbcompany.shop.presentation

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
import com.zhbcompany.shop.presentation.shop_list.ShopListScreen
import com.zhbcompany.shop.presentation.shop_list.ShopListViewModel
import com.zhbcompany.shop.presentation.shop_new_update.ShopNewUpdateScreen
import com.zhbcompany.shop.presentation.shop_new_update.ShopNewUpdateViewModel
import com.zhbcompany.shop.presentation.util.Screen
import com.zhbcompany.shop.ui.theme.ShopTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val listViewModel : ShopListViewModel = getViewModel()
                    val newUpdateViewModel : ShopNewUpdateViewModel = getViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ShopItemListScreen.route
                    ) {
                        composable(route = Screen.ShopItemListScreen.route) {
                            ShopListScreen(
                                navController = navController,
                                viewModel = listViewModel
                            )
                        }

                        composable(
                            route = Screen.ShopNewUpdateScreen.route + "?shopId={shopId}",
                            arguments = listOf(
                                navArgument(
                                    name = "shopId",
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            ShopNewUpdateScreen(
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