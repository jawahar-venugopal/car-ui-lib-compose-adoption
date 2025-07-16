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
package com.android.car.compose.ui.paintbooth.dialogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.android.car.compose.ui.paintbooth.R
import com.android.car.ui.CarUiAlertDialog
import com.android.car.ui.CarUiAlertDialogParams
import com.android.car.ui.recyclerview.CarUiCheckBoxListItem
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarNavIconType

class DialogsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    DialogsActivityScreen()
                }
            }
        }
    }
}

@Composable
fun DialogsActivityScreen() {
    var currentDialog by remember { mutableStateOf<DialogDemoType?>(null) }
    var editTextValue by remember { mutableStateOf("") }
    var singleChoiceSelectedIndex by remember { mutableIntStateOf(0) }

    val dialogList = listOf(
        DialogDemoType.Standard,
        DialogDemoType.WithIcon,
        DialogDemoType.WithEditText,
        DialogDemoType.OnlyPositive,
        DialogDemoType.NoButton,
        DialogDemoType.WithCheckbox,
        DialogDemoType.NoTitle,
        DialogDemoType.Subtitle,
        DialogDemoType.SubtitleIcon,
        DialogDemoType.LongSubtitleIcon,
        DialogDemoType.SingleChoice,
        DialogDemoType.ListNoDefault,
    )

    Column(modifier = Modifier.fillMaxSize()) {
        CarUiToolbar(
            title = stringResource(R.string.app_name),
            navIconType = CarUiToolbarNavIconType.Back,
        )
        CarUiRecyclerView(
            items = dialogList,
            itemContent = { dialogType ->
                DialogButton(
                    dialogType = dialogType,
                    onShowDialog = { selectedType -> currentDialog = selectedType }
                )
            }
        )
        DialogDemo(
            dialogType = currentDialog,
            onDismiss = { currentDialog = null },
            editTextValue = editTextValue,
            onEditTextValueChange = { editTextValue = it },
            singleChoiceSelectedIndex = singleChoiceSelectedIndex,
            onSingleChoiceSelected = { singleChoiceSelectedIndex = it },
        )
    }
}

@Composable
fun DialogButton(
    dialogType: DialogDemoType,
    onShowDialog: (DialogDemoType) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onShowDialog(dialogType)
            },
            modifier = Modifier.wrapContentWidth().semantics { contentDescription = "list_button" }
        ) {
            Text(
                text = stringResource(dialogType.labelRes),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }
    }
}


sealed class DialogDemoType(val labelRes: Int) {
    object Standard : DialogDemoType(R.string.dialog_show_dialog)
    object WithIcon : DialogDemoType(R.string.dialog_show_dialog_icon)
    object WithEditText : DialogDemoType(R.string.dialog_show_dialog_edit)
    object OnlyPositive : DialogDemoType(R.string.dialog_show_dialog_only_positive)
    object NoButton : DialogDemoType(R.string.dialog_show_dialog_no_button)
    object WithCheckbox : DialogDemoType(R.string.dialog_show_dialog_checkbox)
    object NoTitle : DialogDemoType(R.string.dialog_show_dialog_no_title)
    object Subtitle : DialogDemoType(R.string.dialog_show_subtitle)
    object SubtitleIcon : DialogDemoType(R.string.dialog_show_subtitle_and_icon)
    object LongSubtitleIcon : DialogDemoType(R.string.dialog_show_long_subtitle_and_icon)
    object SingleChoice : DialogDemoType(R.string.dialog_show_single_choice)
    object ListNoDefault : DialogDemoType(R.string.dialog_show_list_items_without_default_button)
}

