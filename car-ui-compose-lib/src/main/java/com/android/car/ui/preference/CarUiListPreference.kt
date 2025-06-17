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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.android.car.ui.CarUiAlertDialog
import com.android.car.ui.CarUiAlertDialogParams
import com.android.car.ui.R
import com.android.car.ui.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun CarUiListPreference(
    key: String,
    title: String,
    dialogTitle: String = title,
    summary: String? = null,
    icon: Painter? = null,
    entries: List<String>,
    entryValues: List<String>,
    defaultValue: String = "",
    enabled: Boolean = true,
    restricted: Boolean = false,
    onRestrictedClick: (() -> Unit)? = null,
    onDisabledClick: (() -> Unit)? = null,
    showChevron: Boolean = false,
    useSimpleSummaryProvider: Boolean = false,
    modifier: Modifier = Modifier,
) {
    require(entries.size == entryValues.size) { "entries and entryValues must be the same size." }
    val context = LocalContext.current
    val dataStore = context.dataStore
    val prefKey = stringPreferencesKey(key)
    var selectedIndex by remember { mutableStateOf(-1) }
    var loaded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key) {
        val prefs = dataStore.data.first()
        val value = prefs[prefKey] ?: defaultValue
        selectedIndex = entryValues.indexOf(value).takeIf { it >= 0 } ?: -1
        loaded = true
    }

    if (loaded) {
        CarUiListPreferenceCore(
            title = title,
            dialogTitle = dialogTitle,
            summary = summary,
            icon = icon,
            entries = entries,
            selectedIndex = selectedIndex,
            onEntrySelected = { i ->
                selectedIndex = i
                scope.launch { dataStore.edit { it[prefKey] = entryValues[i] } }
            },
            enabled = enabled,
            restricted = restricted,
            onRestrictedClick = onRestrictedClick,
            onDisabledClick = onDisabledClick,
            showChevron = showChevron,
            useSimpleSummaryProvider = useSimpleSummaryProvider,
            modifier = modifier,
        )
    }
}

@Composable
fun CarUiListPreferenceCore(
    title: String,
    dialogTitle: String = title,
    summary: String? = null,
    icon: Painter? = null,
    entries: List<String>,
    selectedIndex: Int,
    onEntrySelected: (Int) -> Unit,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onRestrictedClick: (() -> Unit)? = null,
    onDisabledClick: (() -> Unit)? = null,
    showChevron: Boolean = false,
    useSimpleSummaryProvider: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var dialogOpen by remember { mutableStateOf(false) }
    val isEnabled = enabled && !restricted && LocalPreferenceCategoryEnabled.current
    val background = MaterialTheme.colors.background
    val padding = dimensionResource(id = R.dimen.car_ui_pref_padding)
    val minHeight = dimensionResource(id = R.dimen.car_ui_pref_min_height)
    val iconSize = dimensionResource(id = R.dimen.car_ui_pref_icon_size)
    dimensionResource(id = R.dimen.car_ui_pref_icon_spacing)
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
                        isEnabled -> it.clickable { dialogOpen = true }
                        restricted && onRestrictedClick != null -> it.clickable { onRestrictedClick() }
                        !isEnabled && onDisabledClick != null -> it.clickable { onDisabledClick() }
                        else -> it
                    }
                }
                .padding(vertical = padding),
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
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                val shownSummary = when {
                    useSimpleSummaryProvider -> {
                        if (selectedIndex < 0 || selectedIndex >= entries.size) "Not set"
                        else entries[selectedIndex]
                    }

                    !summary.isNullOrBlank() -> summary
                    else -> ""
                }
                Text(
                    text = shownSummary,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSecondary
                )
            }
            if (showChevron) {
                Icon(
                    painter = painterResource(id = R.drawable.car_ui_icon_chevron),
                    contentDescription = "Chevron",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
    CarUiAlertDialog(
        CarUiAlertDialogParams(
            show = dialogOpen,
            title = dialogTitle,
            singleChoiceItems = entries,
            singleChoiceSelectedIndex = selectedIndex,
            onSingleChoiceSelect = onEntrySelected,
            onDismiss = { dialogOpen = false }
        )
    )
}
