package dev.danieltm.cmsordbogen.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import dev.danieltm.cmsordbogen.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import dev.danieltm.cmsordbogen.ViewModels.CreatePostViewModel
import dev.danieltm.cmsordbogen.utilities.PostType

@Composable
fun CreateScreen(
    createPostViewModel: CreatePostViewModel
) {
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
            text = "NYT INDLÆG",
            color = colorResource(id = R.color.white),
            fontSize = 20.sp
        )

        // CONTENT ON SCREEN
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_home))
                .padding(top = 8.dp),
            contentAlignment = Alignment.TopCenter
        ){
            Column() {
                DropDownTypeMenu(createPostViewModel = createPostViewModel)
                NameInputField(createPostViewModel = createPostViewModel)
            }
        }
    }
}

@Composable
fun DropDownTypeMenu(createPostViewModel: CreatePostViewModel){

    var expanded by remember { mutableStateOf(false) }
    var postTypeState: PostType by createPostViewModel.postTypeState

    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    var selectedType by remember { mutableStateOf("") }

    val postTypes = listOf(
        PostType.NEWS,
        PostType.EVENT,
        PostType.PUSH,
        PostType.ANNOUNCEMENT
    )

    val icon = if (expanded){
        Icons.Filled.KeyboardArrowUp
    }else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(modifier = Modifier.padding(8.dp)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.top_bar_bg))
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
        ){
            Column() {
                TextField(
                    value = selectedType,
                    onValueChange = { selectedType = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            textFieldSize = coordinates.size.toSize()
                        },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White,
                        backgroundColor = Color.Transparent
                    ),
                    label = {Text("Type")},
                    trailingIcon = {
                        Icon(icon,"contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .background(colorResource(id = R.color.top_bar_bg))
                ) {
                    postTypes.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedType = label.toString()
                            expanded = false
                        }) {
                            Text(text = label.toString(), color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NameInputField(createPostViewModel: CreatePostViewModel){

    var nameTextState: String by createPostViewModel.nameTextState

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
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
    ) {
        Column() {
            Text(
                text = "Navn",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.5f)
            )

            TextField(
                value = nameTextState,
                onValueChange = { nameTextState = it },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    backgroundColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}