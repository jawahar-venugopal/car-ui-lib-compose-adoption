package com.android.car.ui.recyclerview

import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import com.android.car.ui.R

@Composable
fun CarUiSwitchListItem(
    title: String? = null,
    body: String? = null,
    icon: Painter? = null,
    iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
    checked: Boolean,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    CarUiContentListItem(
        title = title,
        body = body,
        icon = icon,
        iconType = iconType,
        enabled = enabled,
        restricted = restricted,
        onClick = null,
        trailingContent = {
            Switch(
                checked = checked,
                enabled = enabled && !restricted,
                onCheckedChange = {
                    if (enabled && !restricted) onCheckedChange?.invoke(it)
                },
                modifier = Modifier.scale(2.0f),
            )
        }
    )
}
