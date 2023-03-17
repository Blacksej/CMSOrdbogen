package dev.danieltm.cmsordbogen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.ViewModels.MainViewModel
import dev.danieltm.cmsordbogen.Views.*
import dev.danieltm.cmsordbogen.ui.theme.CMSOrdbogenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CMSOrdbogenTheme {

                val mainViewModel = viewModel<MainViewModel>()
                val navController = rememberNavController()

                val isLoading by mainViewModel.isLoading.collectAsState()
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

                ScaffoldBottomNavigationBarAndTopBar(
                    mainViewModel = mainViewModel,
                    navController = navController,
                    navHostController = navController,
                    swipeRefreshState = swipeRefreshState
                )
            }
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    swipeRefreshState: SwipeRefreshState
) {
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
fun ScaffoldBottomNavigationBarAndTopBar(
    mainViewModel: MainViewModel,
    navController: NavController,
    navHostController: NavHostController,
    swipeRefreshState: SwipeRefreshState
) {

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
        Navigation(
            navController = navHostController,
            mainViewModel,
            swipeRefreshState = swipeRefreshState
        )
    }
}

@Composable
fun PostItem(item: PostModel) {
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