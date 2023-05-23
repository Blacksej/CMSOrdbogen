package dev.danieltm.cmsordbogen.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.utilities.PostsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ShowPostViewModel : ViewModel() {

    var postsService = PostsService.create()

    private val _id = MutableStateFlow(-1)
    val id = _id.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _sites = MutableStateFlow<List<String>>(emptyList())
    val sites = _sites.asStateFlow()

    private val _body = MutableStateFlow("")
    val body = _body.asStateFlow()

    private val _type = MutableStateFlow("")
    val type = _type.asStateFlow()

    private val _startDate = MutableStateFlow("")
    val startDate = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow("")
    val endDate = _endDate.asStateFlow()

    private val _image = MutableStateFlow("")
    val image = _image.asStateFlow()

    //val sitesState = mutableListOf<String>()


    fun setPost(post: PostModel){
        _id.value = post.id
        _title.value = post.title
        _sites.value = post.sites
        _body.value = post.body
        _type.value = post.type
        _startDate.value = post.startDate
        _endDate.value = post.endDate
        _image.value = post.image ?: ""
    }

    suspend fun deletePost(postId: Int){
        postsService.deletePost(postId)
    }
}