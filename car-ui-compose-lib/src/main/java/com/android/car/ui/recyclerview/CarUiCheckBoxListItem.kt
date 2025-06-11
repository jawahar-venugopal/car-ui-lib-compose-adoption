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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.android.car.ui.R

@Composable
fun CarUiCheckBoxListItem(
    title: String,
    checked: Boolean,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    val horizontalPadding = dimensionResource(id = R.dimen.car_ui_list_item_horizontal_padding)
    val verticalPadding = dimensionResource(id = R.dimen.car_ui_list_item_vertical_padding)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (enabled && !restricted && onCheckedChange != null) Modifier.clickable {
                onCheckedChange(
                    !checked
                )
            } else Modifier)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    if (enabled && !restricted && onCheckedChange != null) onCheckedChange(
                        it
                    )
                },
                enabled = enabled && !restricted
            )
        }
    }
}
