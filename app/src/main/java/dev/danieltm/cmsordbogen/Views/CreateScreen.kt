package dev.danieltm.cmsordbogen.Views

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
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
            Column{
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

// SCREENS FOR THE SELECTED TYPE
@Composable
fun CreateNewsScreen(createPostViewModel: CreatePostViewModel) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NameInputField(createPostViewModel = createPostViewModel)

        BodyInputField(createPostViewModel = createPostViewModel)

        StartDatePicker(createPostViewModel = createPostViewModel)
        EndDatePicker(createPostViewModel = createPostViewModel)

        DropDownSiteMenu(createPostViewModel = createPostViewModel)

        Text(
            text = "Nyheds skærm",
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

    var sitesList = remember {
        createPostViewModel.sitesState.toMutableStateList()
    }

    //var tempDisplaySite: String by createPostViewModel.tempDisplaySiteState

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    //var selectedSite by remember { mutableStateOf("VÆLG SIDE(R)") }

    val listOfPossibleSites = listOf<String>(
        "Ordbogen.com",
        "ABC.Ordbogen.com",
        "Grammatip.com"
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
                    value = "Vælg side/sider",
                    onValueChange = { },
                    modifier = Modifier
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
                )

                DropdownMenu(
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
                            if (!sitesList.contains(label)) {
                                sitesList.add(label)
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
                sitesList.forEach { site ->
                        Text(
                            text = site,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable(onClick = { sitesList.remove(site) }),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
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
                onValueChange = { bodyTextState = it },
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

    var selectedImageUris by remember {
        mutableStateOf(createPostViewModel.imageUris)
    }

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
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.top_bar_bg2))
                ) {
                    Text(text = "Vælg foto(s)", color = Color.White)
                }
            }
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