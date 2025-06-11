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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.android.car.ui.R

enum class CarUiContentListItemIconType { STANDARD, AVATAR, CONTENT }

@Composable
fun CarUiContentListItem(
    title: String,
    body: String? = null,
    icon: Painter? = null,
    iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
    chevron: Boolean = false,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val horizontalPadding = dimensionResource(id = R.dimen.car_ui_list_item_horizontal_padding)
    val verticalPadding = dimensionResource(id = R.dimen.car_ui_list_item_vertical_padding)
    val chevronSize = dimensionResource(id = R.dimen.car_ui_list_item_chevron_size)
    val standardIconSize = dimensionResource(id = R.dimen.car_ui_content_list_item_icon_size)
    val avatarIconSize = dimensionResource(id = R.dimen.car_ui_content_list_item_avatar_size)
    val contentIconSize = dimensionResource(id = R.dimen.car_ui_content_list_item_content_size)
    val iconSpacing = dimensionResource(id = R.dimen.car_ui_content_list_item_icon_spacing)
    val cornerRadius =
        dimensionResource(id = R.dimen.car_ui_content_list_item_content_corner_radius)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = dimensionResource(id = R.dimen.car_ui_content_list_item_min_height))
            .then(if (enabled && !restricted && onClick != null) Modifier.clickable { onClick() } else Modifier),
        color = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
                                .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
                        )
                    }

                    CarUiContentListItemIconType.CONTENT -> {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(contentIconSize)
                                .clip(RoundedCornerShape(cornerRadius))
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
                Spacer(modifier = Modifier.width(iconSpacing))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                if (!body.isNullOrBlank()) {
                    Text(
                        text = body,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            if (chevron) {
                Icon(
                    painter = painterResource(id = R.drawable.car_ui_icon_chevron),
                    contentDescription = null,
                    modifier = Modifier.size(chevronSize)
                )
            }
        }
    }
}
