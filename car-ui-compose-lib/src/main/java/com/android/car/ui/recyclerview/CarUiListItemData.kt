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

import androidx.compose.ui.graphics.painter.Painter

sealed class CarUiListItemData {

    data class Header(
        val text: String,
        val body: String? = null
    ) : CarUiListItemData()

    data class Content(
        val title: String? = null,
        val body: String? = null,
        val icon: Painter? = null,
        val iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onClick: (() -> Unit)? = null
    ) : CarUiListItemData()

    data class ActionCheckBox(
        val title: String,
        val body: String? = null,
        val icon: Painter? = null,
        val iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
        val checked: Boolean = false,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onCheckedChange: ((Boolean) -> Unit)? = null
    ) : CarUiListItemData()

    data class ActionChevron(
        val title: String? = null,
        val body: String? = null,
        val icon: Painter? = null,
        val iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onClick: (() -> Unit)? = null
    ) : CarUiListItemData()

    data class ActionIcon(
        val title: String? = null,
        val body: String? = null,
        val icon: Painter? = null,
        val iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
        val trailingIcon: Painter,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onClick: (() -> Unit)? = null,
        val onSupplementalIconClick: (() -> Unit)? = null,
    ) : CarUiListItemData()

    data class ActionRadioButton(
        val title: String? = null,
        val body: String? = null,
        val icon: Painter? = null,
        val iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
        val selected: Boolean,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onSelectedChange: ((Boolean) -> Unit)? = null
    ) : CarUiListItemData()

    data class ActionSwitch(
        val title: String? = null,
        val body: String? = null,
        val icon: Painter? = null,
        val iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
        val checked: Boolean = false,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onCheckedChange: ((Boolean) -> Unit)? = null
    ) : CarUiListItemData()
}
