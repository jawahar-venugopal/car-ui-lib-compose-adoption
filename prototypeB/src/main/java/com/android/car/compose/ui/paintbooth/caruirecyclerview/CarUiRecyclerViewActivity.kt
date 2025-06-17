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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.car.compose.ui.paintbooth.R
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarNavIconType


class CarUiRecyclerViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CarUiRecyclerViewScreen()
                }
            }
        }
    }
}

@Composable
fun CarUiRecyclerViewScreen() {
    val context = LocalContext.current
    val data = remember {
        List(101) { i -> "${context.getString(R.string.test_data)}$i" }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CarUiToolbar(
            title = stringResource(R.string.app_name),
            navIconType = CarUiToolbarNavIconType.Back,
        )
        CarUiRecyclerView(
            items = data,
            itemContent = { item ->
                TextView(item)
            }
        )
    }
}

@Composable
internal fun TextView(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        )
    }
}
