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
package com.android.car.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.android.car.ui.R

@SuppressLint("ConflictingOnColor")
@Composable
fun buildCarUiColors(): Colors {
    val primary = colorResource(id = R.color.car_ui_primary_color)
    val secondary = colorResource(id = R.color.car_ui_secondary_color)
    val background = colorResource(id = R.color.car_ui_activity_background_color)
    val surface = colorResource(id = R.color.car_ui_surface_color)
    val onPrimary = colorResource(id = R.color.car_ui_on_primary_color)
    val onSurface = colorResource(id = R.color.car_ui_text_color_primary)
    val onSecondary = colorResource(id = R.color.car_ui_on_secondary_color)

    return darkColors(
        primary = primary,
        secondary = secondary,
        background = background,
        surface = surface,
        onPrimary = onPrimary,
        onSurface = onSurface,
        onSecondary = onSecondary
    )
}

@Composable
fun buildCarUiTypography(): Typography {
    return Typography(
        h1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = dimensionResource(id = R.dimen.car_ui_body1_size).value.sp,
        ),
        h3 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.car_ui_body3_size).value.sp,
        ),
        subtitle1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = dimensionResource(id = R.dimen.car_ui_body3_size).value.sp
        ),
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = dimensionResource(id = R.dimen.car_ui_body1_size).value.sp
        ),
        body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = dimensionResource(id = R.dimen.car_ui_body2_size).value.sp
        ),
        button = TextStyle(
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Default,
            fontSize = dimensionResource(id = R.dimen.car_ui_button_text_size).value.sp,
        )
        // Add more styles as needed!
    )
}

@Composable
fun buildCarUiShapes(): Shapes {
    return Shapes(
        small = RoundedCornerShape(dimensionResource(id = R.dimen.car_ui_shape_small)),
        medium = RoundedCornerShape(dimensionResource(id = R.dimen.car_ui_shape_medium)),
        large = RoundedCornerShape(dimensionResource(id = R.dimen.car_ui_shape_large))
    )
}

@Composable
fun CarUiTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = buildCarUiColors(),
        typography = buildCarUiTypography(),
        shapes = buildCarUiShapes(),
        content = content
    )
}
