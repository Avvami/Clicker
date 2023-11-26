package com.personal.clicker.presentation.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.clicker.R
import com.personal.clicker.presentation.UIEvent
import kotlinx.coroutines.launch

@Composable
fun ClickerScreen(
    clickerValue: Int,
    uiEvent: (UIEvent) -> Unit,
    modifier: Modifier
) {
    val animatedSize = remember { Animatable(240.dp.value) }
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                text = clickerValue.toString(),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(270.dp)
                    .clip(shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(235.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            scope.launch {
                                animatedSize.animateTo(
                                    targetValue = 270.dp.value,
                                    animationSpec = tween(
                                        durationMillis = 150,
                                        easing = EaseInOut
                                    )
                                )
                                animatedSize.animateTo(
                                    targetValue = 240.dp.value,
                                    animationSpec = tween(
                                        durationMillis = 150,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            uiEvent(UIEvent.IncreaseClickValue(clickerValue + 1))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.green_forest),
                        contentDescription = "Forest",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Click me!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
                Box(modifier = Modifier
                    .size(animatedSize.value.dp)
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = CircleShape
                    )
                )
            }
        }
    }
}