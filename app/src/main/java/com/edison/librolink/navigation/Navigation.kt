package com.edison.librolink.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edison.librolink.ui.screen.BooksScreen
import com.edison.librolink.ui.screen.DownloadsScreen
import com.edison.librolink.ui.screen.HomeScreen
import com.edison.librolink.ui.theme.ThemeMode

sealed class Screen(val route : String, val title: String, val icon: ImageVector) {
    data object Home : Screen("home","Home", Icons.Default.Home)
    data object Books : Screen("books","My Books",Icons.Default.Favorite)
    data object Downloads : Screen("downloads","Downloads",Icons.Default.List)
}

//@Composable
//fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
//    NavHost(navController, startDestination = Screen.Home.route, modifier = modifier) {
//        composable(Screen.Home.route) { HomeApp() }
//        composable(Screen.Books.route) { BooksApp() }
//        composable(Screen.Downloads.route) { DownloadsApp() }
//    }
//}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen(themeMode = themeMode, onThemeChange = onThemeChange) }
        composable(Screen.Books.route) { BooksScreen(themeMode = themeMode, onThemeChange = onThemeChange) }
        composable(Screen.Downloads.route) { DownloadsScreen(themeMode = themeMode, onThemeChange = onThemeChange) }
    }
}


interface NavigationDestination {
    val route: String
    val titleRes: Int
}


