package dev.danieltm.cmsordbogen.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.danieltm.cmsordbogen.R

@Composable
fun TopBar(navController: NavController){
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
                )
                Text(
                    modifier = Modifier
                        .padding(start = 88.dp, bottom = 8.dp),
                    text = "C M S",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.top_cms_text)
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