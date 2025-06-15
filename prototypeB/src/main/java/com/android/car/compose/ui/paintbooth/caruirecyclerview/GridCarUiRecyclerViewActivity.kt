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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.car.compose.ui.paintbooth.R
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.recyclerview.CarUiRecyclerViewLayoutStyle
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarNavIconType


class GridCarUiRecyclerViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    GridCarUiRecyclerViewScreen()
                }
            }
        }
    }
}

@Composable
fun GridCarUiRecyclerViewScreen() {
    val data = remember {
        List(201) { i -> "data$i" }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        // Top toolbar
        CarUiToolbar(
            title = stringResource(R.string.app_name),
            navIconType = CarUiToolbarNavIconType.Back,
        )
        // Main activity list
        CarUiRecyclerView(
            items = data,
            layoutStyle = CarUiRecyclerViewLayoutStyle.GRID,
            numOfColumns = 4,
            itemContent = { item ->
                TextView(item)
            }
        )
    }
}
