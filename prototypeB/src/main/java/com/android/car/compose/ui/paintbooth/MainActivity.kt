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
package com.android.car.compose.ui.paintbooth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.car.compose.ui.paintbooth.caruirecyclerview.CarUiListItemActivity
import com.android.car.compose.ui.paintbooth.caruirecyclerview.CarUiRecyclerViewActivity
import com.android.car.compose.ui.paintbooth.caruirecyclerview.GridCarUiRecyclerViewActivity
import com.android.car.compose.ui.paintbooth.dialogs.DialogsActivity
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarNavIconType

data class ActivityEntry(val label: String, val activityClass: Class<out Activity>)

class MainActivity : ComponentActivity() {

    private val activities = listOf(
        ActivityEntry("Dialogs sample", DialogsActivity::class.java),
        ActivityEntry("List sample", CarUiRecyclerViewActivity::class.java),
        ActivityEntry("Grid sample", GridCarUiRecyclerViewActivity::class.java),
        ActivityEntry("Preferences sample", MainActivity::class.java),
        ActivityEntry("Toolbar sample", MainActivity::class.java),
        ActivityEntry("ListItem sample", CarUiListItemActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MainScreen(activities)
                }
            }
        }
    }
}

@Composable
fun MainScreen(activities: List<ActivityEntry>) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        // Top toolbar
        CarUiToolbar(
            title = stringResource(R.string.app_name),
            navIconType = CarUiToolbarNavIconType.None,
            logo = painterResource(id = R.drawable.ic_launcher)
        )
        // Main activity list
        CarUiRecyclerView(
            items = activities,
            itemContent = { entry ->
                ActivityButton(entry, context)
            }
        )

    }
}

@Composable
fun ActivityButton(entry: ActivityEntry, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                try {
                    val intent = Intent(context, entry.activityClass)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Activity not found: ${entry.label}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(text = entry.label, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        }
    }
}
