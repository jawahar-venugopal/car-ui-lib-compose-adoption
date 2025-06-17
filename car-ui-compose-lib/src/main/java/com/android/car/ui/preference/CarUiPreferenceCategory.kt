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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val LocalPreferenceCategoryEnabled = compositionLocalOf { true }

@Composable
fun CarUiPreferenceCategory(
    title: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    CompositionLocalProvider(LocalPreferenceCategoryEnabled provides enabled) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h3,
                color = if (enabled) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSecondary
            )
            content()
        }
    }
}
