package com.edison.librolink.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edison.librolink.ui.screen.BooksDestination
import com.edison.librolink.ui.screen.BooksScreen
import com.edison.librolink.ui.screen.DownloadsDestination
import com.edison.librolink.ui.screen.DownloadsScreen
import com.edison.librolink.ui.screen.HomeDestination
import com.edison.librolink.ui.screen.HomeScreen

@Composable
fun HomeApp(navController: NavHostController = rememberNavController()) {
    HomeNavHost(navController = navController)
}


@Composable
fun BooksApp(navController: NavHostController = rememberNavController()){
    BooksNavHost(navController = navController)
}


@Composable
fun DownloadsApp(navController: NavHostController = rememberNavController()) {
    DownloadsNavHost(navController = navController)
}



@Composable
fun HomeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
//    NavHost(
//        navController = navController,
//        startDestination = HomeDestination.route,
//        modifier = modifier
//    ){
//        composable(route = HomeDestination.route){
//            HomeScreen(
//
//            )
//        }
//    }
}


@Composable
fun BooksNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
//    NavHost(
//        navController = navController,
//        startDestination = BooksDestination.route,
//        modifier = modifier
//    ){
//        composable(route = BooksDestination.route){
//            BooksScreen(
//
//            )
//        }
//    }
}



@Composable
fun DownloadsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
//    NavHost(
//        navController = navController,
//        startDestination = DownloadsDestination.route,
//        modifier = modifier
//    ){
//        composable(route = DownloadsDestination.route){
//            DownloadsScreen(
//
//            )
//        }
//    }
}