package com.edison.librolink.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.edison.librolink.ui.theme.ThemeMode

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screen.Home.route
            ),
            BottomNavigationItem(
                label = "My Books",
                icon = Icons.Filled.Favorite,
                route = Screen.Books.route
            ),
            BottomNavigationItem(
                label = "Downloads",
                icon = Icons.Filled.List,
                route = Screen.Downloads.route
            ),
        )
    }
}

@Composable
fun BottomNavigation(
    navController: NavHostController,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {
    val items = BottomNavigationItem().bottomNavigationItems()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = currentRoute == navigationItem.route,
                        label = { Text(navigationItem.label) },
                        icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label) },
                        onClick = {
                            if (currentRoute != navigationItem.route) {
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Navigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            themeMode = themeMode,
            onThemeChange = onThemeChange
        )
    }
}
