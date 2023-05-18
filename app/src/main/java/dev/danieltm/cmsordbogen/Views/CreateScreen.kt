package dev.danieltm.cmsordbogen.Views

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Paint.Align
import android.icu.util.Calendar
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.ViewModels.CreatePostViewModel
import dev.danieltm.cmsordbogen.utilities.PostType
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(70.dp)
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


        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_home))
        ) {

            // CONTENT ON SCREEN
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(top = 8.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                Column(

                    // IntrinsiczSize provides information ot the parent
                    // about the the max or min width/height
                    // of it's widest or or tallest child
                    Modifier
                        .height(IntrinsicSize.Min)
                ) {
                    if (createPostViewModel.postTypeState.value == "NYHED") {
                        CreateNewsScreen(createPostViewModel = createPostViewModel)
                    } else if (createPostViewModel.postTypeState.value == "PUSH") {
                        CreatePushScreen(createPostViewModel = createPostViewModel)
                    } else if (createPostViewModel.postTypeState.value == "EVENT") {
                        CreateEventScreen(createPostViewModel = createPostViewModel)
                    } else if (createPostViewModel.postTypeState.value == "ANNONCERING") {
                        CreateAnnouncementScreen(createPostViewModel = createPostViewModel)
                    } else {
                        TypeNotPickedScreen()
                    }
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

        // Dropdown menu for choosing website composable
        DropDownSiteMenu(createPostViewModel = createPostViewModel)

        // Choose start date and end date for post composable
        StartDatePicker(createPostViewModel = createPostViewModel)
        EndDatePicker(createPostViewModel = createPostViewModel)

        // Title field composable
        NameInputField(createPostViewModel = createPostViewModel)

        // Body/Text field composable
        BodyInputField(createPostViewModel = createPostViewModel)

        // Choose an image composable
        ImagePicker(createPostViewModel = createPostViewModel)

        // Submit button composable
        SubmitPostButton(createPostViewModel = createPostViewModel)


    }
}

suspend fun submitPost(createPostViewModel: CreatePostViewModel) {
    createPostViewModel.createPost()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SubmitPostButton(createPostViewModel: CreatePostViewModel) {
    Column(
        modifier = Modifier
            .padding(bottom = 60.dp)
    ) {
        val context = LocalContext.current
        val openDialog = remember { mutableStateOf(false) }
        val fontSize = 18
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.top_bar_bg)),
            onClick =
            {
                // Check if sites, title or body is empty.
                if (createPostViewModel.sites.value != emptyList<String>() &&
                    createPostViewModel.titleTextState.value != "" &&
                    createPostViewModel.bodyTextState.value != ""
                ) {
                    openDialog.value = true
                } else {
                    Toast.makeText(
                        context,
                        "FEJL: Udfyld venligst som minimum\ntitel, sider og indhold",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) {
            Text(text = "SKAB INDLÆG")
        }

        if (openDialog.value) {
            Dialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                content = {
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.top_bar_bg2))
                            .verticalScroll(rememberScrollState())
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            text = "Bekræft indlæg",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            value = createPostViewModel.postTypeState.value,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = fontSize.sp),
                            label = { Text(text = "Type") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                backgroundColor = Color.DarkGray
                            )
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            value = createPostViewModel.sites.toString(),
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = fontSize.sp),
                            label = { Text(text = "Sider") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                backgroundColor = Color.DarkGray
                            )
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            value = createPostViewModel.titleTextState.value,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = fontSize.sp),
                            label = { Text(text = "Titel") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                backgroundColor = Color.DarkGray
                            )
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            value = createPostViewModel.bodyTextState.value,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = fontSize.sp),
                            label = { Text(text = "Indhold") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                backgroundColor = Color.DarkGray
                            )
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            value = createPostViewModel.postStartDateState.value.toString(),
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = fontSize.sp),
                            label = { Text(text = "Start dato") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                backgroundColor = Color.DarkGray
                            )
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            value = createPostViewModel.postEndDateState.value.toString(),
                            onValueChange = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = fontSize.sp),
                            label = { Text(text = "Slut dato") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                backgroundColor = Color.DarkGray
                            )
                        )
                        if (createPostViewModel.imageUri.value != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,

                                ) {

                                AsyncImage(
                                    model = createPostViewModel.imageUri.value,
                                    contentDescription = "PostImage",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                        }
                        Row() {
                            // Dismiss button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f)
                                    .padding(4.dp),
                                onClick = {
                                    openDialog.value = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.top_bar_bg
                                    )
                                )
                            ) {
                                Text(
                                    text = "Fortryd",
                                    fontSize = fontSize.sp,
                                    color = Color.White
                                )
                            }
                            // Confirm button
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f)
                                    .padding(4.dp),
                                onClick = {
                                    openDialog.value = false

                                    CoroutineScope(Dispatchers.IO).launch {
                                        submitPost(createPostViewModel)
                                        resetPostScreen(createPostViewModel)
                                    }

                                    Toast.makeText(context, "Indlæg oprettet", Toast.LENGTH_LONG)
                                        .show()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.top_bar_bg
                                    )
                                )
                            ) {
                                Text(
                                    text = "Bekræft",
                                    fontSize = fontSize.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            )
        }
    }
}

