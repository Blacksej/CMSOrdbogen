package dev.danieltm.cmsordbogen.ViewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.danieltm.cmsordbogen.Models.BottomNavItem
import dev.danieltm.cmsordbogen.Models.PostModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel : ViewModel(){

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadRecentPosts()
    }

    fun loadRecentPosts(){
        // Simulate delay for loading
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000L)
            _isLoading.value = false
        }

        getPostsTemp()
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

    fun getPostsTemp() : List<PostModel>
    {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var posts = listOf(
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                image = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
        )
        return posts
    }
}