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

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.car.ui.R

@Composable
fun CarUiToolbar(
    title: String = "",
    subtitle: String? = null,
    navIconType: CarUiToolbarNavIconType = CarUiToolbarNavIconType.None,
    onNavClick: (() -> Unit)? = null,
    logo: Painter? = null,
    menuItems: List<CarUiToolbarMenuItem> = emptyList(),
    showOverflowMenu: Boolean = false,
    overflowMenuItems: List<CarUiToolbarMenuItem> = emptyList(),
    searchMode: SearchMode = SearchMode.DISABLED,
    searchQuery: String = "",
    onSearchQueryChanged: ((String) -> Unit)? = null,
    onSearchSubmitted: (() -> Unit)? = null,
    onSearchModeChanged: ((SearchMode) -> Unit)? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    restricted: Boolean = false,
    onMenuItemCheckedChange: ((id: Int, checked: Boolean) -> Unit)? = null,
    onMenuItemActivatedChange: ((id: Int, activated: Boolean) -> Unit)? = null,
) {
    val horizontalPadding = dimensionResource(id = R.dimen.car_ui_toolbar_horizontal_padding)
    val iconSize = dimensionResource(id = R.dimen.car_ui_toolbar_icon_size)
    val spacing = dimensionResource(id = R.dimen.car_ui_toolbar_icon_spacing)
    val toolbarHeight = dimensionResource(id = R.dimen.car_ui_toolbar_height)
    val elevation = dimensionResource(id = R.dimen.car_ui_toolbar_elevation)

    Surface(
        color = backgroundColor,
        modifier = modifier
            .fillMaxWidth()
            .height(toolbarHeight),
        elevation = elevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation icon (always visible if set)
            if (navIconType != CarUiToolbarNavIconType.None) {
                IconButton(
                    onClick = { if (!restricted) onNavClick?.invoke() },
                    enabled = !restricted,
                    modifier = Modifier.size(iconSize)
                ) {
                    Icon(
                        painter = when (navIconType) {
                            CarUiToolbarNavIconType.Back -> painterResource(id = R.drawable.car_ui_icon_arrow_back)
                            CarUiToolbarNavIconType.Close -> painterResource(id = R.drawable.car_ui_icon_close)
                            else -> return@IconButton
                        },
                        contentDescription = navIconType.name,
                        tint = if (!restricted) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface.copy(
                            alpha = 0.38f
                        )
                    )
                }
                Spacer(Modifier.width(spacing))
            }
            // Logo (optional)
            if (logo != null) {
                Icon(
                    painter = logo,
                    contentDescription = "Logo",
                    modifier = Modifier.size(iconSize)
                )
                Spacer(Modifier.width(spacing))
            }
            if (searchMode == SearchMode.SEARCH || searchMode == SearchMode.EDIT) {
                // --- SEARCH/EDIT MODE ---
                SearchView(
                    value = searchQuery,
                    onValueChange = { onSearchQueryChanged?.invoke(it) },
                    hint = if (searchMode == SearchMode.SEARCH)
                        stringResource(id = R.string.car_ui_search_hint)
                    else
                        stringResource(id = R.string.car_ui_edit_hint),
                    enabled = !restricted,
                    restricted = restricted,
                    onQueryTextSubmit = { onSearchSubmitted?.invoke() },
                    onClear = { onSearchQueryChanged?.invoke("") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(spacing))
                // Cancel icon
                IconButton(
                    onClick = {
                        onSearchModeChanged?.invoke(SearchMode.DISABLED)
                    },
                    enabled = !restricted,
                    modifier = Modifier.size(iconSize)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.car_ui_icon_close),
                        contentDescription = "Close Search",
                        tint = if (!restricted) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface.copy(
                            alpha = 0.38f
                        )
                    )
                }
            } else {
                // --- NORMAL TOOLBAR MODE ---
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onPrimary
                    )
                    if (!subtitle.isNullOrEmpty()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                }
                // Menu Items
                menuItems.forEach { menuItem ->
                    CarUiToolbarMenuItemView(
                        menuItem = menuItem,
                        onCheckedChange = { checked ->
                            onMenuItemCheckedChange?.invoke(
                                menuItem.id,
                                checked
                            )
                        },
                        onActivatedChange = { activated ->
                            onMenuItemActivatedChange?.invoke(
                                menuItem.id,
                                activated
                            )
                        }
                    )
                    Spacer(Modifier.width(spacing))
                }
                // Overflow menu
                if (showOverflowMenu && overflowMenuItems.isNotEmpty()) {
                    IconButton(onClick = { /* TODO: show overflow */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.car_ui_icon_overflow_menu),
                            contentDescription = "More",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
                // Search icon (to enter search mode)
                IconButton(
                    onClick = { onSearchModeChanged?.invoke(SearchMode.SEARCH) },
                    enabled = !restricted,
                    modifier = Modifier.size(iconSize)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.car_ui_icon_search),
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
