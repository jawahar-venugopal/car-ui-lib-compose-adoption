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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.android.car.ui.R
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun CarUiScrollbar(
    state: CarUiScrollbarState,
    thumbColor: Color = colorResource(id = R.color.car_ui_scrollbar_thumb_color),
    thumbMinHeight: Dp = dimensionResource(id = R.dimen.car_ui_scrollbar_thumb_min_height),
    thumbRadius: Dp = dimensionResource(id = R.dimen.car_ui_scrollbar_thumb_radius),
    buttonSize: Dp = dimensionResource(id = R.dimen.car_ui_scrollbar_button_size),
    buttonMargin: Dp = dimensionResource(id = R.dimen.car_ui_scrollbar_button_margin),
    iconUp: Painter = painterResource(id = R.drawable.car_ui_recyclerview_ic_up),
    iconDown: Painter = painterResource(id = R.drawable.car_ui_recyclerview_ic_down),
    onPageUp: (() -> Unit)? = null,
    onPageDown: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    if (!state.canScrollForward && !state.canScrollBackward) return
    val scrollbarWidth = dimensionResource(id = R.dimen.car_ui_scrollbar_width)
    val thumbWidth = dimensionResource(id = R.dimen.car_ui_scrollbar_thumb_width)

    val atTop = state.firstVisibleItemIndex == 0
    val atBottom = (state.firstVisibleItemIndex + state.visibleItemsCount) >= state.totalItemsCount
    val disabledAlpha = 0.46f

    val density = LocalDensity.current
    val scrollbarHeightPx = remember { mutableStateOf(0) }

    val validVisibleItems = if (state.visibleItemsCount > 0) state.visibleItemsCount else 1
    val validTotalItems = if (state.totalItemsCount > 0) state.totalItemsCount else 1

    val thumbMinHeightPx = with(density) { thumbMinHeight.roundToPx() }
    val trackHeightPx = scrollbarHeightPx.value
    val proportionalThumbHeightPx =
        ((validVisibleItems.toFloat() / validTotalItems.toFloat()) * trackHeightPx).roundToInt()
    val thumbHeightPx = max(
        proportionalThumbHeightPx,
        min(thumbMinHeightPx, trackHeightPx)
    )
    val scrollPercent = if (validTotalItems > validVisibleItems) {
        state.firstVisibleItemIndex.toFloat() / (validTotalItems - validVisibleItems).toFloat()
    } else 0f
    val thumbOffsetPx = ((trackHeightPx - thumbHeightPx) * scrollPercent).toInt()


    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(scrollbarWidth)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(buttonSize)
                .padding(top = buttonMargin)
        ) {
            IconButton(
                onClick = {
                    if (!atTop) {
                        onPageUp?.invoke()
                    }
                },
                enabled = !atTop,
                modifier = Modifier.matchParentSize()
            ) {
                Icon(
                    painter = iconUp,
                    contentDescription = "Scroll Up",
                    tint = if (!atTop) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface.copy(
                        alpha = disabledAlpha
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(scrollbarWidth)
                .padding(top = buttonSize, bottom = buttonSize)
                .background(colorResource(id = R.color.car_ui_scrollbar_track_color))
                .onGloballyPositioned {
                    scrollbarHeightPx.value = it.size.height
                }
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, thumbOffsetPx) }
                    .width(thumbWidth)
                    .height(with(density) { thumbHeightPx.toDp() })
                    .clip(RoundedCornerShape(thumbRadius))
                    .background(thumbColor)
                    .align(Alignment.TopCenter)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(buttonSize)
                .padding(bottom = buttonMargin)
        ) {
            IconButton(
                onClick = {
                    if (!atBottom) {
                        onPageDown?.invoke()
                    }
                },
                enabled = !atBottom,
                modifier = Modifier.matchParentSize()
            ) {
                Icon(
                    painter = iconDown,
                    contentDescription = "Scroll Down",
                    tint = if (!atBottom) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface.copy(
                        alpha = disabledAlpha
                    )
                )
            }
        }
    }
}
