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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.android.car.ui.recyclerview.CarUiCheckBoxListItem
import com.android.car.ui.recyclerview.CarUiRadioButtonListItem
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.toolbar.CarUiEditText

@Composable
fun CarUiAlertDialog(
    params: CarUiAlertDialogParams
) {
    if (!params.show) return
    val dialogPadding = dimensionResource(id = R.dimen.car_ui_alert_dialog_padding)
    val iconSize = dimensionResource(id = R.dimen.car_ui_alert_dialog_icon_size)
    val buttonSpacing = dimensionResource(id = R.dimen.car_ui_alert_dialog_button_spacing)

    AlertDialog(
        backgroundColor = colorResource(R.color.car_ui_alert_dialog_bg_color),
        contentColor = MaterialTheme.colors.onSurface,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        ),
        onDismissRequest = { params.onDismiss?.invoke() },
        modifier = Modifier.width(760.dp),
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
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, bottom = 18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    params.icon?.let {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(iconSize)
                                .align(alignment = Alignment.CenterVertically),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Column(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = dimensionResource(R.dimen.car_ui_padding_5))
                    ) {
                        params.title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                        params.subtitle?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                if (params.content != null) {
                    params.content.invoke(this)
                } else {
                    params.message?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    params.editTextOnValueChange?.let { onValueChange ->
                        var value by remember { mutableStateOf(params.editTextValue ?: "") }
                        CarUiEditText(
                            value = value,
                            onValueChange = {
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
                            hint = params.editTextPrompt ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            keyboardType = params.editTextKeyboardType
                        )
                    }

                    params.singleChoiceItems?.let { items ->
                        var selectedIndex by remember {
                            mutableStateOf(
                                params.singleChoiceSelectedIndex ?: -1
                            )
                        }
                        CarUiRecyclerView(
                            items = items,
                        ) { item ->
                            val index = items.indexOf(item)
                            CarUiRadioButtonListItem(
                                title = item,
                                selected = index == selectedIndex,
                                onSelectedChange = { isSelected ->
                                    if (isSelected) {
                                        selectedIndex = index
                                        params.onSingleChoiceSelect?.invoke(index)
                                    }
                                })
                        }
                    }

                    params.multiChoiceItems?.let { items ->
                        var checkedStates by remember { mutableStateOf(List(items.size) { false }) }

                        CarUiRecyclerView(items = items) { item ->
                            val index = items.indexOf(item)
                            CarUiCheckBoxListItem(
                                title = item,
                                checked = checkedStates[index],
                                onCheckedChange = { isChecked ->
                                    checkedStates =
                                        checkedStates.toMutableList().also { it[index] = isChecked }
                                    params.onMultiChoiceChange?.invoke(index, isChecked)
                                }
                            )
                        }
                    }
                }
            }
        },
    )
}