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

import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun CarUiCheckBoxListItem(
    title: String? = null,
    body: String? = null,
    icon: Painter? = null,
    iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
    checked: Boolean = false,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    CarUiContentListItem(
        title = title,
        body = body,
        icon = icon,
        iconType = iconType,
        enabled = enabled,
        restricted = restricted,
        onClick = null,
        trailingContent = {
            Checkbox(
                checked = checked,
                enabled = enabled && !restricted,
                onCheckedChange = {
                    if (enabled && !restricted) onCheckedChange?.invoke(it)
                },
                modifier = Modifier.scale(2.0f),
                colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colors.onSurface)
            )
        }
    )
}
