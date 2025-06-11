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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.android.car.ui.R


enum class CarUiRecyclerViewLayoutStyle { LIST, GRID }

@Composable
fun <T> CarUiRecyclerView(
    items: List<T>,
    showDivider: Boolean = false,
    layoutStyle: CarUiRecyclerViewLayoutStyle = CarUiRecyclerViewLayoutStyle.LIST,
    numOfColumns: Int = 1,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    val dividerColor = colorResource(id = R.color.car_ui_divider_color)
    val dividerHeight = dimensionResource(id = R.dimen.car_ui_divider_height)
    dimensionResource(id = R.dimen.car_ui_scrollbar_thickness)
    dimensionResource(id = R.dimen.car_ui_scrollbar_padding)
    colorResource(id = R.color.car_ui_scrollbar_thumb_color)
    colorResource(id = R.color.car_ui_scrollbar_track_color)
    RoundedCornerShape(dimensionResource(id = R.dimen.car_ui_scrollbar_thumb_radius))
    val recyclerViewHeight = Modifier.fillMaxHeight()

    Box(modifier = modifier.then(recyclerViewHeight)) {
        if (layoutStyle == CarUiRecyclerViewLayoutStyle.GRID && numOfColumns > 1) {
            val gridState = rememberLazyGridState()
            // Grid with scrollbar
            Box {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(numOfColumns),
                    state = gridState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items) { item ->
                        itemContent(item)
                        // No dividers for grid for visual parity
                    }
                }
            }
            CarUiScrollbar(
                gridState = gridState,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        } else {
            val listState = rememberLazyListState()
            // List with scrollbar
            Box {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items) { item ->
                        itemContent(item)
                        if (showDivider) {
                            Divider(
                                color = dividerColor,
                                thickness = dividerHeight
                            )
                        }
                    }
                }
            }
            CarUiScrollbar(
                listState = listState,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}
