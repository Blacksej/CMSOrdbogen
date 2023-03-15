package dev.danieltm.cmsordbogen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.danieltm.cmsordbogen.Models.BottomNavItem
import dev.danieltm.cmsordbogen.ViewModels.MainViewModel
import dev.danieltm.cmsordbogen.ui.theme.CMSOrdbogenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { 
            
            val mainViewModel = viewModel<MainViewModel>()

                CMSOrdbogenTheme {

                    val navController = rememberNavController()

                    Scaffold(
                        topBar = {
                            TopBar()
                        },
                        bottomBar = {
                            BottomNavigationBar(
                                items = mainViewModel.getNavList(),
                                navController = navController,
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 15.dp,
                                            topEnd = 15.dp
                                        )
                                    )
                                    .height(70.dp),
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    ){
                            paddingValues -> Modifier.padding(paddingValues)
                        Navigation(navController = navController)
                    }
                }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){
            // REPRESENTS HOME SCREEN
            HomeScreen()
        }
        composable("create"){
            // REPRESENTS CREATE SCREEN
            CreateScreen()
        }
        composable("posts"){
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
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = colorResource(id = R.color.bottom_bar_bg),
        elevation = 5.dp

    ) {
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = colorResource(id = R.color.bottom_bar_selected),
                unselectedContentColor = colorResource(id = R.color.bottom_bar_unselected),
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if(item.badgeCount > 0){
                            BadgedBox(
                                badge =
                                {
                                    Badge{Text(item.badgeCount.toString())}
                                }
                            ){
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                            }
                        } else{
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                        if(selected && item.route != "create"){
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
fun TopBar(){
    TopAppBar(
        title = {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.ordbogen_line_white_400px),
                    contentDescription = "OrdbogenLogo",
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .size(200.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 88.dp, bottom = 8.dp),
                    text = "C M S",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
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
            ),
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}

@Composable
fun HomeScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_home)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Home Screen",
            color = Color.Black,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun CreateScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_create)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Create Screen",
            color = Color.Black,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun PostsScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_posts)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Posts Screen",
            color = Color.Black,
            style = MaterialTheme.typography.h5
        )
    }
}