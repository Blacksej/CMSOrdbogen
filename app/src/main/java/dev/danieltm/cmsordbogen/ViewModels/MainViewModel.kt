package dev.danieltm.cmsordbogen.ViewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dev.danieltm.cmsordbogen.Models.BottomNavItem
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.Models.Repositories.PostRepository
import dev.danieltm.cmsordbogen.utilities.IPostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel : ViewModel(){

    var postRepo = PostRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _allPostsList = MutableStateFlow(emptyList<PostModel>())
    val allPostsList = _allPostsList.asStateFlow()


    init {
        loadRecentPosts()
        getPostsTemp()
    }

    fun loadRecentPosts(){
        // Simulate delay for loading
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000L)
            _isLoading.value = false
        }

    }

    fun getNavList() : List<BottomNavItem>
    {
        var navItems = listOf(
            BottomNavItem(
                name = "HJEM",
                route = "home",
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                name = "",
                route = "create",
                icon = Icons.Default.AddCircle
            ),
            BottomNavItem(
                name = "INDLÆG",
                route = "posts",
                icon = Icons.Default.Menu,
                badgeCount = 7
            ),
        )
        return navItems
    }

    fun getPostsTemp()
    {
        _allPostsList.value = postRepo.getAllPosts()
    }
}