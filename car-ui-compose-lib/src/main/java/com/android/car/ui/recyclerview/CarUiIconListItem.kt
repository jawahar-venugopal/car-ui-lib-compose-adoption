package com.android.car.ui.recyclerview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import com.android.car.ui.R

@Composable
fun CarUiIconListItem(
    title: String? = null,
    body: String? = null,
    icon: Painter? = null,
    iconType: CarUiContentListItemIconType = CarUiContentListItemIconType.STANDARD,
    trailingIcon: Painter,
    enabled: Boolean = true,
    restricted: Boolean = false,
    onClick: (() -> Unit)? = null,
    onSupplementalIconClick: (() -> Unit)? = null,
) {
    CarUiContentListItem(
        title = title,
        body = body,
        icon = icon,
        iconType = iconType,
        enabled = enabled,
        restricted = restricted,
        onClick = onClick,
        trailingContent = {
            Icon(
                painter = trailingIcon,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.car_ui_primary_icon_size))
                    .then(
                        if (enabled && !restricted && onSupplementalIconClick != null)
                            Modifier.clickable(
                                onClick = onSupplementalIconClick
                            )
                        else Modifier
                    )
            )
        }
    )
}