@Composable
fun DialogDemo(
    dialogType: DialogDemoType?,
    onDismiss: () -> Unit,
    editTextValue: String,
    onEditTextValueChange: (String) -> Unit,
    singleChoiceSelectedIndex: Int,
    onSingleChoiceSelected: (Int) -> Unit,
) {
    if (dialogType == null) return

    when (dialogType) {
        DialogDemoType.Standard -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.standard_alert_dialog),
                message = stringResource(R.string.my_message),
                neutralButton = stringResource(R.string.neutral),
                onNeutralClick = onDismiss,
                positiveButton = stringResource(R.string.ok),
                onPositiveClick = onDismiss,
                negativeButton = stringResource(R.string.cancel),
                onNegativeClick = onDismiss,
                onDismiss = onDismiss
            )
        )

        DialogDemoType.WithIcon -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.alert_dialog_with_icon),
                message = stringResource(R.string.my_message),
                icon = painterResource(id = R.drawable.ic_tracklist),
                onDismiss = onDismiss
            )
        )

        DialogDemoType.WithEditText -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.standard_alert_dialog),
                editTextValue = editTextValue,
                editTextPrompt = stringResource(R.string.edit_me_please),
                editTextOnValueChange = onEditTextValueChange,
                positiveButton = stringResource(R.string.ok),
                onPositiveClick = onDismiss,
                onDismiss = onDismiss
            )
        )

        DialogDemoType.OnlyPositive -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.standard_alert_dialog),
                message = stringResource(R.string.my_message),
                positiveButton = stringResource(R.string.ok),
                onPositiveClick = onDismiss,
                onDismiss = onDismiss
            )
        )

        DialogDemoType.NoButton -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.standard_alert_dialog),
                onDismiss = onDismiss
            )
        )

        DialogDemoType.WithCheckbox -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.custom_dialog_box),
                content = {
                    var checked by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CarUiCheckBoxListItem(
                            title = "I am a checkbox",
                            onCheckedChange = { checked = it },
                            checked = checked
                        )
                    }
                },
                positiveButton = stringResource(R.string.ok),
                onPositiveClick = onDismiss,
                negativeButton = stringResource(R.string.cancel),
                onNegativeClick = onDismiss,
                onDismiss = onDismiss
            )
        )

        DialogDemoType.NoTitle -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                message = stringResource(R.string.no_title_message),
                positiveButton = stringResource(R.string.ok),
                onPositiveClick = onDismiss,
                negativeButton = stringResource(R.string.cancel),
                onNegativeClick = onDismiss,
                onDismiss = onDismiss
            )
        )

        DialogDemoType.Subtitle -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.my_title),
                subtitle = stringResource(R.string.my_subtitle),
                message = stringResource(R.string.my_message),
                onDismiss = onDismiss
            )
        )

        DialogDemoType.SubtitleIcon -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.my_title),
                subtitle = stringResource(R.string.my_subtitle),
                message = stringResource(R.string.my_message),
                icon = painterResource(id = R.drawable.ic_tracklist),
                onDismiss = onDismiss
            )
        )

        DialogDemoType.LongSubtitleIcon -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.long_title),
                subtitle = stringResource(R.string.long_subtitle),
                message = stringResource(R.string.my_message),
                icon = painterResource(id = R.drawable.ic_tracklist),
                onDismiss = onDismiss
            )
        )

        DialogDemoType.SingleChoice -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = stringResource(R.string.select_one_option),
                subtitle = stringResource(R.string.select_one_option_at_a_time),
                singleChoiceItems = listOf(
                    stringResource(R.string.first_item),
                    stringResource(R.string.second_item),
                    stringResource(R.string.third_item)
                ),
                singleChoiceSelectedIndex = singleChoiceSelectedIndex,
                onSingleChoiceSelect = onSingleChoiceSelected,
                onDismiss = onDismiss
            )
        )

        DialogDemoType.ListNoDefault -> CarUiAlertDialog(
            CarUiAlertDialogParams(
                show = true,
                title = "Select one option.",
                subtitle = "Only one option may be selected at a time",
                singleChoiceItems = listOf(
                    stringResource(R.string.first_item),
                    stringResource(R.string.second_item),
                    stringResource(R.string.third_item)
                ),
                onDismiss = onDismiss
            )
        )
    }
}


