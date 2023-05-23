package dev.danieltm.cmsordbogen.Views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.danieltm.cmsordbogen.R
import dev.danieltm.cmsordbogen.ViewModels.ShowPostViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


@Composable
fun ShowPostScreen(showPostViewModel: ShowPostViewModel) {

    val postId by showPostViewModel.id.collectAsState()
    val postTitle by showPostViewModel.title.collectAsState()
    val postSites by showPostViewModel.sites.collectAsState()
    val postBody by showPostViewModel.body.collectAsState()
    val postType by showPostViewModel.type.collectAsState()
    val postStartDate by showPostViewModel.startDate.collectAsState()
    val postEndDate by showPostViewModel.endDate.collectAsState()

    val postImage by showPostViewModel.image.collectAsState()

    val conf = Bitmap.Config.ARGB_8888 // see other conf types

    val bmp = Bitmap.createBitmap(
        5,
        5,
        conf
    ) // this creates a MUTABLE bitmap


    var decodedImage: android.graphics.Bitmap = bmp

    val context = LocalContext.current

    try{
        val imageBytes = Base64.decode(postImage, Base64.DEFAULT)
        decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    catch (error: java.lang.IllegalArgumentException){

    }
    catch(error: java.lang.NullPointerException){

    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_home))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.background_home))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(bottom = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = postType,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(colorResource(id = R.color.top_bar_bg2)),
                    readOnly = true,
                    label = { Text(text = "Type", fontSize = 14.sp, color = Color.DarkGray) },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    )
                )
                TextField(
                    value = postSites.toString(),
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(colorResource(id = R.color.top_bar_bg2)),
                    readOnly = true,
                    label = { Text(text = "Sider", fontSize = 14.sp, color = Color.DarkGray) },
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    )
                )
                TextField(
                    value = postTitle,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(colorResource(id = R.color.top_bar_bg2)),
                    readOnly = true,
                    label = { Text(text = "Titel", fontSize = 14.sp, color = Color.DarkGray) },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    )
                )
                TextField(
                    value = postBody,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(colorResource(id = R.color.top_bar_bg2)),
                    readOnly = true,
                    label = { Text(text = "Indhold", fontSize = 14.sp, color = Color.DarkGray) },
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    )
                )
                TextField(
                    value = postStartDate,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(colorResource(id = R.color.top_bar_bg2)),
                    readOnly = true,
                    label = { Text(text = "Start dato", fontSize = 14.sp, color = Color.DarkGray) },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    )
                )
                TextField(
                    value = postEndDate,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(colorResource(id = R.color.top_bar_bg2)),
                    readOnly = true,
                    label = { Text(text = "Slut dato", fontSize = 14.sp, color = Color.DarkGray) },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    )
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height((decodedImage.height / 5).dp)
                ){
                    AsyncImage(
                        model = decodedImage,
                        contentDescription = "PostImage",
                        contentScale = ContentScale.FillWidth
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(100.dp),
                    onClick = {
                        runBlocking {
                            launch { deletePost(showPostViewModel, postId) }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.top_bar_bg))
                ) {
                    Text(
                        text = "! SLET INDLÆG !",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

suspend fun deletePost(showPostViewModel: ShowPostViewModel, postId: Int) {
    showPostViewModel.deletePost(postId = postId)
}