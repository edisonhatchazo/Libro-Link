package com.edison.librolink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.edison.librolink.ui.theme.ThemeMode

@Composable
fun HomeApp(
    navController: NavHostController = rememberNavController(),
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {
    HomeNavHost(navController = navController, themeMode = themeMode, onThemeChange = onThemeChange)
}


@Composable
fun BooksApp(
    navController: NavHostController = rememberNavController(),
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
){
    BooksNavHost(navController = navController, themeMode = themeMode, onThemeChange = onThemeChange)
}

