package com.edison.librolink.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edison.librolink.ui.theme.ThemeMode

sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Home")
    data object Books : Screen("books", "My Books")
}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeApp(themeMode = themeMode, onThemeChange = onThemeChange) }
        composable(Screen.Books.route) { BooksApp(themeMode = themeMode, onThemeChange = onThemeChange) }
    }
}

interface NavigationDestination {
    val route: String
    val titleRes: Int
}


