package dev.danieltm.cmsordbogen.ViewModels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.danieltm.cmsordbogen.utilities.PostType
import java.time.LocalDate

class CreatePostViewModel : ViewModel() {

    val titleTextState: MutableState<String> = mutableStateOf("")
    val bodyTextState: MutableState<String> = mutableStateOf("")
    val postTypeState: MutableState<String> = mutableStateOf("IKKE VALGT")
    val authorState: MutableState<String> = mutableStateOf("")
    val imageUris: MutableState<List<Uri?>> = mutableStateOf(emptyList())
    val postStartDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    val postEndDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    val creationDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    
    fun createPost(name: String, body: String, type: PostType, startDate: LocalDate, endDate: LocalDate)
    {

    }

    fun convertEnumsToDanish(postTypes: List<PostType>) : List<String>
    {
        var danishTypes: MutableList<String> = mutableListOf<String>()

        postTypes.forEach { type ->
            when(type){
                PostType.ANNOUNCEMENT -> danishTypes.add("ANNONCERING")
                PostType.EVENT -> danishTypes.add("EVENT")
                PostType.NEWS -> danishTypes.add("NYHED")
                PostType.PUSH -> danishTypes.add("PUSH")
                else -> {
                    // DO NOTHING
                }
            }
        }
        return danishTypes
    }

    

}