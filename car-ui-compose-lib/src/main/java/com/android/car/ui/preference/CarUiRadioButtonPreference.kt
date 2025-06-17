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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.android.car.ui.R
import com.android.car.ui.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun CarUiRadioButtonPreference(
    groupKey: String,
    value: String,
    title: String,
    summary: String? = null,
    icon: Painter? = null,
    defaultSelected: Boolean = false,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onRestrictedClick: (() -> Unit)? = null,
    clickableWhileDisabled: Boolean = false,
    onDisabledClick: (() -> Unit)? = null,
    showChevron: Boolean = false,
    modifier: Modifier = Modifier,
    selected: Boolean? = null,
    onSelected: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val dataStore = context.dataStore
    val prefKey = stringPreferencesKey(groupKey)
    var internalSelected by remember { mutableStateOf(defaultSelected) }
    var loaded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val isAuto = selected == null || onSelected == null

    LaunchedEffect(groupKey) {
        if (isAuto) {
            val prefs = dataStore.data.first()
            internalSelected = (prefs[prefKey] ?: "").equals(value)
            loaded = true
        } else {
            loaded = true
        }
    }
    if (!loaded) return

    val isChecked = selected ?: internalSelected
    val isEnabled = enabled && !restricted && LocalPreferenceCategoryEnabled.current
    val clickable = (enabled || clickableWhileDisabled) && !restricted
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
            .alpha(if (restricted) ContentAlpha.disabled else 1f)
            .heightIn(min = minHeight),
        shape = shape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .let {
                    when {
                        isEnabled && (onSelected != null || isAuto) -> it.clickable {
                            if (isAuto) {
                                internalSelected = true
                                scope.launch { dataStore.edit { it[prefKey] = value } }
                            }
                            onSelected?.invoke()
                        }

                        restricted && onRestrictedClick != null -> it.clickable { onRestrictedClick() }
                        !isEnabled && onDisabledClick != null -> it.clickable { onDisabledClick() }
                        else -> it
                    }
                }
                .padding(all = padding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(iconSize)
                        .align(alignment = Alignment.CenterVertically),
                    contentScale = ContentScale.Fit
                )
            }
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = if (icon != null) iconSpacing else 0.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                if (!summary.isNullOrBlank()) {
                    Text(
                        text = summary,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSecondary
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
            RadioButton(
                selected = isChecked,
                onClick = if (isEnabled && (onSelected != null || isAuto)) {
                    {
                        if (isAuto) {
                            internalSelected = true
                            scope.launch { dataStore.edit { it[prefKey] = value } }
                        }
                        onSelected?.invoke()
                    }
                } else null,
                enabled = clickable,
                modifier = Modifier.scale(2.0f),
                colors = RadioButtonDefaults.colors(unselectedColor = MaterialTheme.colors.onSurface)
            )
        }
    }
}
