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
package com.android.car.ui.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.android.car.ui.R

@Composable
fun CarUiListPreference(
    title: String,
    summary: String? = null,
    icon: Painter? = null,
    entries: List<String>,
    selectedIndex: Int,
    onEntrySelected: (Int) -> Unit,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onRestrictedClick: (() -> Unit)? = null,
    onDisabledClick: (() -> Unit)? = null,
    showChevron: Boolean = true,
    modifier: Modifier = Modifier,
) {
    var dialogOpen by remember { mutableStateOf(false) }
    val isEnabled = enabled && !restricted
    val contentColor =
        if (isEnabled) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSurface.copy(
            alpha = 0.38f
        )
    val background = MaterialTheme.colors.background
    val padding = dimensionResource(id = R.dimen.car_ui_pref_padding)
    val minHeight = dimensionResource(id = R.dimen.car_ui_pref_min_height)
    val iconSize = dimensionResource(id = R.dimen.car_ui_pref_icon_size)
    val iconSpacing = dimensionResource(id = R.dimen.car_ui_pref_icon_spacing)
    val shape = MaterialTheme.shapes.medium

    Surface(
        color = background,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = padding)
            .alpha(if (restricted) ContentAlpha.disabled else 1f)
            .heightIn(min = minHeight),
        shape = shape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .let {
                    when {
                        isEnabled -> it.clickable { dialogOpen = true }
                        restricted && onRestrictedClick != null -> it.clickable { onRestrictedClick() }
                        !isEnabled && onDisabledClick != null -> it.clickable { onDisabledClick() }
                        else -> it
                    }
                }
                .padding(all = padding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier
                        .size(iconSize)
                        .padding(end = iconSpacing)
                )
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    color = contentColor
                )
                if (!summary.isNullOrBlank()) {
                    Text(
                        text = summary,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                } else {
                    // Dynamic summary: selected entry
                    Text(
                        text = entries.getOrNull(selectedIndex) ?: "",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            if (showChevron) {
                Icon(
                    painter = painterResource(id = R.drawable.car_ui_icon_chevron),
                    contentDescription = "Chevron",
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }

    // Dialog for selection
    if (dialogOpen) {
        val dialogItemVerticalPadding =
            dimensionResource(id = R.dimen.car_ui_dialog_list_item_vertical_padding)
        val dialogItemHorizontalPadding =
            dimensionResource(id = R.dimen.car_ui_dialog_list_item_horizontal_padding)
        val dialogItemIconMarginEnd =
            dimensionResource(id = R.dimen.car_ui_dialog_list_item_icon_margin_end)
        AlertDialog(
            onDismissRequest = { dialogOpen = false },
            title = { Text(title) },
            text = {
                Column {
                    entries.forEachIndexed { i, entry ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    dialogOpen = false
                                    if (i != selectedIndex) onEntrySelected(i)
                                }
                                .padding(
                                    vertical = dialogItemVerticalPadding,
                                    horizontal = dialogItemHorizontalPadding
                                )
                        ) {
                            RadioButton(selected = i == selectedIndex, onClick = null)
                            Spacer(Modifier.width(dialogItemIconMarginEnd))
                            Text(entry)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}