fun resetPostScreen(createPostViewModel: CreatePostViewModel){
    createPostViewModel.clearPostValues()
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

    var postTypeState: String by createPostViewModel.postTypeState

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var selectedType by remember { mutableStateOf(postTypeState) }

    val danishTypesList: List<String>

    // Creates a list of all the possible posttypes in a list
    val postTypes = listOf(
        PostType.NEWS,
        PostType.EVENT,
        PostType.PUSH,
        PostType.ANNOUNCEMENT
    )

    // Converts the enums above to the danish equivalent
    danishTypesList = createPostViewModel.convertEnumsToDanish(postTypes)

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
                    danishTypesList.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedType = label
                            expanded = false
                            postTypeState = label
                        }) {
                            Text(text = label, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropDownSiteMenu(createPostViewModel: CreatePostViewModel) {

    var expanded by remember { mutableStateOf(false) }

    val sites by createPostViewModel.sites.collectAsState()
    val sitesMutable = sites.toMutableList()

    //var tempDisplaySite: String by createPostViewModel.tempDisplaySiteState

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    //var selectedSite by remember { mutableStateOf("VÆLG SIDE(R)") }

    val listOfPossibleSites = listOf<String>(
        "Ordbogen.com",
        "ABC.Ordbogen.com",
        "Grammatip.com"
    )

    val checkedValue = remember { mutableStateOf(false) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
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
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
            ) {
                listOfPossibleSites.forEach() { site ->
                    Row() {
                        Text(
                            modifier = Modifier
                                .padding(top = 5.dp),
                            text = site,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Switch(
                            checked = sites.contains(site),
                            onCheckedChange = {
                                //checkedValue.value = it
                                if (!sites.contains(site)) {
                                    createPostViewModel.addSiteToList(site)
                                } else if (sites.contains(site)) {
                                    createPostViewModel.removeSiteFromList(site)
                                }
                            },
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                    }
                    /*Button(
                        modifier = Modifier
                            .padding(start = 0.dp)
                            .fillMaxWidth(),
                        enabled = selected,
                        onClick = {
                            createPostViewModel.addSiteToList(site)
                            selected = !selected
                                  },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.top_bar_bg))
                    ) {
                        Text(text = site)
                    }*/
                }
                /*TextField(
                    value = "Vælg side/sider",
                    onValueChange = { },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable { expanded = !expanded }
                        .height(55.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White,
                        backgroundColor = Color.Transparent
                    ),
                    enabled = false,
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    //label = { Text("Vælg side(r)") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    },
                )*/

                /*DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .background(colorResource(id = R.color.top_bar_bg))
                ) {
                    listOfPossibleSites.forEach { label ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            // Checks if the list already contains the site.
                            // Makes sure we don't get a duplicate of a site.
                            if (!sites.contains(label)) {
                                createPostViewModel.addSiteToList(label)
                            }
                        }) {
                            Text(text = label, color = Color.White)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    sites.forEach { site ->
                        Text(
                            text = site,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable(onClick = { createPostViewModel.removeSiteFromList(site) }),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }*/
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NameInputField(createPostViewModel: CreatePostViewModel) {

    var titleTextState: String by createPostViewModel.titleTextState

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
                value = titleTextState,
                onValueChange = { titleTextState = it },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    backgroundColor = Color.Transparent
                ),
                label = { Text(text = "Titel", color = Color.White) },
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BodyInputField(createPostViewModel: CreatePostViewModel) {

    var bodyTextState: String by createPostViewModel.bodyTextState

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = bodyTextState,
                onValueChange =
                {
                    bodyTextState = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    backgroundColor = Color.Transparent
                ),
                label = { Text(text = "Indhold", color = Color.White) },
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StartDatePicker(createPostViewModel: CreatePostViewModel) {

    // Fetching the Local Context
    val context = LocalContext.current

    // Specifies what format i want a date in
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    var startDate by remember { mutableStateOf(createPostViewModel.postStartDateState) }

    // Declaring integer values
    // for year, month and day
    val year: Int
    val month: Int
    val day: Int

    // Initializing a Calendar
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val date = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            date.value = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            startDate.value = LocalDate.parse(date.value, formatter)
            // "$dayOfMonth/${month+1}/$year"
        }, year, month, day
    )

    var bodyTextState: String by createPostViewModel.bodyTextState

    // controller and manager for controlling keyboard actions,
    // and managing the focus in the app. For example if i click on the
    // screen the focusmanager can detect it and set a focus or unfocus an element
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // Creating the outer box for the datepicker
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
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
        // Using a ROW to align datepicker and date display text in a single line
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Creating a button that on
            // click displays/shows the DatePickerDialog
            Button(
                onClick = {
                    datePickerDialog.show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(text = "Vælg start dato", color = Color.White)
            }
            Text(
                fontSize = 16.sp,
                text = "START DATO: ${startDate.value}",
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EndDatePicker(createPostViewModel: CreatePostViewModel) {

    // Fetching the Local Context
    val context = LocalContext.current

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    var endDate by remember { mutableStateOf(createPostViewModel.postEndDateState) }

    // Declaring integer values
    // for year, month and day
    val year: Int
    val month: Int
    val day: Int

    // Initializing a Calendar
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val date = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            date.value = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            endDate.value = LocalDate.parse(date.value, formatter)
            // "$dayOfMonth/${month+1}/$year"
        }, year, month, day
    )

    var bodyTextState: String by createPostViewModel.bodyTextState

    // controller and manager for controlling keyboard actions,
    // and managing the focus in the app. For example if i click on the
    // screen the focusmanager can detect it and set a focus or unfocus an element
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // Outer box for the datepicker
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
        // Defining a ROW to align datepicker and date text in a single line
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Creating a button that on
            // click displays/shows the DatePickerDialog
            Button(
                onClick = {
                    datePickerDialog.show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(text = "Vælg slut dato", color = Color.White)
            }
            Text(
                fontSize = 16.sp,
                text = "SLUT DATO: ${endDate.value}",
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}


@Composable
fun ImagePicker(createPostViewModel: CreatePostViewModel) {

    val context = LocalContext.current

    // Defining a boolean to check if dialog box is shown or not (true/false)
    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    val angle by remember { mutableStateOf(0f) }
    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var selectedImageUri by remember {
        mutableStateOf(createPostViewModel.imageUri)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri.value = uri }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            // Button for opening an image picker
            Button(
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.top_bar_bg2)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "TILFØJ FOTO", color = Color.White)
            }
        }

        Row() {
            Button(
                onClick = { showCustomDialog = !showCustomDialog }, modifier = Modifier
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.top_bar_bg2),
                    disabledBackgroundColor = colorResource(id = R.color.disabled_button).copy(alpha = 0.5f)
                ),
                enabled = selectedImageUri.value != null
            ) {
                Text(text = "FORHÅNDSVIS BILLEDE", color = Color.White)
            }

            if (selectedImageUri.value != null) {
                OutlinedButton(
                    onClick = { selectedImageUri.value = null },
                    modifier = Modifier
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.background_home)
                    ),
                    border = BorderStroke(1.dp, color = colorResource(id = R.color.top_bar_bg2))
                )
                {
                    Text(text = "X", color = Color.Black)
                }
            }
        }

        // IF statement to check if dialog is open already (true)
        // and if the selectedImageUri is not null, since i don't
        // want the user to be able to be able to open dialog window
        // if no picture is chosen
        if (showCustomDialog && selectedImageUri.value != null) {
            CustomAlertDialog({
                showCustomDialog = !showCustomDialog
            }, {
                val activity = (context as? Activity)
                activity?.finish()
            },
                selectedImageUri.value
            )
        }
    }
}

@Composable
fun CustomAlertDialog(onDismiss: () -> Unit, onExit: () -> Unit, uri: Uri?) {

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.top_bar_bg))
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {

                    AsyncImage(
                        model = uri,
                        contentDescription = "PostImage",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )
                }

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.top_bar_bg2)
                        ),
                    ) {
                        Text(text = "LUK", color = Color.White)
                    }
                }
            }
        }
    }
}