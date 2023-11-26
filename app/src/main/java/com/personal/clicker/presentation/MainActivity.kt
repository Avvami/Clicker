package com.personal.clicker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.clicker.ClickerApplication
import com.personal.clicker.R
import com.personal.clicker.domain.TabItem
import com.personal.clicker.presentation.ui.screens.ClickerScreen
import com.personal.clicker.presentation.ui.screens.HistoryScreen
import com.personal.clicker.presentation.ui.theme.ClickerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ClickerTheme {
                val mainViewModel = viewModel<ClickerViewModel>(
                    factory = viewModelFactory {
                        ClickerViewModel(ClickerApplication.appModule.clickerRepository)
                    }
                )
                val tabItems = listOf(
                    TabItem(title = "Clicker", unselectedIcon = R.drawable.icon_mouse_fill0, selectedIcon = R.drawable.icon_mouse_fill1),
                    TabItem(title = "History", unselectedIcon = R.drawable.icon_history_edu_fill0, selectedIcon = R.drawable.icon_history_edu_fill1)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "You can do better") },
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }
                    ) { innerPadding ->
                        val pageCount = tabItems.size
                        val pagerState = rememberPagerState(initialPage = 0)
                        LaunchedEffect(mainViewModel.selectedTabIndex) {
                            pagerState.animateScrollToPage(mainViewModel.selectedTabIndex)
                        }
                        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                            if (!pagerState.isScrollInProgress)
                                mainViewModel.uiEvent(UIEvent.SetSelectedTabIndex(pagerState.currentPage))
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            TabRow(selectedTabIndex = mainViewModel.selectedTabIndex, containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                                tabItems.forEachIndexed { index, tabItem ->
                                    Tab(
                                        selected = index == mainViewModel.selectedTabIndex,
                                        onClick = {
                                            mainViewModel.uiEvent(
                                                UIEvent.SetSelectedTabIndex(
                                                    index
                                                )
                                            )
                                        },
                                        text = {
                                            Text(text = tabItem.title)
                                        },
                                        icon = {
                                            AnimatedContent(
                                                targetState = index == mainViewModel.selectedTabIndex,
                                                label = "",
                                                transitionSpec = { scaleIn() with scaleOut() }
                                            ) {
                                                Icon(painter = painterResource(id = if (it) tabItem.selectedIcon else tabItem.unselectedIcon), contentDescription = tabItem.title)
                                            }
                                        }
                                    )
                                }
                            }
                            HorizontalPager(
                                pageCount = pageCount,
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                            ) { index ->
                                when (index) {
                                    0 -> {
                                        ClickerScreen(
                                            clickerValue = mainViewModel.clickerValue,
                                            uiEvent = mainViewModel::uiEvent,
                                            modifier = Modifier
                                        )
                                    }
                                    1 -> {
                                        HistoryScreen(uiEvent = mainViewModel::uiEvent)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}