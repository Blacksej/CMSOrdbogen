package dev.danieltm.cmsordbogen.Views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import dev.danieltm.cmsordbogen.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dev.danieltm.cmsordbogen.ViewModels.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostsScreen(
    mainViewModel: MainViewModel,
    swipeRefreshState: SwipeRefreshState
) {
    val postsList by mainViewModel.allPostsList.collectAsState()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        colorResource(id = R.color.top_bar_bg),
                        colorResource(id = R.color.top_bar_bg2)
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 9.dp, start = 8.dp),
            text = "ALLE INDLÆG",
            color = colorResource(id = R.color.white),
            fontSize = 20.sp
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_home))
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            // Acts as refresh for the list
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = mainViewModel::loadRecentPosts,
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = colorResource(id = R.color.refresh_bg),
                        contentColor = colorResource(id = R.color.refresh_content_color)
                    )
                }
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    val grouped = postsList.groupBy { it.type }
                    grouped.forEach { (type, posts) ->
                        stickyHeader {
                            Text(
                                text = type.uppercase(),
                                modifier = Modifier
                                    .background(color = colorResource(id = R.color.background_home))
                                    .height(30.dp)
                                    .fillMaxWidth()
                                    .padding(start = 8.dp)
                                    .offset(y = (-6).dp),
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                        items(posts) { post ->
                            PostItem(item = post)
                        }
                    }
                }
            }
        }
    }
}