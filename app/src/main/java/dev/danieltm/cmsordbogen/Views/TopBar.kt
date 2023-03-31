package dev.danieltm.cmsordbogen.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.danieltm.cmsordbogen.R

@Composable
fun TopBar(navController: NavController, onNavigationClick: () -> Unit){
    val interactionSource = remember { MutableInteractionSource() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == null || currentRoute == "login") {
        return
    }

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
                )
                Text(
                    modifier = Modifier
                        .padding(start = 40.dp, bottom = 8.dp),
                    text = "C M S",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.top_cms_text)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick =  onNavigationClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        modifier = Modifier
            .height(60.dp)
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
fun DrawerHeader()
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.top_bar_bg))
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "PROFIL", fontSize = 30.sp)
    }
}

@Composable
private fun DrawerMenuItem(
    imageVector: ImageVector,
    text: String,
    onItemClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .background(colorResource(id = R.color.top_bar_bg2))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.White)
    }
}

@Composable
fun DrawerBody(navController: NavController?, closeNavDrawer: () -> Unit){
    Column {
        DrawerMenuItem(
            imageVector = Icons.Default.Face,
            text = "LOG IND",
            onItemClick = {
                navController?.navigate("login")
                closeNavDrawer()
            }
        )
    }
}