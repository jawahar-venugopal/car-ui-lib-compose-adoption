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
package com.android.car.ui.recyclerview

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.android.car.ui.R

enum class CarUiRecyclerViewLayoutStyle { LIST, GRID }

@Composable
fun CarUiRecyclerView(
    items: List<CarUiListItemData>,
    showDivider: Boolean = true,
    layoutStyle: CarUiRecyclerViewLayoutStyle = CarUiRecyclerViewLayoutStyle.LIST,
    numOfColumns: Int = 1,
    modifier: Modifier = Modifier,
) {
    val dividerColor = colorResource(id = R.color.car_ui_divider_color)
    val dividerHeight = dimensionResource(id = R.dimen.car_ui_divider_height)

    if (layoutStyle == CarUiRecyclerViewLayoutStyle.GRID && numOfColumns > 1) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(numOfColumns),
            modifier = modifier
        ) {
            items(items) { item ->
                CarUiListItemDispatcher(item)
                // In grid layouts, dividers are not typical; skip for visual parity
            }
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(items) { item ->
                CarUiListItemDispatcher(item)
                if (showDivider) {
                    Divider(
                        color = dividerColor,
                        thickness = dividerHeight
                    )
                }
            }
        }
    }
}

@Composable
private fun CarUiListItemDispatcher(item: CarUiListItemData) {
    when (item) {
        is CarUiListItemData.Content -> CarUiContentListItem(
            title = item.title,
            body = item.body,
            icon = item.icon,
            iconType = item.iconType,
            chevron = item.chevron,
            enabled = item.enabled,
            restricted = item.restricted,
            onClick = item.onClick
        )

        is CarUiListItemData.Header -> CarUiHeaderListItem(
            text = item.text
        )

        is CarUiListItemData.CheckBox -> CarUiCheckBoxListItem(
            title = item.title,
            checked = item.checked,
            enabled = item.enabled,
            restricted = item.restricted,
            onCheckedChange = item.onCheckedChange
        )

        is CarUiListItemData.RadioButton -> CarUiRadioButtonListItem(
            title = item.title,
            selected = item.selected,
            enabled = item.enabled,
            restricted = item.restricted,
            onClick = item.onClick
        )
    }
}
