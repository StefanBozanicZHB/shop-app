package com.zhbcompany.shop.presentation.shop_list

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhbcompany.shop.R
import com.zhbcompany.shop.domain.model.emptyShopItem
import com.zhbcompany.shop.presentation.shop_list.components.AddOrUpdateShopItemDialog
import com.zhbcompany.shop.presentation.shop_list.components.ShopItemCard
import com.zhbcompany.shop.presentation.shop_list.components.SortingDrawerOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(
    viewModel: ShopListViewModel
) {
    val state = viewModel.state.value
    val snackBarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isDialogVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getShopItems()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                ModalDrawerSheet {
                    Text(
                        text = stringResource(id = R.string.sort_by),
                        modifier = Modifier.padding(16.dp),
                        fontSize = 34.sp,
                        lineHeight = 38.sp
                    )
                    HorizontalDivider()
                    SortingDrawerOptions(
                        shopItemOrder = state.shopItemOrder,
                        onOrderChange = { order ->
                            viewModel.onEvent(ShopListEvent.Sort(order))
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.currentShopItem = emptyShopItem
                        isDialogVisible.value = true
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_shop_item),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        scrolledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.tertiaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    navigationIcon = {},
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = stringResource(id = R.string.sorting_menu)
                            )
                        }
                    },
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (!state.isLoading && state.shopItemDomains.isEmpty()) {
                            ShowText(stringResource(R.string.empty_screen))
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(all = 4.dp)
                            ) {
                                items(state.shopItemDomains) { item ->
                                    ShopItemCard(
                                        shopItemDomain = item,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp),
                                        onDeleteClick = {
                                            viewModel.onEvent(ShopListEvent.Delete(item))
                                            scope.launch {
                                                val undo = snackBarHostState.showSnackbar(
                                                    message = context.getString(R.string.shop_item_deleted),
                                                    actionLabel = context.getString(R.string.undo),
                                                    duration = SnackbarDuration.Short
                                                )
                                                if (undo == SnackbarResult.ActionPerformed) {
                                                    viewModel.onEvent(ShopListEvent.UndoDelete)
                                                    snackBarHostState.currentSnackbarData?.dismiss()
                                                }
                                            }
                                        },
                                        onCompleteClick = {
                                            viewModel.onEvent(ShopListEvent.ToggleCompleted(item))
                                        },
                                        onCardClick = {
                                            viewModel.currentShopItem = item
                                            isDialogVisible.value = true
                                        }
                                    )
                                }
                            }
                        }
                    }
                    if (state.isLoading) {
                        Loading(context)
                    }
                    if (state.error != null) {
                        ShowText(state.error)
                    }
                }
            }
        }

        AddOrUpdateShopItemDialog(
            isVisible = isDialogVisible.value,
            shopItemDomain = viewModel.currentShopItem,
            onDismiss = { isDialogVisible.value = false },
            onSave = { shopItem ->
                viewModel.saveShopItem(shopItem)
                Log.d("ZHB", shopItem.toString())
            }
        )
    }
}

@Composable
fun ShowText(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 30.sp,
            lineHeight = 36.sp
        )
    }
}

@Composable
fun Loading(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            Modifier.semantics {
                this.contentDescription = context.getString(R.string.loading_indicator)
            }
        )
    }
}