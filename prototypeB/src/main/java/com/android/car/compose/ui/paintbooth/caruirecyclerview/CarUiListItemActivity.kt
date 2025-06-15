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
package com.android.car.compose.ui.paintbooth.caruirecyclerview

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.car.compose.ui.paintbooth.R
import com.android.car.ui.recyclerview.CarUiContentListItemIconType
import com.android.car.ui.recyclerview.CarUiListItemData
import com.android.car.ui.recyclerview.CarUiListItemDispatcher
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarNavIconType


class CarUiListItemActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CarUiListItemActivityScreen()
                }
            }
        }
    }
}

@Composable
fun CarUiListItemActivityScreen() {
    var radioSelectedIndex by remember { mutableIntStateOf(0) }
    var checkBoxChecked by remember { mutableStateOf(false) }
    var disabledCheckBoxChecked by remember { mutableStateOf(false) }
    var selectedCheckBoxChecked by remember { mutableStateOf(true) }
    var switchChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        CarUiToolbar(
            title = stringResource(R.string.app_name),
            navIconType = CarUiToolbarNavIconType.Back,
        )
        CarUiRecyclerView(
            items = getTestData(
                LocalContext.current, radioSelectedIndex, checkBoxChecked,
                onCheckBoxCheckedChange = { checkBoxChecked = it },
                disabledCheckBoxChecked,
                onDisabledCheckBoxCheckedChange = { disabledCheckBoxChecked = it },
                selectedCheckBoxChecked,
                onSelectedCheckBoxCheckedChange = { selectedCheckBoxChecked = it },
                switchChecked,
                onSwitchCheckedChange = { switchChecked = it }
            ) { idx ->
                radioSelectedIndex = idx
            }, itemContent = { item ->
                CarUiListItemDispatcher(item)
            })
    }
}

@Composable
fun getTestData(
    context: Context, radioSelectedIndex: Int,
    checkBoxChecked: Boolean,
    onCheckBoxCheckedChange: (Boolean) -> Unit,
    disabledCheckBoxChecked: Boolean,
    onDisabledCheckBoxCheckedChange: (Boolean) -> Unit,
    selectedCheckBoxChecked: Boolean,
    onSelectedCheckBoxCheckedChange: (Boolean) -> Unit,
    switchChecked: Boolean,
    onSwitchCheckedChange: (Boolean) -> Unit,
    onRadioChanged: (Int) -> Unit
): List<CarUiListItemData> {
    val data = mutableListOf<CarUiListItemData>()

    data.add(CarUiListItemData.Header(stringResource(R.string.first_header)))

    data.add(
        CarUiListItemData.Content(
            stringResource(R.string.test_title), stringResource(R.string.test_body)
        )
    )

    data.add(
        CarUiListItemData.Content(
            stringResource(R.string.test_title_no_body)
        )
    )

    data.add(
        CarUiListItemData.Header(
            stringResource(R.string.random_header), stringResource(R.string.header_with_body)
        )
    )

    data.add(
        CarUiListItemData.Content(
            body = stringResource(R.string.test_body_no_title)
        )
    )

    data.add(
        CarUiListItemData.Content(
            title = stringResource(R.string.test_title),
            icon = painterResource(R.drawable.ic_launcher)
        )
    )

    data.add(
        CarUiListItemData.Content(
            title = stringResource(R.string.test_title),
            body = stringResource(R.string.test_body),
            icon = painterResource(R.drawable.ic_launcher)
        )
    )

    data.add(
        CarUiListItemData.Content(
            title = stringResource(R.string.title_with_content_icon),
            icon = painterResource(R.drawable.ic_sample_logo),
            iconType = CarUiContentListItemIconType.CONTENT
        )
    )

    data.add(
        CarUiListItemData.Content(
            title = stringResource(R.string.test_title),
            body = stringResource(R.string.with_avatar_icon),
            icon = painterResource(R.drawable.ic_sample_logo),
            iconType = CarUiContentListItemIconType.AVATAR
        )
    )

    data.add(
        CarUiListItemData.Content(
            title = stringResource(R.string.test_title),
            body = stringResource(R.string.display_toast_on_click),
            icon = painterResource(R.drawable.ic_launcher),
            onClick = {
                Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
            })
    )

    data.add(
        CarUiListItemData.ActionCheckBox(
            title = stringResource(R.string.title_item_with_checkbox),
            body = stringResource(R.string.toast_on_selection_changed),
            icon = painterResource(R.drawable.ic_launcher),
            checked = checkBoxChecked,
            onCheckedChange = { isChecked ->
                onCheckBoxCheckedChange(isChecked)
                Toast.makeText(
                    context, "Item checked state is: " + isChecked, Toast.LENGTH_SHORT
                ).show()
            })
    )

    data.add(
        CarUiListItemData.ActionCheckBox(
            title = stringResource(R.string.title_with_disabled_checkbox),
            body = stringResource(R.string.click_should_have_no_effect),
            icon = painterResource(R.drawable.ic_launcher),
            enabled = false,
            checked = disabledCheckBoxChecked,
            onCheckedChange = { isChecked ->
                onDisabledCheckBoxCheckedChange(isChecked)
                Toast.makeText(
                    context, "Item checked state is: " + isChecked, Toast.LENGTH_SHORT
                ).show()
            })
    )

    data.add(
        CarUiListItemData.ActionSwitch(
            body = stringResource(R.string.body_item_with_switch),
            icon = painterResource(R.drawable.ic_launcher),
            checked = switchChecked,
            onCheckedChange = { isChecked ->
                onSwitchCheckedChange(isChecked)
                Toast.makeText(
                    context, "Click on item with switch", Toast.LENGTH_SHORT
                ).show()
            })
    )

    data.add(
        CarUiListItemData.ActionCheckBox(
            title = stringResource(R.string.title_item_with_checkbox),
            body = stringResource(R.string.item_initially_checked),
            icon = painterResource(R.drawable.ic_launcher),
            checked = selectedCheckBoxChecked,
            onCheckedChange = { isChecked ->
                onSelectedCheckBoxCheckedChange(isChecked)
            })
    )

    data.add(
        CarUiListItemData.ActionRadioButton(
            title = stringResource(R.string.title_item_with_radio_button),
            body = stringResource(R.string.item_initially_checked),
            selected = radioSelectedIndex == 0,
            onSelectedChange = { isSelected ->
                if (isSelected) onRadioChanged(0)
            }
        ))

    data.add(
        CarUiListItemData.ActionRadioButton(
            title = stringResource(R.string.item_mutually_exclusive_with_item_above),
            icon = painterResource(R.drawable.ic_launcher),
            selected = radioSelectedIndex == 1,
            onSelectedChange = { isSelected ->
                if (isSelected) onRadioChanged(1)
            }
        ))

    data.add(
        CarUiListItemData.ActionIcon(
            title = stringResource(R.string.supplemental_icon_with_listener),
            body = stringResource(R.string.test_body),
            icon = painterResource(R.drawable.ic_launcher),
            iconType = CarUiContentListItemIconType.CONTENT,
            trailingIcon = painterResource(R.drawable.ic_launcher),
            onClick = {
                Toast.makeText(
                    context, "Clicked item",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onSupplementalIconClick = {
                Toast.makeText(
                    context, "Clicked supplemental icon",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ))

    data.add(
        CarUiListItemData.ActionChevron(
            title = stringResource(R.string.item_with_chevron),
            body = stringResource(R.string.test_body),
        )
    )
    return data
}
