package com.zhbcompany.shop.presentation.shop_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhbcompany.shop.domain.model.ShopItemDomain
import com.zhbcompany.shop.domain.model.emptyShopItem
import com.zhbcompany.shop.domain.use_case.ShopUseCaseResult
import com.zhbcompany.shop.domain.use_case.ShopUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShopListViewModel(
    private val shopUseCases: ShopUseCases,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    // todo use state or flow
    private val _state = mutableStateOf(ShopListState())
    val state: State<ShopListState> = _state

    private var undoShopItemDomain: ShopItemDomain? = null

    private var getShopItemsJob: Job? = null

    var currentShopItem: ShopItemDomain = emptyShopItem

    // todo how to use CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    fun onEvent(event: ShopListEvent) {
        when (event) {
            is ShopListEvent.Delete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    shopUseCases.deleteShopItem(event.item)
                    getShopItems()
                    undoShopItemDomain = event.item
                }
            }

            is ShopListEvent.Sort -> {
                val stateOrderAlreadyMatchedEventOrder =
                    _state.value.shopItemOrder::class == event.shopItemOrder::class &&
                            _state.value.shopItemOrder.sortingDirection == event.shopItemOrder.sortingDirection

                if (stateOrderAlreadyMatchedEventOrder) {
                    return
                }

                _state.value = _state.value.copy(
                    shopItemOrder = event.shopItemOrder
                )

                getShopItems()
            }

            is ShopListEvent.ToggleCompleted -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    shopUseCases.toggleCompleteShopItem(shopItemDomain = event.item)
                    getShopItems()
                }
            }

            ShopListEvent.UndoDelete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    shopUseCases.addShopItem(undoShopItemDomain ?: return@launch)
                    undoShopItemDomain = null
                    getShopItems()
                }
            }
        }
    }

    fun getShopItems() {
        getShopItemsJob?.cancel()

        getShopItemsJob = viewModelScope.launch(dispatcher + errorHandler) {
            val result = shopUseCases.getShopItems(
                shopItemOrder = _state.value.shopItemOrder
            )
            when (result) {
                is ShopUseCaseResult.Success -> {
                    _state.value = _state.value.copy(
                        shopItemDomains = result.shopItemDomains,
                        shopItemOrder = _state.value.shopItemOrder,
                        isLoading = false
                    )
                }

                is ShopUseCaseResult.Error -> {
                    _state.value = _state.value.copy(
                        error = "Error: ${result.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun saveShopItem(shopItem: ShopItemDomain) {
        viewModelScope.launch(dispatcher + errorHandler) {
            try {
                if (shopItem.id != null) {
                    shopUseCases.updateShopItem(shopItem)
                } else {
                    shopUseCases.addShopItem(shopItem)
                }
                getShopItems()
//                _eventFlow.emit(UiEvent.SaveShop)
            } catch (e: Exception) {
//                _eventFlow.emit(
//                    UiEvent.ShowSnackbar(
//                        message = e.message ?: NewUpdateStrings.SAVE_ERROR
//                    )
//                )
            }
        }
    }
}