package dev.danieltm.cmsordbogen.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.danieltm.cmsordbogen.R
import dev.danieltm.cmsordbogen.Models.PostModel

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
                    .align(Alignment.CenterVertically)
                    .size(200.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = item.title,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "ID: " + item.id.toString(),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}