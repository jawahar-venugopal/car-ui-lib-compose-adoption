/*
 * Copyright (C) 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is a Kotlin Compose port/adaptation of AOSP car-ui-lib code:
 * https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-ui-lib
 */
package com.android.car.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp

@Composable
fun CarUiAlertDialog(
    params: CarUiAlertDialogParams
) {
    if (!params.show) return

    val minDialogWidth = dimensionResource(id = R.dimen.car_ui_alert_dialog_min_width)
    val dialogPadding = dimensionResource(id = R.dimen.car_ui_alert_dialog_padding)
    val iconSize = dimensionResource(id = R.dimen.car_ui_alert_dialog_icon_size)
    val buttonSpacing = dimensionResource(id = R.dimen.car_ui_alert_dialog_button_spacing)

    AlertDialog(
        onDismissRequest = { params.onDismiss?.invoke() },
        buttons = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dialogPadding, vertical = buttonSpacing),
                horizontalArrangement = Arrangement.End
            ) {
                params.neutralButton?.let {
                    TextButton(onClick = { params.onNeutralClick?.invoke() }) {
                        Text(it)
                    }
                    Spacer(modifier = Modifier.width(buttonSpacing))
                }
                params.negativeButton?.let {
                    TextButton(onClick = { params.onNegativeClick?.invoke() }) {
                        Text(it)
                    }
                    Spacer(modifier = Modifier.width(buttonSpacing))
                }
                params.positiveButton?.let {
                    TextButton(onClick = { params.onPositiveClick?.invoke() }) {
                        Text(it)
                    }
                }
            }
        },
        title = {
            Column(Modifier.fillMaxWidth()) {
                params.icon?.let {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.size(iconSize)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                params.title?.let {
                    Text(text = it, style = MaterialTheme.typography.h6)
                }
                params.subtitle?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = it, style = MaterialTheme.typography.subtitle2)
                }
            }
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                // Custom content slot
                if (params.content != null) {
                    params.content.invoke(this)
                } else {
                    params.message?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // EditText (prompt, etc.)
                    params.editTextOnValueChange?.let { onValueChange ->
                        var value by remember { mutableStateOf(params.editTextValue ?: "") }
                        BasicTextField(
                            value = value,
                            onValueChange = {
                                // Input filters (if any)
                                var filtered = it
                                params.editTextInputFilters?.forEach { filter ->
                                    val spanned = android.text.SpannableStringBuilder(filtered)
                                    filter.filter(filtered, 0, filtered.length, spanned, 0, 0)
                                        ?.let { result ->
                                            filtered = result.toString()
                                        }
                                }
                                value = filtered
                                onValueChange(filtered)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = params.editTextKeyboardType
                            ),
                            visualTransformation = params.editTextVisualTransformation,
                            decorationBox = { innerTextField ->
                                if (value.isEmpty() && !params.editTextPrompt.isNullOrEmpty()) {
                                    Text(
                                        text = params.editTextPrompt,
                                        style = MaterialTheme.typography.body2,
                                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }

                    // Single-choice
                    params.singleChoiceItems?.let { items ->
                        val selectedIndex = params.singleChoiceSelectedIndex ?: -1
                        Column {
                            items.forEachIndexed { index, item ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = index == selectedIndex,
                                        onClick = { params.onSingleChoiceSelect?.invoke(index) }
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(item)
                                }
                            }
                        }
                    }

                    // Multi-choice
                    params.multiChoiceItems?.let { items ->
                        val checkedStates = params.multiChoiceChecked ?: List(items.size) { false }
                        Column {
                            items.forEachIndexed { index, item ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Checkbox(
                                        checked = checkedStates[index],
                                        onCheckedChange = { checked ->
                                            params.onMultiChoiceChange?.invoke(index, checked)
                                        }
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(item)
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier = Modifier.widthIn(min = minDialogWidth)
    )
}