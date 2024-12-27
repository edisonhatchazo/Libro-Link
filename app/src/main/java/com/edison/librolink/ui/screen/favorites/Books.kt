package com.edison.librolink.ui.screen.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.edison.librolink.R
import com.edison.librolink.navigation.NavigationDestination
import com.edison.librolink.ui.AppViewModelProvider
import com.edison.librolink.ui.theme.ThemeMode

object BooksDestination: NavigationDestination {
    override val route = "books"
    override val titleRes = R.string.my_books
}

@Composable
fun BooksScreen(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    navigateToBookDetailScreen: (String) -> Unit,
    onThemeChange: (ThemeMode) -> Unit
) {
    val isSystemDarkTheme = isSystemInDarkTheme()
    var showDescriptions by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier,
        topBar = {
            BooksTopAppBar(
                title = stringResource(R.string.my_books),
                isDarkMode = when (themeMode) {
                    ThemeMode.DARK -> true
                    ThemeMode.LIGHT -> false
                    ThemeMode.SYSTEM -> isSystemDarkTheme // Use system theme dynamically
                },
                onThemeChange = {
                    val newTheme = when (themeMode) {
                        ThemeMode.DARK -> ThemeMode.LIGHT
                        ThemeMode.LIGHT -> ThemeMode.DARK
                        ThemeMode.SYSTEM -> if (isSystemDarkTheme) ThemeMode.LIGHT else ThemeMode.DARK
                    }
                    onThemeChange(newTheme)
                },
                onToggleView = { showDescriptions = !showDescriptions }
            )
        }
    ) {paddingValues ->
        MainFavoriteScreen(
            showDescriptions = showDescriptions,
            navigateToBookDetailScreen = navigateToBookDetailScreen,
            modifier = modifier.padding(paddingValues = paddingValues),
        )
    }
}


@Composable
fun MainFavoriteScreen(
    showDescriptions: Boolean,
    modifier: Modifier,
    navigateToBookDetailScreen: (String) -> Unit,
    viewModel: BooksViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val favoriteBooks by viewModel.favoriteBooks.collectAsState(emptyList())

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (favoriteBooks.isEmpty()) {
            Text("No favorite books added.", modifier = Modifier.padding(16.dp))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(8.dp)
            ) {
                items(favoriteBooks) { book ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { navigateToBookDetailScreen(book.id) }

                    ) {
                        if (showDescriptions) {
                            // Description view
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = book.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "By: ${book.authors ?: "Unknown"}",
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelMedium,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = book.description ?: "No Description",
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        } else {
                            // Thumbnail view
                            val imageUrl = book.imageUrl?.replace("http://", "https://")
                            if (imageUrl != null) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = book.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("No Image")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksTopAppBar(
    title: String,
    isDarkMode: Boolean,
    onThemeChange: () -> Unit,
    onToggleView: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val color = when(isDarkMode){
        true -> Color.DarkGray
        else -> Color.LightGray
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = color),
            title = { Text(title) },
            scrollBehavior = scrollBehavior,
            actions = {
                IconButton(onClick = onToggleView) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = stringResource(R.string.toggle)
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
}

