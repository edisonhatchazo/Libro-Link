package com.edison.librolink.ui.screen.home

import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edison.librolink.R
import com.edison.librolink.data.retrofit.BookItem
import com.edison.librolink.navigation.NavigationDestination
import com.edison.librolink.ui.AppViewModelProvider
import com.edison.librolink.ui.theme.ThemeMode

object BookReadingDestination: NavigationDestination {
    override val route = "book_reading_home"
    override val titleRes = R.string.read_book

    const val BOOKREADIDARG = "BookReadId"
    val routeWithArgs = "$route/{$BOOKREADIDARG}"
}

@Composable
fun BookReadingScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    viewModel: BookReadingViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val isFavorite = uiState.isFavorite
    val book = viewModel.book.collectAsState()
    val isSystemDarkTheme = isSystemInDarkTheme()
    Scaffold(
        modifier = modifier,
        topBar = {
            BookReadingTopAppBar(
                canNavigateBack = true,
                navigateUp = navigateBack,
                title = "Read Book",
                isFavorite = isFavorite,
                isDarkMode = when (themeMode) {
                    ThemeMode.DARK -> true
                    ThemeMode.LIGHT -> false
                    ThemeMode.SYSTEM -> isSystemDarkTheme // Use system theme dynamically
                },
                viewModel = viewModel,
                onThemeChange = {
                    val newTheme = when (themeMode) {
                        ThemeMode.DARK -> ThemeMode.LIGHT
                        ThemeMode.LIGHT -> ThemeMode.DARK
                        ThemeMode.SYSTEM -> if (isSystemDarkTheme) ThemeMode.LIGHT else ThemeMode.DARK
                    }
                    onThemeChange(newTheme)
                }
            )
        }
    ) {innerPadding ->
        MainBookReadingScreen(
            book = book.value,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }

}

@Composable
fun MainBookReadingScreen(
    book: BookItem?,
    modifier: Modifier,
){
    val webReaderLink = book?.accessInfo?.webReaderLink

    if (webReaderLink != null) {
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.allowFileAccess = false // Disable file access
                    settings.allowContentAccess = false
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
                    loadUrl(webReaderLink) // Load the webReaderLink in WebView
                }
            },
            update = { webView ->
                webView.loadUrl(webReaderLink) // Ensure WebView reloads if link changes
            }
        )
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("This book cannot be read online.")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReadingTopAppBar(
    title: String,
    isDarkMode: Boolean,
    canNavigateBack: Boolean,
    isFavorite: Boolean,
    navigateUp:() -> Unit,
    onThemeChange: () -> Unit,
    viewModel: BookReadingViewModel

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val color = when(isDarkMode){
        true -> Color.DarkGray
        else -> Color.LightGray
    }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = color),
        modifier = Modifier.height(75.dp),
        title = {Text(title)},
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { viewModel.toggleFavorite() }) {
                Icon(
                    imageVector = if(!isFavorite) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite,
                    contentDescription = stringResource(R.string.add_to_favorites)
                )
            }
            IconButton(onClick = onThemeChange) {
                Icon(
                    painter = painterResource(
                        id = if (isDarkMode) R.drawable.icons8_dark_mode_50
                        else R.drawable.icons8_light_mode_78
                    ),
                    contentDescription = stringResource(
                        if (isDarkMode) R.string.light_mode else R.string.dark_mode
                    ),
                    tint = Color.White
                )
            }
        }
    )
}