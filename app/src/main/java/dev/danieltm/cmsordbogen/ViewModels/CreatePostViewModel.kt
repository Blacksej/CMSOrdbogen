package dev.danieltm.cmsordbogen.ViewModels

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.utilities.PostType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class CreatePostViewModel : ViewModel() {

    val nameTextState: MutableState<String> = mutableStateOf("")
    val bodyTextState: MutableState<String> = mutableStateOf("")
    val postTypeState: MutableState<PostType> = mutableStateOf(PostType.NEWS)
    val postStartDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    val postEndDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    
    fun createPost(name: String, body: String, type: PostType, startDate: LocalDate, endDate: LocalDate)
    {

    }

    

}