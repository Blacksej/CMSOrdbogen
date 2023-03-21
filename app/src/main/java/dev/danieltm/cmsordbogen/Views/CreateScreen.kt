package dev.danieltm.cmsordbogen.Views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import dev.danieltm.cmsordbogen.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import dev.danieltm.cmsordbogen.ViewModels.CreatePostViewModel
import dev.danieltm.cmsordbogen.utilities.PostType

 // MAIN SCREEN
@Composable
fun CreateScreen(
    createPostViewModel: CreatePostViewModel
) {
    val focusManager = LocalFocusManager.current
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 9.dp, start = 10.dp, end = 10.dp),
                text = "NYT INDLÆG",
                color = colorResource(id = R.color.white),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            DropDownTypeMenu(createPostViewModel = createPostViewModel)
        }

        // CONTENT ON SCREEN
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_home))
                .padding(top = 8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                if (createPostViewModel.postTypeState.value == PostType.NEWS) {
                    CreateNewsScreen(createPostViewModel = createPostViewModel)
                } else if (createPostViewModel.postTypeState.value == PostType.PUSH) {
                    CreatePushScreen(createPostViewModel = createPostViewModel)
                } else if (createPostViewModel.postTypeState.value == PostType.EVENT) {
                    CreateEventScreen(createPostViewModel = createPostViewModel)
                } else if (createPostViewModel.postTypeState.value == PostType.ANNOUNCEMENT) {
                    CreateAnnouncementScreen(createPostViewModel = createPostViewModel)
                } else {
                    TypeNotPickedScreen()
                }
            }
        }
    }
}



 // SCREENS FOR THE SELECTED TYPE
@Composable
fun CreateNewsScreen(createPostViewModel: CreatePostViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameInputField(createPostViewModel = createPostViewModel)
        Text(
            text = "News screen",
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.black)
        )

        ImagePicker(createPostViewModel)
    }
}

@Composable
fun CreateAnnouncementScreen(createPostViewModel: CreatePostViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameInputField(createPostViewModel = createPostViewModel)
        Text(
            text = "Announcement screen",
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.black)
        )
    }
}

@Composable
fun CreateEventScreen(createPostViewModel: CreatePostViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameInputField(createPostViewModel = createPostViewModel)
        Text(
            text = "Event screen",
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.black)
        )
    }
}

@Composable
fun CreatePushScreen(createPostViewModel: CreatePostViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameInputField(createPostViewModel = createPostViewModel)
        Text(
            text = "Push screen",
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.black),
        )
    }
}



 // SCREEN IF NO TYPE IS PICKED
@Composable
fun TypeNotPickedScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Vælg en indlægs type",
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.black),
        )
    }
}



// REUSABLE COMPOSABLES
@Composable
fun DropDownTypeMenu(createPostViewModel: CreatePostViewModel) {

    var expanded by remember { mutableStateOf(false) }

    var postTypeState: PostType by createPostViewModel.postTypeState

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var selectedType by remember { mutableStateOf("") }

    val postTypes = listOf(
        PostType.NEWS,
        PostType.EVENT,
        PostType.PUSH,
        PostType.ANNOUNCEMENT
    )

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(modifier = Modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
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
        ) {
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
                        }
                        .clickable { expanded = !expanded },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White,
                        backgroundColor = Color.Transparent
                    ),
                    enabled = false,
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    label = { Text("Type") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    },
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
                            postTypeState = label
                        }) {
                            Text(text = label.toString(), color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NameInputField(createPostViewModel: CreatePostViewModel) {

    var nameTextState: String by createPostViewModel.nameTextState

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

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
            TextField(
                value = nameTextState,
                onValueChange = { nameTextState = it },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    backgroundColor = Color.Transparent
                ),
                label = { Text(text = "Navn") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
        }
    }
}

@Composable
fun ImagePicker(createPostViewModel: CreatePostViewModel) {

    var selectedImageUri by remember {
        mutableStateOf(createPostViewModel.imageUri)
    }

    var selectedImageUris by remember {
        mutableStateOf(createPostViewModel.imageUris)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri.value = uri }
    )

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris.value = uris }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.top_bar_bg2))
                ) {
                    Text(text = "Vælg et foto", color = Color.White)
                }
                Button(
                    onClick = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.top_bar_bg2))
                ) {
                    Text(text = "Vælg flere fotos", color = Color.White)
                }
            }
        }
        item {
            AsyncImage(
                model = selectedImageUri.value,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        items(selectedImageUris.value) { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}