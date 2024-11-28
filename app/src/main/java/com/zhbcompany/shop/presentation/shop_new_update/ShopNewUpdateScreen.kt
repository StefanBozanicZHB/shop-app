package com.zhbcompany.shop.presentation.shop_new_update

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhbcompany.shop.presentation.components.CompleteButton
import com.zhbcompany.shop.presentation.components.DeleteButton
import com.zhbcompany.shop.presentation.components.getShopColors
import com.zhbcompany.shop.util.ContentDescription
import com.zhbcompany.shop.util.NewUpdateStrings
import com.zhbcompany.shop.util.ShopListStrings
import com.zhbcompany.shop.presentation.shop_new_update.components.HintTextField
import com.zhbcompany.shop.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopNewUpdateScreen(
    navController: NavController,
    viewModel: ShopNewUpdateViewModel
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val shopItemColors = getShopColors(shopItem = state.shopItem)

    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val topBarHeight = if (isPortrait) 64.dp else 0.dp
    val horizontalPadding = 16.dp
    val verticalPadding = if (isPortrait) 16.dp else 2.dp

    val backgroundImage = if (isPortrait) {
        R.drawable.background_portrait
    } else {
        R.drawable.background_landscape
    }

    // todo check this
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                ShopNewUpdateViewModel.UiEvent.Back -> {
                    navController.navigateUp()
                }

                ShopNewUpdateViewModel.UiEvent.SaveShop -> {
                    navController.navigateUp()
                }

                is ShopNewUpdateViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!isPortrait) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(ShopNewUpdateEvent.SaveShop)
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, // Todo use Save icon
                        contentDescription = ContentDescription.SAVE_SHOP_ITEM,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        topBar = {
            if (isPortrait) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = ShopListStrings.SHOP_LIST,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    // todo check this
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        scrolledContainerColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ShopNewUpdateEvent.Back)
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = ContentDescription.BACK,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {},
                    //todo check this
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )

            }
        },
        bottomBar = {
            if (isPortrait) {
                BottomAppBar(
                    actions = {
                        CompleteButton(
                            onCompleteClick = {
                                viewModel.onEvent(ShopNewUpdateEvent.ToggleCompleted)
                            },
                            color = shopItemColors.checkColor,
                            completed = state.shopItem.completed
                        )
                        DeleteButton(
                            onDeleteClick = {
                                scope.launch {
                                    val confirm = snackbarHostState.showSnackbar(
                                        message = NewUpdateStrings.CONFIRM_DELETE,
                                        actionLabel = NewUpdateStrings.YES
                                    )
                                    if (confirm == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(ShopNewUpdateEvent.Delete)
                                    }
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                viewModel.onEvent(ShopNewUpdateEvent.SaveShop)
                            },
                            shape = CircleShape,
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add, //todo Save icon
                                contentDescription = ContentDescription.SAVE_SHOP_ITEM,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = shopItemColors.backgroundColor)
            ) {
                // todo check this when keyboard is open
                // todo what is the best way to use for background
                Image(
                    painter = painterResource(id = backgroundImage),
                    contentDescription = ContentDescription.BACKGROUND_IMAGE,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.TopStart
                )
                Column(
                    modifier = Modifier
                        .padding(top = topBarHeight)
                        .fillMaxSize()
                ) {
                    HintTextField(
                        text = state.shopItem.title,
                        hint = NewUpdateStrings.TITLE_HINT,
                        textColor = shopItemColors.textColor,
                        onValueChange = {
                            viewModel.onEvent(ShopNewUpdateEvent.EnteredTitle(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(ShopNewUpdateEvent.ChangedTitleFocus(it))
                        },
                        isHintVisible = state.isTitleHintVisible,
                        singleLine = true,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(
                            horizontal = horizontalPadding,
                            vertical = verticalPadding
                        )
                    )
                    Spacer(modifier = Modifier.height(verticalPadding))
                    HintTextField(
                        text = state.shopItem.description,
                        hint = NewUpdateStrings.DESCRIPTION_HINT,
                        textColor = shopItemColors.textColor,
                        onValueChange = {
                            viewModel.onEvent(ShopNewUpdateEvent.EnteredDescription(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(ShopNewUpdateEvent.ChangedDescriptionFocus(it))
                        },
                        isHintVisible = state.isDescriptionHintVisible,
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                horizontal = horizontalPadding,
                                vertical = verticalPadding
                            )
                    )
                }
            }
        }
    }
}