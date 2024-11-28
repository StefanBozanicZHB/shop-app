package com.zhbcompany.shop.presentation.shop_new_update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhbcompany.shop.util.NewUpdateStrings
import com.zhbcompany.shop.domain.use_case.ShopUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ShopNewUpdateViewModel(
    private val shopUseCases: ShopUseCases,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val savedStateHandle = SavedStateHandle() //todo ?
    private val _state = mutableStateOf(ShopNewUpdateState())
    val state: State<ShopNewUpdateState> = _state

    // todo ?
    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    private var currentShopId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveShop : UiEvent()
        object Back : UiEvent()
    }

    init {
        savedStateHandle.get<Int>("shopId")?.let { id ->
            if (id != -1) {
                viewModelScope.launch(dispatcher + errorHandler) {
                    shopUseCases.getShopItemById(id)?.also { shopItem ->
                        currentShopId = id
                        _state.value = _state.value.copy(
                            shopItem = shopItem,
                            isLoading = false,
                            isTitleHintVisible = shopItem.title.isBlank(),
                            isDescriptionHintVisible = shopItem.description.isBlank()
                        )
                    }
                }
            } else {
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }

        }
    }


    fun onEvent(event: ShopNewUpdateEvent) {
        when (event) {
            ShopNewUpdateEvent.Back -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    _eventFlow.emit(UiEvent.Back)
                }
            }

            is ShopNewUpdateEvent.ChangedDescriptionFocus -> {
                val shouldDescriptionHintBeVisible =
                    !event.focusState.isFocused && _state.value.shopItem.description.isBlank()
                _state.value = _state.value.copy(
                    isDescriptionHintVisible = shouldDescriptionHintBeVisible
                )
            }

            is ShopNewUpdateEvent.ChangedTitleFocus -> {
                val shouldTitleHintBeVisible =
                    !event.focusState.isFocused && _state.value.shopItem.title.isBlank()
                _state.value = _state.value.copy(
                    isTitleHintVisible = shouldTitleHintBeVisible
                )
            }

            ShopNewUpdateEvent.Delete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    if (currentShopId != null) {
                        shopUseCases.deleteShopItem(_state.value.shopItem)
                    }
                    _eventFlow.emit(UiEvent.Back)
                }
            }

            is ShopNewUpdateEvent.EnteredDescription -> {
                _state.value = _state.value.copy(
                    shopItem = _state.value.shopItem.copy(
                        description = event.value
                    )
                )
            }

            is ShopNewUpdateEvent.EnteredTitle -> {
                _state.value = _state.value.copy(
                    shopItem = _state.value.shopItem.copy(
                        title = event.value
                    )
                )
            }

            ShopNewUpdateEvent.SaveShop -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    try {
                        if (currentShopId != null) { // todo use enum instead
                            shopUseCases.updateShopItem(_state.value.shopItem)
                        } else {
                            shopUseCases.addShopItem(
                                _state.value.shopItem.copy(
                                    timestamp = System.currentTimeMillis(),
                                    id = null
                                )
                            )
                        }
                        _eventFlow.emit(UiEvent.SaveShop)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: NewUpdateStrings.SAVE_ERROR
                            )
                        )
                    }
                }
            }

            ShopNewUpdateEvent.ToggleArchived -> {
                _state.value = _state.value.copy(
                    shopItem = _state.value.shopItem.copy(
                        archived = !_state.value.shopItem.archived
                    )
                )
            }

            ShopNewUpdateEvent.ToggleCompleted -> {
                _state.value = _state.value.copy(
                    shopItem = _state.value.shopItem.copy(
                        completed = !_state.value.shopItem.completed
                    )
                )
            }
        }
    }


}