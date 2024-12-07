package com.zhbcompany.shop.presentation.shop_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhbcompany.shop.domain.model.ShopItemDomain

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
            val title = remember { mutableStateOf(shopItemDomain.title) }
            val description = remember { mutableStateOf(shopItemDomain.description) }
            val store = remember { mutableStateOf(shopItemDomain.store) }
            val isTitleError = remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = if (shopItemDomain.id == null) "Add Model" else "Update Model",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
                        isTitleError.value = it.isBlank()
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (isTitleError.value) {
                    Text(
                        text = "Title is required",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = store.value,
                    onValueChange = { store.value = it },
                    label = { Text("Store") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if(title.value.isBlank()) {
                                isTitleError.value = true
                            } else {
                                onSave(
                                    shopItemDomain.copy(
                                        title = title.value,
                                        description = description.value,
                                        store = store.value
                                    )
                                )
                                onDismiss()
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}