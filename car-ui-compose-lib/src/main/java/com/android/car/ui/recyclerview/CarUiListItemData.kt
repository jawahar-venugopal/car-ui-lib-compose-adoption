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
    data class Content(
        val title: String,
        val body: String? = null,
        val icon: Painter? = null,
        val iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
        val chevron: Boolean = false,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onClick: (() -> Unit)? = null
    ) : CarUiListItemData()

    data class Header(
        val text: String
    ) : CarUiListItemData()

    data class CheckBox(
        val title: String,
        val checked: Boolean,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onCheckedChange: ((Boolean) -> Unit)? = null
    ) : CarUiListItemData()

    data class RadioButton(
        val title: String,
        val selected: Boolean,
        val enabled: Boolean = true,
        val restricted: Boolean = false,
        val onClick: (() -> Unit)? = null
    ) : CarUiListItemData()
}
