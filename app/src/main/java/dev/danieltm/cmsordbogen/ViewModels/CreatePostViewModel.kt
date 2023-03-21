package dev.danieltm.cmsordbogen.ViewModels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.danieltm.cmsordbogen.utilities.PostType
import java.time.LocalDate

class CreatePostViewModel : ViewModel() {

    val nameTextState: MutableState<String> = mutableStateOf("")
    val bodyTextState: MutableState<String> = mutableStateOf("")
    val postTypeState: MutableState<PostType> = mutableStateOf(PostType.NOT_PICKED)
    val imageUri: MutableState<Uri?> = mutableStateOf(null)
    val imageUris: MutableState<List<Uri?>> = mutableStateOf(emptyList())
    val postStartDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    val postEndDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    
    fun createPost(name: String, body: String, type: PostType, startDate: LocalDate, endDate: LocalDate)
    {

    }

    

}