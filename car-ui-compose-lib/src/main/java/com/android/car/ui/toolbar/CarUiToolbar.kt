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

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.android.car.ui.R

@Composable
fun CarUiToolbar(
    title: String = "",
    subtitle: String? = null,
    navIconType: CarUiToolbarNavIconType = CarUiToolbarNavIconType.Disabled,
    logo: Painter? = null,
    menuItems: List<CarUiToolbarMenuItem> = emptyList(),
    showOverflowMenu: Boolean = false,
    overflowMenuItems: List<CarUiToolbarMenuItem> = emptyList(),
    searchMode: SearchMode = SearchMode.DISABLED,
    searchHint: String? = null,
    searchQuery: String = "",
    onSearchQueryChanged: ((String) -> Unit)? = null,
    onSearchSubmitted: (() -> Unit)? = null,
    onSearchModeChanged: ((SearchMode) -> Unit)? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(R.color.car_ui_toolbar_background),
    restricted: Boolean = false,
    showProgressBar: Boolean = false,
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
    val progressbarHeight = 8.dp
    val elevation = dimensionResource(id = R.dimen.car_ui_toolbar_elevation)

    Surface(
        color = backgroundColor,
        modifier = modifier
            .fillMaxWidth()
            .height(toolbarHeight + progressbarHeight),
        elevation = elevation
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(toolbarHeight),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxHeight()
                            .widthIn(min = toolbarMargin)
                            .padding(
                                top = 10.dp, bottom = 10.dp, start = 16.dp, end = titleMarginStart
                            ),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        val context = LocalContext.current
                        val activity = context as? ComponentActivity
                        if (navIconType != CarUiToolbarNavIconType.Disabled) {
                            IconButton(
                                onClick = {
                                    when (navIconType) {
                                        CarUiToolbarNavIconType.Back -> if (searchMode == SearchMode.DISABLED) activity?.onBackPressedDispatcher?.onBackPressed() else onSearchModeChanged?.invoke(
                                            SearchMode.DISABLED
                                        )

                                        CarUiToolbarNavIconType.Close -> activity?.finish()
                                        else -> {}
                                    }
                                }, enabled = !restricted, modifier = Modifier.size(76.dp)
                            ) {
                                Icon(
                                    painter = when (navIconType) {
                                        CarUiToolbarNavIconType.Back -> painterResource(id = R.drawable.car_ui_icon_arrow_back)
                                        CarUiToolbarNavIconType.Close -> painterResource(id = R.drawable.car_ui_icon_close)
                                        CarUiToolbarNavIconType.Down -> painterResource(id = R.drawable.car_ui_icon_down)
                                        else -> return@IconButton
                                    },
                                    contentDescription = navIconType.name,
                                    modifier = Modifier.size(navIconSize),
                                    tint = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                        if (logo != null) {
                            IconButton(
                                modifier = Modifier.size(76.dp), onClick = {}) {
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
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SearchView(
                                value = searchQuery,
                                hint = searchHint
                                    ?: if (searchMode == SearchMode.SEARCH) stringResource(id = R.string.car_ui_search_hint)
                                    else stringResource(id = R.string.car_ui_edit_hint),
                                enabled = !restricted,
                                restricted = restricted,
                                onValueChange = { onSearchQueryChanged?.invoke(it) },
                                onQueryTextSubmit = { onSearchSubmitted?.invoke() },
                                onClear = {
                                    onSearchQueryChanged?.invoke("")
                                    onSearchModeChanged?.invoke(SearchMode.SEARCH)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.h1,
                                color = colorResource(R.color.car_ui_text_color_primary)
                            )
                            if (!subtitle.isNullOrEmpty()) {
                                Text(
                                    text = subtitle,
                                    style = MaterialTheme.typography.subtitle1,
                                    color = colorResource(R.color.car_ui_text_color_primary).copy(
                                        alpha = 0.8f
                                    )
                                )
                            }
                        }
                    }
                }
                if (searchMode == SearchMode.DISABLED) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .background(color = backgroundColor)
                            .align(Alignment.CenterEnd)
                            .padding(end = toolbarMargin)
                    ) {
                        menuItems.filter { !it.isSearch }.forEach { menuItem ->
                            CarUiToolbarMenuItemView(
                                menuItem = menuItem,
                                iconSize = menuItemIconSize,
                                iconBgSize = menuItemIconBgSize,
                            )
                            Spacer(Modifier.padding(end = menuItemMargin))
                        }
                        val searchMenuItem = menuItems.firstOrNull { it.isSearch && it.visible }
                        if (searchMenuItem != null && searchMenuItem.iconRes != null) {
                            CarUiToolbarMenuItemView(
                                menuItem = searchMenuItem.copy(onClick = {
                                    onSearchModeChanged?.invoke(SearchMode.SEARCH)
                                }),
                                iconSize = menuItemIconSize,
                                iconBgSize = menuItemIconBgSize,
                            )
                        }
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
            if (showProgressBar) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(progressbarHeight),
                )
            }
        }
    }
}
