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

import androidx.compose.runtime.Composable

@Composable
fun CarUiListItemDispatcher(item: CarUiListItemData) {
    when (item) {

        is CarUiListItemData.Header -> CarUiHeaderListItem(
            text = item.text,
            body = item.body
        )

        is CarUiListItemData.Content -> CarUiContentListItem(
            title = item.title,
            body = item.body,
            icon = item.icon,
            iconType = item.iconType,
            enabled = item.enabled,
            restricted = item.restricted,
            onClick = item.onClick
        )

        is CarUiListItemData.ActionCheckBox -> CarUiCheckBoxListItem(
            title = item.title,
            body = item.body,
            icon = item.icon,
            iconType = item.iconType,
            checked = item.checked,
            enabled = item.enabled,
            restricted = item.restricted,
            onCheckedChange = item.onCheckedChange
        )

        is CarUiListItemData.ActionChevron -> CarUiChevronListItem(
            title = item.title,
            body = item.body,
            icon = item.icon,
            iconType = item.iconType,
            enabled = item.enabled,
            restricted = item.restricted,
            onClick = item.onClick
        )

        is CarUiListItemData.ActionIcon -> CarUiIconListItem(
            title = item.title,
            body = item.body,
            icon = item.icon,
            iconType = item.iconType,
            trailingIcon = item.trailingIcon,
            enabled = item.enabled,
            restricted = item.restricted,
            onClick = item.onClick,
            onSupplementalIconClick = item.onSupplementalIconClick
        )

        is CarUiListItemData.ActionRadioButton -> CarUiRadioButtonListItem(
            title = item.title,
            body = item.body,
            icon = item.icon,
            iconType = item.iconType,
            selected = item.selected,
            enabled = item.enabled,
            restricted = item.restricted,
            onSelectedChange = item.onSelectedChange
        )

        is CarUiListItemData.ActionSwitch -> CarUiSwitchListItem(
            title = item.title,
            body = item.body,
            icon = item.icon,
            iconType = item.iconType,
            checked = item.checked,
            enabled = item.enabled,
            restricted = item.restricted,
            onCheckedChange = item.onCheckedChange
        )
    }
}