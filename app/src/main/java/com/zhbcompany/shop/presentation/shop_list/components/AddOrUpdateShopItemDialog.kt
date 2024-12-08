package com.zhbcompany.shop.presentation.shop_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhbcompany.shop.R
import com.zhbcompany.shop.domain.model.ShopItemDomain
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdateShopItemDialog(
    isVisible: Boolean,
    shopItemDomain: ShopItemDomain,
    onDismiss: () -> Unit,
    onSave: (shopItem: ShopItemDomain) -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss
        ) {
            val focusRequester = remember { FocusRequester() }

            val isTitleError = remember { mutableStateOf(false) }
            val title = remember {
                mutableStateOf(TextFieldValue(shopItemDomain.title, TextRange(shopItemDomain.title.length)))
            }
            val description = remember {
                mutableStateOf(TextFieldValue(shopItemDomain.description, TextRange(shopItemDomain.description.length)))
            }
            val store = remember {
                mutableStateOf(TextFieldValue(shopItemDomain.store, TextRange(shopItemDomain.store.length)))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    valueState = title.value,
                    labelId = stringResource(id = R.string.title),
                    enabled = true,
                    isSingleLine = true,
                    icon = Icons.Rounded.ShoppingCart
                ) {
                    title.value = it
                    isTitleError.value = it.text.isBlank()
                }

                if (isTitleError.value) {
                    Text(
                        text = stringResource(id = R.string.title_is_required),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    valueState = description.value,
                    labelId = stringResource(id = R.string.description),
                    enabled = true,
                    isSingleLine = false,
                    icon = Icons.Rounded.Info
                ) {
                    description.value = it
                }

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    valueState = store.value,
                    labelId = stringResource(id = R.string.store),
                    enabled = true,
                    isSingleLine = true,
                    icon = Icons.Rounded.LocationOn,
                    imeAction = ImeAction.Done,
                    onAction = KeyboardActions(
                        onDone = {
                            if (title.value.text.isBlank()) {
                                isTitleError.value = true
                            } else {
                                onSave(
                                    shopItemDomain.copy(
                                        title = title.value.text,
                                        description = description.value.text,
                                        store = store.value.text
                                    )
                                )
                                onDismiss()
                            }
                        }
                    )
                ) {
                    store.value = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(id = R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (title.value.text.isBlank()) {
                                isTitleError.value = true
                            } else {
                                onSave(
                                    shopItemDomain.copy(
                                        title = title.value.text,
                                        description = description.value.text,
                                        store = store.value.text
                                    )
                                )
                                onDismiss()
                            }
                        }
                    ) {
                        Text(stringResource(id = R.string.save))
                    }
                }
            }

            LaunchedEffect(true) {
                delay(100)
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: TextFieldValue,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    icon: ImageVector,
    onValueChange: (TextFieldValue) -> Unit,
) {
    OutlinedTextField(
        value = valueState,
        onValueChange = onValueChange,
        label = { Text(text = labelId) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "icon"
            )
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}