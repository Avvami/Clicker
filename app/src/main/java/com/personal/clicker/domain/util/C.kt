package com.personal.clicker.domain.util

import android.content.Context

object C {
    private const val PREFERENCE_STORAGE_NAME = "com_personal_clicker_shared_pref"
    private const val SORT_TYPE = "SORT_TYPE"

    fun saveSortType(context: Context, sortType: SortType) {
        context.getSharedPreferences(PREFERENCE_STORAGE_NAME, Context.MODE_PRIVATE).edit()
            .putString(SORT_TYPE, sortType.name).apply()
    }

    fun getSortType(context: Context): SortType {
        val sortTypeString = context.getSharedPreferences(PREFERENCE_STORAGE_NAME, Context.MODE_PRIVATE)
            .getString(SORT_TYPE, SortType.DATE_DESC.name)
        return SortType.valueOf(sortTypeString ?: SortType.DATE_DESC.name)
    }
}