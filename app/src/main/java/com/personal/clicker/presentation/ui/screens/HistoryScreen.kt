package com.personal.clicker.presentation.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.clicker.R
import com.personal.clicker.domain.History
import com.personal.clicker.domain.util.LocalDateTimeConverter
import com.personal.clicker.presentation.UIEvent
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    historyList: List<History>,
    uiEvent: (UIEvent) -> Unit,
    isBottomSheetOpen: Boolean,
    isDeleteDialogOpen: Boolean,
    selectedHistoryItem: History?
) {
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
                                onClick = {}
                            ) {
                                Text(text = "Value")
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_arrow_downward_fill1),
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
                    items(historyList) {history ->
                        HistoryItem(history = history, modifier = Modifier.combinedClickable(
                            onClick = { /*Do nothing*/ },
                            onLongClick = {
                                uiEvent(UIEvent.OpenDeleteDialog(true, history))
                            }
                        ))
                    }
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
        title = {
            Text(text = "Delete selected item?")
        },
        text = {
            Text(text = "Item will be permanently removed from the database.")
        },
        icon = {
            Icon(painter = painterResource(id = R.drawable.icon_delete_forever_fill1), contentDescription = "Delete")
        }
    )
}