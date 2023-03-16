package dev.danieltm.cmsordbogen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.danieltm.cmsordbogen.Models.BottomNavItem
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.ViewModels.MainViewModel
import dev.danieltm.cmsordbogen.ui.theme.CMSOrdbogenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val mainViewModel = viewModel<MainViewModel>()
            val navController = rememberNavController()

            CMSOrdbogenTheme {

                val isLoading by mainViewModel.isLoading.collectAsState()
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

                Scaffold(
                    topBar = {
                        TopBar(navController = navController)
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            items = mainViewModel.getNavList(),
                            navController = navController,
                            modifier = Modifier
                                /*.clip(
                                    RoundedCornerShape(
                                        topStart = 15.dp,
                                        topEnd = 15.dp
                                    )
                                )*/
                                .height(60.dp),
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) { paddingValues ->
                    Modifier.padding(paddingValues)
                    Navigation(navController = navController, mainViewModel, swipeRefreshState = swipeRefreshState)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, mainViewModel: MainViewModel, swipeRefreshState: SwipeRefreshState) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // REPRESENTS HOME SCREEN
            HomeScreen(mainViewModel.getPostsTemp(), mainViewModel, swipeRefreshState)
        }
        composable("create") {
            // REPRESENTS CREATE SCREEN
            CreateScreen()
        }
        composable("posts") {
            // REPRESENTS POSTS SCREEN
            PostsScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = colorResource(id = R.color.bottom_bar_bg),
        elevation = 5.dp

    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = colorResource(id = R.color.bottom_bar_selected),
                unselectedContentColor = colorResource(id = R.color.bottom_bar_unselected),
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge =
                                {
                                    Badge { Text(item.badgeCount.toString()) }
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                        if (selected && item.route != "create") {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ordbogen_line_white_400px),
                    contentDescription = "OrdbogenLogo",
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .size(200.dp)
                    //.clickable { navController.navigate("home") }
                )
                Text(
                    modifier = Modifier
                        .padding(start = 88.dp, bottom = 8.dp),
                    //.clickable { navController.navigate("home") },
                    text = "C M S",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.top_cms_text)
                )
            }
        },
        modifier = Modifier
            .height(80.dp)
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
            .clickable(interactionSource = interactionSource, indication = null) {
                navController.navigate("home")
            },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}

@Composable
fun ListItem(item: PostModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            .height(120.dp)
            .background(color = colorResource(id = R.color.top_bar_bg2))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ordbogen_line_white_400px),
                contentDescription = "user icon",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(CenterVertically)
                    .size(200.dp)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(CenterVertically),
                text = item.name,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(posts: List<PostModel>, mainViewModel: MainViewModel, swipeRefreshState: SwipeRefreshState) {
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
            text = "SENESTE INDLÆG",
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
                    val grouped = posts.groupBy { it.type }
                    grouped.forEach { (type, posts) ->
                        stickyHeader {
                            Text(
                                text = type.toString().uppercase(),
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
                            ListItem(item = post)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_create)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Create Screen",
            color = Color.Black,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun PostsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_posts)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Posts Screen",
            color = Color.Black,
            style = MaterialTheme.typography.h5
        )
    }
}