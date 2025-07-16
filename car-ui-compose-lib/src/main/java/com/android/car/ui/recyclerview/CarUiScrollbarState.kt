package com.android.car.ui.recyclerview

data class CarUiScrollbarState(
    val firstVisibleItemIndex: Int,
    val visibleItemsCount: Int,
    val totalItemsCount: Int,
    val canScrollForward: Boolean,
    val canScrollBackward: Boolean
)
