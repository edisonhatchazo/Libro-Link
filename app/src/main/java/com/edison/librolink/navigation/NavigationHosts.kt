package com.edison.librolink.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.edison.librolink.ui.screen.favorites.BooksDestination
import com.edison.librolink.ui.screen.favorites.BooksScreen
import com.edison.librolink.ui.screen.home.BookDetailScreen
import com.edison.librolink.ui.screen.home.BookReadingDestination
import com.edison.librolink.ui.screen.home.BookReadingScreen
import com.edison.librolink.ui.screen.home.HomeBookDetailDestination
import com.edison.librolink.ui.screen.home.HomeDestination
import com.edison.librolink.ui.screen.home.HomeScreen
import com.edison.librolink.ui.theme.ThemeMode

@Composable
fun HomeNavHost(
    navController: NavHostController,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route){
            HomeScreen(
                themeMode = themeMode,
                onThemeChange = onThemeChange,
                navigateToBookDetailScreen = { bookId ->
                    if (bookId.isNotEmpty()) {
                        navController.navigate("${HomeBookDetailDestination.route}/$bookId")
                    } else {
                        Log.e("Navigation", "Book ID is null or empty!")
                    }
                }
            )
        }
        composable(
            route = HomeBookDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(HomeBookDetailDestination.BOOKDETAILIDARG){
                type = NavType.StringType
            })
        ) {
            BookDetailScreen(
                themeMode = themeMode,
                onThemeChange = onThemeChange,
                navigateBack = {navController.navigateUp()},
                navigateToBookReadingScreen = {bookId ->
                    if (bookId.isNotEmpty()) {
                        navController.navigate("${BookReadingDestination.route}/$bookId")
                    } else {
                        Log.e("Navigation", "Book ID is null or empty!")
                    }
                }
            )
        }

        composable(
            route = BookReadingDestination.routeWithArgs,
            arguments = listOf(navArgument(BookReadingDestination.BOOKREADIDARG){
                type = NavType.StringType
            })
        ) {
            BookReadingScreen(
                navigateBack = {navController.navigateUp()},
                themeMode = themeMode,
                onThemeChange = onThemeChange)
        }

    }
}


@Composable
fun BooksNavHost(
    navController: NavHostController,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BooksDestination.route,
        modifier = modifier
    ){
        composable(route = BooksDestination.route){
            BooksScreen(
                themeMode = themeMode,
                onThemeChange = onThemeChange,
                navigateToBookDetailScreen = { bookId ->
                    if (bookId.isNotEmpty()) {
                        navController.navigate("${HomeBookDetailDestination.route}/$bookId")
                    } else {
                        Log.e("Navigation", "Book ID is null or empty!")
                    }
                }
            )
        }
        composable(
            route = HomeBookDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(HomeBookDetailDestination.BOOKDETAILIDARG){
                type = NavType.StringType
            })
        ) {
            BookDetailScreen(
                themeMode = themeMode,
                onThemeChange = onThemeChange,
                navigateBack = {navController.navigateUp()},
                navigateToBookReadingScreen = {bookId ->
                    if (bookId.isNotEmpty()) {
                        navController.navigate("${BookReadingDestination.route}/$bookId")
                    } else {
                        Log.e("Navigation", "Book ID is null or empty!")
                    }
                }
            )
        }

        composable(
            route = BookReadingDestination.routeWithArgs,
            arguments = listOf(navArgument(BookReadingDestination.BOOKREADIDARG){
                type = NavType.StringType
            })
        ) {
            BookReadingScreen(
                navigateBack = {navController.navigateUp()},
                themeMode = themeMode,
                onThemeChange = onThemeChange)
        }
    }
}


