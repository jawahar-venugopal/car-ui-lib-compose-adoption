package com.android.car.ui.toolbar

import androidx.annotation.DrawableRes
import com.android.car.ui.R

enum class CarUiMenuItemDisplayBehavior { Always, IfRoom, Never }

data class CarUiToolbarMenuItem(
    val title: String? = null,
    @DrawableRes val iconRes: Int? = null,
    val onClick: (() -> Unit)? = null,
    val checkable: Boolean = false,
    val checked: Boolean = false,
    val onCheckedChange: ((Boolean) -> Unit)? = null,
    val activatable: Boolean = false,
    val activated: Boolean = false,
    val onActivatedChange: ((Boolean) -> Unit)? = null,
    val displayBehavior: CarUiMenuItemDisplayBehavior = CarUiMenuItemDisplayBehavior.Always,
    val enabled: Boolean = true,
    val visible: Boolean = true,
    val isSearch: Boolean = false,
    val showIconAndTitle: Boolean = false,
    val tinted: Boolean = true,
    val restricted: Boolean = false
) {
    companion object {
        fun search(
            title: String? = null,
            enabled: Boolean = true,
            visible: Boolean = true,
            showIconAndTitle: Boolean = false,
            tinted: Boolean = true,
            restricted: Boolean = false,
            onClick: (() -> Unit)? = null,
        ) = CarUiToolbarMenuItem(
            title = title,
            enabled = enabled,
            visible = visible,
            showIconAndTitle = showIconAndTitle,
            tinted = tinted,
            restricted = restricted,
            iconRes = R.drawable.car_ui_icon_search,
            isSearch = true,
            onClick = onClick
        )

        fun settings(
            title: String? = null,
            enabled: Boolean = true,
            visible: Boolean = true,
            showIconAndTitle: Boolean = false,
            tinted: Boolean = true,
            restricted: Boolean = false,
            onClick: (() -> Unit)? = null
        ) = CarUiToolbarMenuItem(
            title = title,
            enabled = enabled,
            visible = visible,
            showIconAndTitle = showIconAndTitle,
            tinted = tinted,
            restricted = restricted,
            iconRes = R.drawable.car_ui_icon_settings,
            onClick = onClick
        )

        fun icon(
            enabled: Boolean = true,
            visible: Boolean = true,
            tinted: Boolean = true,
            restricted: Boolean = false,
            @DrawableRes iconRes: Int,
            onClick: (() -> Unit)? = null,
        ) = CarUiToolbarMenuItem(
            enabled = enabled,
            visible = visible,
            tinted = tinted,
            restricted = restricted,
            iconRes = iconRes,
            onClick = onClick,
        )

        fun text(
            title: String,
            enabled: Boolean = true,
            visible: Boolean = true,
            tinted: Boolean = true,
            restricted: Boolean = false,
            onClick: (() -> Unit)? = null,
        ) = CarUiToolbarMenuItem(
            title = title,
            visible = visible,
            restricted = restricted,
            tinted = tinted,
            onClick = onClick,
            enabled = enabled
        )

        fun iconAndText(
            @DrawableRes iconRes: Int,
            title: String,
            enabled: Boolean = true,
            visible: Boolean = true,
            tinted: Boolean = true,
            restricted: Boolean = false,
            onClick: (() -> Unit)? = null
        ) = CarUiToolbarMenuItem(
            iconRes = iconRes,
            title = title,
            visible = visible,
            tinted = tinted,
            restricted = restricted,
            showIconAndTitle = true,
            onClick = onClick,
            enabled = enabled
        )

        fun checkable(
            @DrawableRes iconRes: Int? = null,
            title: String? = null,
            visible: Boolean = true,
            showIconAndTitle: Boolean = false,
            tinted: Boolean = true,
            restricted: Boolean = false,
            checked: Boolean,
            enabled: Boolean = true,
            onCheckedChange: (Boolean) -> Unit
        ) = CarUiToolbarMenuItem(
            title = title,
            iconRes = iconRes,
            visible = visible,
            showIconAndTitle = showIconAndTitle,
            tinted = tinted,
            restricted = restricted,
            checkable = true,
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )

        fun activatable(
            title: String? = null,
            @DrawableRes iconRes: Int? = null,
            visible: Boolean = true,
            showIconAndTitle: Boolean = false,
            tinted: Boolean = true,
            restricted: Boolean = false,
            enabled: Boolean = true,
            activated: Boolean,
            onActivatedChange: (Boolean) -> Unit,
        ) = CarUiToolbarMenuItem(
            visible = visible,
            showIconAndTitle = showIconAndTitle,
            tinted = tinted,
            restricted = restricted,
            iconRes = iconRes,
            title = title,
            activatable = true,
            activated = activated,
            onActivatedChange = onActivatedChange,
            enabled = enabled
        )
    }
}
