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
package com.android.car.compose.ui.paintbooth.preferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import com.android.car.compose.ui.paintbooth.R
import com.android.car.ui.preference.CarUiCheckboxPreference
import com.android.car.ui.preference.CarUiEditTextPreference
import com.android.car.ui.preference.CarUiListPreference
import com.android.car.ui.preference.CarUiPreference
import com.android.car.ui.preference.CarUiPreferenceCategory
import com.android.car.ui.preference.CarUiSwitchPreference
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarNavIconType

class PreferenceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    PreferenceDemoScreen()
                }
            }
        }
    }
}

@Composable
fun PreferenceDemoScreen() {
    val prefItems = listOf<@Composable () -> Unit>(
        {
            CarUiPreferenceCategory(title = stringResource(R.string.basic_preferences)) {
                CarUiPreference(
                    title = AnnotatedString(stringResource(R.string.title_basic_preference)),
                    summary = stringResource(R.string.summary_basic_preference)
                )
                CarUiPreference(
                    title = AnnotatedString.fromHtml(htmlString = "<b>Very</b> <i>stylish</i> <u>preference</u>"),
                    summary = stringResource(R.string.summary_stylish_preference),
                )
                CarUiPreference(
                    title = AnnotatedString(stringResource(R.string.title_icon_preference)),
                    summary = stringResource(R.string.summary_icon_preference),
                    icon = painterResource(R.drawable.ic_settings_wifi)
                )
                CarUiPreference(
                    title = AnnotatedString(stringResource(R.string.title_single_line_title_preference)),
                    summary = stringResource(R.string.summary_single_line_title_preference),
                )
                CarUiPreference(
                    title = AnnotatedString(stringResource(R.string.title_single_line_no_summary)),
                )
            }
        },
        {
            CarUiPreferenceCategory(title = stringResource(R.string.widgets)) {
                CarUiCheckboxPreference(
                    key = "checkbox",
                    title = stringResource(R.string.title_checkbox_preference),
                    summary = stringResource(R.string.summary_checkbox_preference)
                )
                CarUiSwitchPreference(
                    key = "switch",
                    title = stringResource(R.string.title_switch_preference),
                    summary = stringResource(R.string.summary_switch_preference)
                )
            }
        },
        {
            CarUiPreferenceCategory(title = stringResource(R.string.dialogs)) {
                CarUiEditTextPreference(
                    key = "edittext",
                    title = stringResource(R.string.title_edittext_preference),
                    dialogTitle = stringResource(R.string.dialog_title_edittext_preference),
                    useSimpleSummaryProvider = true
                )
                CarUiListPreference(
                    key = "list",
                    title = stringResource(R.string.title_list_preference),
                    dialogTitle = stringResource(R.string.dialog_title_list_preference),
                    entries = stringArrayResource(R.array.entries).toList(),
                    entryValues = stringArrayResource(R.array.entry_values).toList(),
                    useSimpleSummaryProvider = true
                )
            }
        }
    )
    Column(modifier = Modifier.fillMaxSize()) {
        CarUiToolbar(
            title = stringResource(R.string.preferences_screen_title),
            navIconType = CarUiToolbarNavIconType.Back,
        )
        CarUiRecyclerView(
            items = prefItems,
            itemContent = { item ->
                item()
            }
        )
    }
}
