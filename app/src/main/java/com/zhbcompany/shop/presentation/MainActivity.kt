package com.zhbcompany.shop.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.zhbcompany.shop.presentation.shop_list.ShopListScreen
import com.zhbcompany.shop.presentation.shop_list.ShopListViewModel
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
                    val listViewModel : ShopListViewModel = getViewModel()
                    ShopListScreen(viewModel = listViewModel)
                }
            }
        }
    }
}