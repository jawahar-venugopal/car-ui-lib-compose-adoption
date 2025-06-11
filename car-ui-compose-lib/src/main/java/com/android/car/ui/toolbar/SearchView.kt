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
    val isEditable = enabled && !restricted
    val iconSize = dimensionResource(id = R.dimen.car_ui_primary_icon_size)
    val iconSpacing = dimensionResource(id = R.dimen.car_ui_toolbar_icon_spacing)

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.surface
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.car_ui_icon_search),
                contentDescription = "Search",
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.size(iconSize)
            )
            CarUiEditText(
                value = value,
                onValueChange = onValueChange,
                hint = hint,
                enabled = enabled,
                restricted = restricted,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = iconSpacing, end = iconSpacing),
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
                onImeAction = { onQueryTextSubmit?.invoke() }
            )
            if (value.isNotEmpty() && isEditable && onClear != null) {
                IconButton(
                    onClick = onClear,
                    enabled = isEditable,
                    modifier = Modifier.size(iconSize)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.car_ui_icon_close),
                        contentDescription = "Clear",
                        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

