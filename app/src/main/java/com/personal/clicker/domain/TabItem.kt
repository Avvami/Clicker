package com.personal.clicker.domain

import androidx.annotation.DrawableRes

data class TabItem(
    val title: String,
    @DrawableRes
    val unselectedIcon: Int,
    @DrawableRes
    val selectedIcon: Int
)
