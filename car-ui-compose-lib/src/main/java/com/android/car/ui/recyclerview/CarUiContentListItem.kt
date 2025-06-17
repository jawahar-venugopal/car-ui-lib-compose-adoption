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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.android.car.ui.R

enum class CarUiContentListItemIconType { STANDARD, AVATAR, CONTENT }

@Composable
fun CarUiContentListItem(
    title: String? = null,
    body: String? = null,
    icon: Painter? = null,
    iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    val standardIconSize = dimensionResource(id = R.dimen.car_ui_primary_icon_size)
    val avatarIconSize = dimensionResource(id = R.dimen.car_ui_primary_icon_size)
    val contentIconSize = dimensionResource(id = R.dimen.car_ui_list_item_icon_container_width)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = dimensionResource(id = R.dimen.car_ui_list_item_height))
            .then(if (enabled && !restricted && onClick != null) Modifier.clickable { onClick() } else Modifier),
        color = colorResource(android.R.color.transparent)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .width(dimensionResource(R.dimen.car_ui_list_item_icon_container_width)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.let {
                when (iconType) {
                    CarUiContentListItemIconType.AVATAR -> {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(avatarIconSize)
                                .clip(CircleShape)
                        )
                    }

                    CarUiContentListItemIconType.CONTENT -> {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(contentIconSize)
                        )
                    }

                    CarUiContentListItemIconType.STANDARD -> {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier.size(standardIconSize)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dimensionResource(R.dimen.car_ui_list_item_text_start_margin))
            ) {
                if (!title.isNullOrBlank()) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.body1,
                        color = if (enabled) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface.copy(
                            alpha = ContentAlpha.disabled
                        )
                    )
                }
                if (!body.isNullOrBlank()) {
                    Text(
                        text = body,
                        style = MaterialTheme.typography.subtitle1,
                        color = if (enabled) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onSurface.copy(
                            alpha = ContentAlpha.disabled
                        )
                    )
                }
            }
            if (trailingContent != null) {
                Box(
                    modifier = Modifier
                        .heightIn(min = 0.dp)
                        .widthIn(min = contentIconSize),
                    contentAlignment = Alignment.Center
                ) {
                    trailingContent()
                }
            }
        }
    }
}
