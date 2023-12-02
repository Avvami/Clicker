package com.personal.clicker.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@Composable
fun HistoryScreen(
    historyList: List<History>,
    uiEvent: (UIEvent) -> Unit
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
                    items(historyList) {history ->
                        HistoryItem(history = history)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(
    history: History
) {
    Row(
        modifier = Modifier
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