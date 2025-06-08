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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.android.car.ui.R

@Composable
fun CarUiToolbarMenuItemView(
    menuItem: CarUiToolbarMenuItem,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    onActivatedChange: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    if (!menuItem.visible) return

    val iconSize = dimensionResource(id = R.dimen.car_ui_toolbar_icon_size)
    val isEnabled = menuItem.enabled && !menuItem.restricted

    // Tint logic
    val contentColor = when {
        !isEnabled -> MaterialTheme.colors.onSurface.copy(alpha = 0.38f)
        menuItem.primary -> MaterialTheme.colors.primary
        else -> MaterialTheme.colors.onPrimary
    }
    val iconTint = if (menuItem.tinted) contentColor else Color.Unspecified

    val backgroundModifier = if (menuItem.activated) {
        modifier.background(MaterialTheme.colors.primary.copy(alpha = 0.12f), shape = CircleShape)
    } else modifier

    when {
        menuItem.checkable -> {
            IconToggleButton(
                checked = menuItem.checked,
                onCheckedChange = {
                    if (isEnabled) {
                        onCheckedChange?.invoke(it)
                        menuItem.onClick?.invoke()
                    }
                },
                enabled = isEnabled,
                modifier = backgroundModifier.size(iconSize)
            ) {
                if (menuItem.icon != null) {
                    Icon(
                        painter = menuItem.icon,
                        contentDescription = menuItem.title,
                        tint = iconTint
                    )
                } else if (!menuItem.title.isNullOrEmpty()) {
                    Text(
                        text = menuItem.title,
                        color = contentColor
                    )
                }
            }
        }
        menuItem.activatable -> {
            IconButton(
                onClick = {
                    if (isEnabled) {
                        onActivatedChange?.invoke(!menuItem.activated)
                        menuItem.onClick?.invoke()
                    }
                },
                enabled = isEnabled,
                modifier = backgroundModifier.size(iconSize)
            ) {
                if (menuItem.icon != null) {
                    Icon(
                        painter = menuItem.icon,
                        contentDescription = menuItem.title,
                        tint = iconTint
                    )
                } else if (!menuItem.title.isNullOrEmpty()) {
                    Text(
                        text = menuItem.title,
                        color = contentColor
                    )
                }
            }
        }
        else -> {
            IconButton(
                onClick = { if (isEnabled) menuItem.onClick?.invoke() },
                enabled = isEnabled,
                modifier = backgroundModifier.size(iconSize)
            ) {
                if (menuItem.icon != null && (menuItem.showIconAndTitle || menuItem.title.isNullOrEmpty())) {
                    Icon(
                        painter = menuItem.icon,
                        contentDescription = menuItem.title,
                        tint = iconTint
                    )
                }
                if (!menuItem.title.isNullOrEmpty() && (menuItem.showIconAndTitle || menuItem.icon == null)) {
                    Text(
                        text = menuItem.title,
                        color = contentColor
                    )
                }
            }
        }
    }
}
