package dev.danieltm.cmsordbogen.ViewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.lifecycle.ViewModel
import dev.danieltm.cmsordbogen.Models.BottomNavItem

class MainViewModel : ViewModel(){

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
}