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

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.car.ui.R

@Composable
fun CarUiToolbar(
    title: String = "",
    subtitle: String? = null,
    navIconType: CarUiToolbarNavIconType = CarUiToolbarNavIconType.None,
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
    backgroundColor: Color = colorResource(R.color.car_ui_toolbar_background),
    restricted: Boolean = false,
    onMenuItemCheckedChange: ((id: Int, checked: Boolean) -> Unit)? = null,
    onMenuItemActivatedChange: ((id: Int, activated: Boolean) -> Unit)? = null,
) {
    val toolbarMargin = dimensionResource(id = R.dimen.car_ui_toolbar_margin)
    val navIconSize = dimensionResource(id = R.dimen.car_ui_toolbar_nav_icon_size)
    val logoSize = dimensionResource(id = R.dimen.car_ui_toolbar_logo_size)
    val titleMarginStart = dimensionResource(id = R.dimen.car_ui_toolbar_title_margin_start)
    val menuItemMargin = dimensionResource(id = R.dimen.car_ui_toolbar_menu_item_margin)
    val menuItemIconSize = dimensionResource(id = R.dimen.car_ui_toolbar_menu_item_icon_size)
    val menuItemIconBgSize =
        dimensionResource(id = R.dimen.car_ui_toolbar_menu_item_icon_background_size)
    val toolbarHeight = dimensionResource(id = R.dimen.car_ui_toolbar_row_height)
    val elevation = dimensionResource(id = R.dimen.car_ui_toolbar_elevation)
    val searchIconContainerWidth =
        dimensionResource(id = R.dimen.car_ui_toolbar_search_search_icon_container_width)
    val closeIconContainerWidth =
        dimensionResource(id = R.dimen.car_ui_toolbar_search_close_icon_container_width)
    val searchIconSize = dimensionResource(id = R.dimen.car_ui_toolbar_search_search_icon_size)
    val closeIconSize = dimensionResource(id = R.dimen.car_ui_toolbar_search_close_icon_size)

    Surface(
        color = backgroundColor,
        modifier = modifier
            .fillMaxWidth()
            .height(toolbarHeight),
        elevation = elevation
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(toolbarMargin)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    val context = LocalContext.current
                    val activity = context as? ComponentActivity
                    if (navIconType != CarUiToolbarNavIconType.None) {
                        IconButton(
                            onClick = { when (navIconType) {
                                CarUiToolbarNavIconType.Back -> activity?.onBackPressedDispatcher?.onBackPressed()
                                CarUiToolbarNavIconType.Close -> activity?.finish()
                                else -> {}
                            } },
                            enabled = !restricted,
                            modifier = Modifier.size(navIconSize)
                        ) {
                            Icon(
                                painter = when (navIconType) {
                                    CarUiToolbarNavIconType.Back -> painterResource(id = R.drawable.car_ui_icon_arrow_back)
                                    CarUiToolbarNavIconType.Close -> painterResource(id = R.drawable.car_ui_icon_close)
                                    else -> return@IconButton
                                },
                                contentDescription = navIconType.name,
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                    if (logo != null) {
                        Icon(
                            painter = logo,
                            contentDescription = "Logo",
                            modifier = Modifier.size(logoSize),
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            if (searchMode == SearchMode.SEARCH || searchMode == SearchMode.EDIT) {
                // --- SEARCH/EDIT MODE ---
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = titleMarginStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Search icon container
                    Box(
                        modifier = Modifier.width(searchIconContainerWidth),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.car_ui_icon_search),
                            contentDescription = stringResource(id = R.string.car_ui_search_content_desc),
                            modifier = Modifier.size(searchIconSize),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    // Search input
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
                    // Close/cancel icon container
                    Box(
                        modifier = Modifier.width(closeIconContainerWidth),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { onSearchModeChanged?.invoke(SearchMode.DISABLED) },
                            enabled = !restricted,
                            modifier = Modifier.size(closeIconSize)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.car_ui_icon_close),
                                contentDescription = "Close Search",
                                tint = if (!restricted) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface.copy(
                                    alpha = 0.38f
                                )
                            )
                        }
                    }
                }
            } else {
                // --- NORMAL TOOLBAR MODE ---
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h1,
                        color = colorResource(R.color.car_ui_text_color_primary)
                    )
                    if (!subtitle.isNullOrEmpty()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.subtitle2,
                            color = colorResource(R.color.car_ui_text_color_primary).copy(alpha = 0.8f)
                        )
                    }
                }
                menuItems.filter { !it.isSearch }.forEach { menuItem ->
                    CarUiToolbarMenuItemView(
                        menuItem = menuItem,
                        iconSize = menuItemIconSize,
                        iconBgSize = menuItemIconBgSize,
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
                    Spacer(Modifier.padding(end = menuItemMargin))
                }
                // Menu Items
                val searchMenuItem = menuItems.firstOrNull { it.isSearch && it.visible }
                if (searchMenuItem != null) {
                    // Search icon (to enter search mode)
                    IconButton(
                        onClick = { onSearchModeChanged?.invoke(SearchMode.SEARCH) },
                        enabled = !restricted,
                        modifier = Modifier.size(menuItemIconSize)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.car_ui_icon_search),
                            contentDescription = "Search",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    Spacer(Modifier.padding(end = menuItemMargin))
                }
                // Overflow menu
                if (showOverflowMenu && overflowMenuItems.isNotEmpty()) {
                    IconButton(
                        onClick = { /* TODO: show overflow */ },
                        modifier = Modifier.size(menuItemIconSize)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.car_ui_icon_overflow_menu),
                            contentDescription = "More",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}
