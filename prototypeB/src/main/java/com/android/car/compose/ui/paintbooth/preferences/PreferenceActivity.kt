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

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "car_ui_lib_prefs")

class PreferenceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PreferenceDemoScreen()
                }
            }
        }
    }
}

@Composable
fun PreferenceDemoScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // State for each preference
    var checkboxChecked by rememberSaveable { mutableStateOf(false) }
    var switchChecked by rememberSaveable { mutableStateOf(false) }
    var editTextValue by rememberSaveable { mutableStateOf("") }
    var listPrefValue by rememberSaveable { mutableStateOf("") }
    var prefsLoaded by remember { mutableStateOf(false) }

    // Load preferences only once on first composition
    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first()
        checkboxChecked = prefs[booleanPreferencesKey("checkbox")] ?: false
        switchChecked = prefs[booleanPreferencesKey("switch")] ?: false
        editTextValue = prefs[stringPreferencesKey("edittext")] ?: ""
        listPrefValue = prefs[stringPreferencesKey("list")] ?: ""
        prefsLoaded = true
    }

    // Handlers to save each preference to DataStore
    fun saveCheckbox(checked: Boolean) {
        checkboxChecked = checked
        scope.launch {
            context.dataStore.edit { it[booleanPreferencesKey("checkbox")] = checked }
        }
    }

    fun saveSwitch(checked: Boolean) {
        switchChecked = checked
        scope.launch {
            context.dataStore.edit { it[booleanPreferencesKey("switch")] = checked }
        }
    }

    fun saveEditText(value: String) {
        editTextValue = value
        scope.launch {
            context.dataStore.edit { it[stringPreferencesKey("edittext")] = value }
        }
    }

    fun saveListPref(value: String) {
        listPrefValue = value
        scope.launch {
            context.dataStore.edit { it[stringPreferencesKey("list")] = value }
        }
    }

    // Wait for preferences to load before rendering
    if (!prefsLoaded) return

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
                    title = stringResource(R.string.title_checkbox_preference),
                    summary = stringResource(R.string.summary_checkbox_preference),
                    checked = checkboxChecked,
                    onCheckedChange = ::saveCheckbox
                )
                CarUiSwitchPreference(
                    title = stringResource(R.string.title_switch_preference),
                    summary = stringResource(R.string.summary_switch_preference),
                    checked = switchChecked,
                    onCheckedChange = ::saveSwitch
                )
            }
        },
        {
            CarUiPreferenceCategory(title = stringResource(R.string.dialogs)) {
                CarUiEditTextPreference(
                    title = stringResource(R.string.title_edittext_preference),
                    dialogTitle = stringResource(R.string.dialog_title_edittext_preference),
                    value = editTextValue,
                    onValueChange = ::saveEditText,
                    useSimpleSummaryProvider = true
                )
                CarUiListPreference(
                    title = stringResource(R.string.title_list_preference),
                    dialogTitle = stringResource(R.string.dialog_title_list_preference),
                    entries = stringArrayResource(R.array.entries).toList(),
                    entryValues = stringArrayResource(R.array.entry_values).toList(),
                    selectedValue = listPrefValue,
                    onValueChange = ::saveListPref,
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
            itemContent = { item -> item() }
        )
    }
}
