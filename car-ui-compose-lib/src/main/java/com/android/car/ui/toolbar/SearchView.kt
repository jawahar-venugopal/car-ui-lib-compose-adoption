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
package com.android.car.ui.toolbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.android.car.ui.R

@Composable
fun SearchView(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    enabled: Boolean = true,
    restricted: Boolean = false,
    onQueryTextSubmit: (() -> Unit)? = null,
    onClear: (() -> Unit)? = null,
) {
    val iconSpacing = dimensionResource(id = R.dimen.car_ui_toolbar_icon_spacing)
    val isEditable = enabled && !restricted
    Surface(
        modifier = modifier,
        color = colorResource(R.color.car_ui_toolbar_background)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CarUiEditText(
                value = value,
                onValueChange = onValueChange,
                hint = hint,
                enabled = enabled,
                restricted = restricted,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.car_ui_icon_search),
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.car_ui_primary_icon_size))
                    )
                },
                trailingIcon = {
                    if (value.isNotEmpty() && isEditable && onClear != null) {
                        IconButton(
                            onClick = onClear,
                            enabled = isEditable,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.car_ui_icon_close),
                                contentDescription = "Clear",
                                modifier = Modifier.size(dimensionResource(id = R.dimen.car_ui_primary_icon_size)),
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = iconSpacing, end = iconSpacing),
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
                onImeAction = { onQueryTextSubmit?.invoke() },
            )
        }
    }
}

