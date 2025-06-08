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
package com.android.car.ui

import android.text.InputFilter
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

data class CarUiAlertDialogParams(
    val show: Boolean = false,
    val title: String? = null,
    val subtitle: String? = null,
    val message: String? = null,
    val icon: Painter? = null,

    // EditText
    val editTextValue: String? = null,
    val editTextPrompt: String? = null,
    val editTextOnValueChange: ((String) -> Unit)? = null,
    val editTextInputFilters: List<InputFilter>? = null,
    val editTextKeyboardType: KeyboardType = KeyboardType.Text,
    val editTextVisualTransformation: VisualTransformation = VisualTransformation.None,

    // Single-choice
    val singleChoiceItems: List<String>? = null,
    val singleChoiceSelectedIndex: Int? = null,
    val onSingleChoiceSelect: ((Int) -> Unit)? = null,

    // Multi-choice
    val multiChoiceItems: List<String>? = null,
    val multiChoiceChecked: List<Boolean>? = null,
    val onMultiChoiceChange: ((Int, Boolean) -> Unit)? = null,

    // Custom content slot
    val content: (@Composable (androidx.compose.foundation.layout.ColumnScope.() -> Unit))? = null,

    // Buttons
    val positiveButton: String? = null,
    val onPositiveClick: (() -> Unit)? = null,
    val negativeButton: String? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val neutralButton: String? = null,
    val onNeutralClick: (() -> Unit)? = null,

    // Dismiss
    val dismissOnClickOutside: Boolean = true,
    val onDismiss: (() -> Unit)? = null,
)
