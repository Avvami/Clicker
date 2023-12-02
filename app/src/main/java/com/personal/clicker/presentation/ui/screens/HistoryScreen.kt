package com.personal.clicker.presentation.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.clicker.R
import com.personal.clicker.domain.History
import com.personal.clicker.domain.util.C
import com.personal.clicker.domain.util.LocalDateTimeConverter
import com.personal.clicker.domain.util.SortType
import com.personal.clicker.presentation.UIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    historyList: List<History>,
    uiEvent: (UIEvent) -> Unit,
    isBottomSheetOpen: Boolean,
    isDeleteDialogOpen: Boolean,
    selectedHistoryItem: History?,
    sortType: MutableStateFlow<SortType>
) {
    val context = LocalContext.current
    var storedSortType by remember {
        mutableStateOf(C.getSortType(context))
    }
    val currentSortType by sortType.collectAsState()
    LaunchedEffect(currentSortType) {
        storedSortType = C.getSortType(context)
        Log.i("SortType", storedSortType.name)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (historyList.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "History is empty",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { uiEvent(UIEvent.OpenBottomSheet(true)) }
                            ) {
                                Text(text = if (storedSortType == SortType.VALUE_DESC || storedSortType == SortType.VALUE_ASC) "Value" else "Date")
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(id = if (storedSortType == SortType.VALUE_DESC || storedSortType == SortType.DATE_DESC)
                                        R.drawable.icon_arrow_downward_fill1 else R.drawable.icon_arrow_upward_fill1),
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            TextButton(
                                onClick = {
                                    uiEvent(UIEvent.ClearHistory)
                                }
                            ) {
                                Text(text = "Clear all")
                            }
                        }
                    }
                    items(
                        count = historyList.size,
                        key = { historyList[it].id },
                        itemContent = {index ->
                            val historyItem = historyList[index]
                            HistoryItem(history = historyItem, modifier = Modifier.combinedClickable(
                                onClick = { /*Do nothing*/ },
                                onLongClick = {
                                    uiEvent(UIEvent.OpenDeleteDialog(true, historyItem))
                                }
                            ))
                        }
                    )
                }
                if (isDeleteDialogOpen) {
                    DeleteDialog(
                        onConfirm = {
                            uiEvent(UIEvent.DeleteHistoryItem(selectedHistoryItem!!))
                            uiEvent(UIEvent.OpenDeleteDialog(false))
                        },
                        onDismiss = {
                            uiEvent(UIEvent.OpenDeleteDialog(false))
                        }
                    )
                }
                if (isBottomSheetOpen) {
                    BottomSheet(
                        onDismiss = { uiEvent(UIEvent.OpenBottomSheet(false)) },
                        uiEvent = uiEvent,
                        sortType = storedSortType
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItem(
    history: History,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.icon_ads_click_fill0), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = history.value.toString(), style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
        }
        LocalDateTimeConverter().objectFromString(history.date)?.format(DateTimeFormatter.ofPattern("h:mm a MM.dd.yy"))?.let { formattedString ->
            Text(text = formattedString, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
        } ?: Text(text = "Error", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun DeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete selected item?") },
        text = { Text(text = "Item will be permanently removed from the database.") },
        icon = { Icon(painter = painterResource(id = R.drawable.icon_delete_forever_fill1), contentDescription = "Delete") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    uiEvent: (UIEvent) -> Unit,
    sortType: SortType
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val sortByDate = remember { mutableStateOf(if (sortType == SortType.DATE_DESC || sortType == SortType.DATE_ASC) true else null) }
//    val sortByDateDesc = remember { mutableStateOf(sortType == SortType.DATE_DESC) }
    val sortByValue = remember { mutableStateOf(if (sortType == SortType.VALUE_DESC || sortType == SortType.VALUE_ASC) true else null) }
//    val sortByValueDesc = remember { mutableStateOf(sortType == SortType.VALUE_DESC) }
    ModalBottomSheet(onDismissRequest = { onDismiss() }, sheetState = sheetState) {
        Text(
            text = "Sort by",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        SortItem(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(shape = MaterialTheme.shapes.large)
                .background(color = sortByDate.value?.let { MaterialTheme.colorScheme.secondaryContainer }
                    ?: Color.Transparent)
                .clickable {
                    sortByDate.value.let {
                        if (it != null) sortByDate.value = !it else sortByDate.value = true
                    }
                    sortByValue.value = null
                    uiEvent(UIEvent.SortHistory(if (sortByDate.value == true) SortType.DATE_DESC else SortType.DATE_ASC))
                    C.saveSortType(
                        context,
                        if (sortByDate.value == true) SortType.DATE_DESC else SortType.DATE_ASC
                    )
                },
            sortType = "Date",
            resId = if (sortByDate.value == true) R.drawable.icon_arrow_downward_fill1 else R.drawable.icon_arrow_upward_fill1,
            iconVisible = sortByDate.value != null
        )
        SortItem(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(shape = MaterialTheme.shapes.large)
                .background(color = sortByValue.value?.let { MaterialTheme.colorScheme.secondaryContainer }
                    ?: Color.Transparent)
                .clickable {
                    sortByValue.value.let {
                        if (it != null) sortByValue.value = !it else sortByValue.value = true
                    }
                    sortByDate.value = null
                    uiEvent(UIEvent.SortHistory(if (sortByValue.value == true) SortType.VALUE_DESC else SortType.VALUE_ASC))
                    C.saveSortType(
                        context,
                        if (sortByValue.value == true) SortType.VALUE_DESC else SortType.VALUE_ASC
                    )
                },
            sortType = "Value",
            resId = if (sortByValue.value == true) R.drawable.icon_arrow_downward_fill1 else R.drawable.icon_arrow_upward_fill1,
            iconVisible = sortByValue.value != null
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SortItem(
    modifier: Modifier,
    sortType: String,
    @DrawableRes
    resId: Int,
    iconVisible: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            painter = painterResource(id = resId),
            contentDescription = "Sort type",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(18.dp)
                .alpha(if (iconVisible) 1f else 0f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = sortType, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium)
    }
}