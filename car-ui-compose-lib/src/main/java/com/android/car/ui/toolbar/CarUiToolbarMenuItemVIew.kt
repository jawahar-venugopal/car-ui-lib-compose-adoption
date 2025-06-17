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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CarUiToolbarMenuItemView(
    menuItem: CarUiToolbarMenuItem,
    iconSize: Dp,
    iconBgSize: Dp,
    modifier: Modifier = Modifier
) {
    if (!menuItem.visible) return

    val isEnabled = menuItem.enabled && !menuItem.restricted

    val (checkedState, setCheckedState) = remember {
        mutableStateOf(menuItem.checked)
    }
    val (activatedState, setActivatedState) = remember {
        mutableStateOf(menuItem.activated)
    }

    LaunchedEffect(menuItem.checked) {
        setCheckedState(menuItem.checked)
    }
    LaunchedEffect(menuItem.activated) {
        setActivatedState(menuItem.activated)
    }

    val contentColor = when {
        !isEnabled -> MaterialTheme.colors.onSecondary
        else -> MaterialTheme.colors.onSurface
    }
    val iconTint = if (menuItem.tinted) contentColor else Color.Unspecified

    @Composable
    fun IconAndTitleRow() {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (menuItem.iconRes != null && (menuItem.showIconAndTitle || menuItem.title.isNullOrEmpty())) {
                Icon(
                    painter = painterResource(menuItem.iconRes),
                    contentDescription = menuItem.title,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            }
            if (!menuItem.title.isNullOrEmpty() && (menuItem.showIconAndTitle || menuItem.iconRes == null)) {
                Text(
                    text = menuItem.title,
                    color = contentColor
                )
            }
        }
    }

    when {
        menuItem.checkable -> {
            Row(
                modifier = modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (menuItem.showIconAndTitle || menuItem.iconRes != null || menuItem.title != null) {
                    IconAndTitleRow()
                    Spacer(Modifier.width(8.dp))
                }
                Switch(
                    checked = checkedState,
                    onCheckedChange = {
                        if (isEnabled) {
                            setCheckedState(it)
                            menuItem.onCheckedChange?.invoke(it)
                        }
                    },
                    enabled = isEnabled
                )
            }
        }

        menuItem.activatable -> {
            menuItem.iconRes?.let {
                CarUiActivatableMenuItem(
                    it,
                    title = menuItem.title,
                    activated = activatedState,
                    onActivatedChange = {
                        if (isEnabled) {
                            setActivatedState(it)
                            menuItem.onActivatedChange?.invoke(it)
                        }
                    },
                    iconSize = iconSize,
                    iconBgSize = iconBgSize
                )
            }
        }

        else -> {
            IconButton(
                onClick = { if (isEnabled) menuItem.onClick?.invoke() },
                enabled = isEnabled,
            ) {
                IconAndTitleRow()
            }
        }
    }
}

@Composable
fun CarUiActivatableMenuItem(
    iconRes: Int,
    title: String? = null,
    activated: Boolean,
    onActivatedChange: (Boolean) -> Unit,
    iconSize: Dp,
    iconBgSize: Dp,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val bgColor = if (activated) Color.White else Color.Transparent
    val iconTint = if (activated) Color.Black else MaterialTheme.colors.onSurface

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(iconBgSize)
            .clip(CircleShape)
            .background(bgColor)
            .clickable(
                enabled = enabled,
                indication = rememberRipple(bounded = false, radius = iconBgSize / 2),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onActivatedChange(!activated) }
            )
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}
