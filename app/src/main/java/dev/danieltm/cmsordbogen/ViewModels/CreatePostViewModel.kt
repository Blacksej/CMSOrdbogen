package dev.danieltm.cmsordbogen.ViewModels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.utilities.PostType
import dev.danieltm.cmsordbogen.utilities.PostsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class CreatePostViewModel : ViewModel() {

    var postsService = PostsService.create()

    val titleTextState: MutableState<String> = mutableStateOf("")
    val bodyTextState: MutableState<String> = mutableStateOf("")
    val postTypeState: MutableState<String> = mutableStateOf("IKKE VALGT")

    val sitesState = mutableListOf<String>()
    val tempDisplaySiteState: MutableState<String> = mutableStateOf("IKKE VALGT")

    val authorState: MutableState<String> = mutableStateOf("Daniel")
    val imageUri: MutableState<Uri?> = mutableStateOf(null)
    val postStartDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    val postEndDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    val creationDateState: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    
    suspend fun createPost()
    {
        val model = PostModel(
            id = 6,
            title = titleTextState.value,
            body = bodyTextState.value,
            type = postTypeState.value,
            sites = sitesState,
            //author = "Daniel",
            image = imageUri.value.toString(),
            startDate = postStartDateState.value.toString(),
            endDate = postEndDateState.value.toString(),
            //creationTime = LocalDate.now()
        )

        if(model != null){
            postsService.createNewsPost(model)
        }
    }

    // Converts PostType ENUMS to danish, since the app has to display
    // value in danish
    fun convertEnumsToDanish(postTypes: List<PostType>) : List<String>
    {
        var danishTypes: MutableList<String> = mutableListOf<String>()

        // Iterates over the postTypes list and using a when(switch) to
        // determine what type it is, and afterwards adds the corresponding
        // danish type name to a list
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