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

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CarUiEditText(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    enabled: Boolean = true,
    restricted: Boolean = false,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    onImeAction: (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Done,
) {
    val isEditable = enabled && !restricted
    TextField(
        value = value,
        onValueChange = { if (isEditable) onValueChange(it) },
        enabled = enabled,
        placeholder = { Text(hint) },
        singleLine = true,
        modifier = modifier,
        textStyle = MaterialTheme.typography.body1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardActions = KeyboardActions(
            onSearch = { onImeAction?.invoke() },
            onDone = { onImeAction?.invoke() }
        ),
        colors = TextFieldDefaults.textFieldColors(
            placeholderColor = MaterialTheme.colors.onSurface,
            textColor = MaterialTheme.colors.onSurface,
            backgroundColor = colorResource(android.R.color.transparent),
            disabledTextColor = MaterialTheme.colors.onSecondary,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            disabledIndicatorColor = MaterialTheme.colors.onSecondary,
            leadingIconColor = MaterialTheme.colors.onSurface,
            disabledLeadingIconColor = MaterialTheme.colors.onSurface.copy(alpha = 0.38f),
            trailingIconColor = MaterialTheme.colors.onSurface,
            disabledTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = 0.38f),
        ),
        readOnly = restricted
    )
}

