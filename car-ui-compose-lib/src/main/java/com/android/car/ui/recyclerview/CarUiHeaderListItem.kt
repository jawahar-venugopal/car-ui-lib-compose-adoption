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
package com.android.car.ui.recyclerview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.car.ui.R

@Composable
fun CarUiHeaderListItem(
    text: String,
    body: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.height(dimensionResource(R.dimen.car_ui_list_item_header_height)).fillMaxWidth(),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = text,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Start,
            modifier = modifier.wrapContentHeight()
        )
        if (!body.isNullOrEmpty()) {
            Text(
                text = body,
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Start,
                modifier = Modifier.wrapContentHeight()
                    .padding(start = dimensionResource(R.dimen.car_ui_list_item_text_no_icon_start_margin))
            )
        }
    }
}
