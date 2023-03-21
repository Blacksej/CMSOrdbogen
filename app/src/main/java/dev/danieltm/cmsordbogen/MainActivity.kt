package dev.danieltm.cmsordbogen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.danieltm.cmsordbogen.ViewModels.CreatePostViewModel
import dev.danieltm.cmsordbogen.ViewModels.MainViewModel
import dev.danieltm.cmsordbogen.Views.*
import dev.danieltm.cmsordbogen.ui.theme.CMSOrdbogenTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CMSOrdbogenTheme {

                val mainViewModel = viewModel<MainViewModel>()
                val createPostViewModel = viewModel<CreatePostViewModel>()
                val navController = rememberNavController()

                val isLoading by mainViewModel.isLoading.collectAsState()
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

                ScaffoldBottomNavigationBarAndTopBar(
                    mainViewModel = mainViewModel,
                    createPostViewModel = createPostViewModel,
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
    createPostViewModel: CreatePostViewModel,
    swipeRefreshState: SwipeRefreshState
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // REPRESENTS HOME SCREEN
            HomeScreen(mainViewModel, swipeRefreshState)
        }
        composable("create") {
            // REPRESENTS CREATE SCREEN
            CreateScreen(createPostViewModel)
        }
        composable("posts") {
            // REPRESENTS POSTS SCREEN
            PostsScreen(mainViewModel, swipeRefreshState)
        }
    }
}

@Composable
fun ScaffoldBottomNavigationBarAndTopBar(
    mainViewModel: MainViewModel,
    createPostViewModel: CreatePostViewModel,
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
            createPostViewModel,
            swipeRefreshState = swipeRefreshState
        )
    }
}