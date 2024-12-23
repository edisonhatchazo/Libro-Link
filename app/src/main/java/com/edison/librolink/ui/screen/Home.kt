package com.edison.librolink.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.edison.librolink.ui.theme.ThemeMode

object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,

) {
    val viewModel: BooksViewModel = viewModel()
    val isSystemDarkTheme = isSystemInDarkTheme()
    var showDescriptions by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("")}
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopAppBar(
                title = stringResource(R.string.home),
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
                onSearch = { newQuery ->
                    query = newQuery
                    viewModel.clearBooks()
                    viewModel.loadMoreBooks(query)
                },
                onToggleView = { showDescriptions = !showDescriptions }

            )
        }
    ) { paddingValues ->
        MainBookScreen(
            modifier = modifier.padding(paddingValues = paddingValues),
            onLoadMore = {viewModel.loadMoreBooks(query)},
            viewModel = viewModel,
            showDescriptions = showDescriptions
        )
    }
}

@Composable
fun MainBookScreen(
    modifier: Modifier = Modifier,
    viewModel: BooksViewModel,
    onLoadMore: () -> Unit,
    showDescriptions: Boolean
) {
    val books by viewModel.books.collectAsState()
    val gridState = rememberLazyGridState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                if (lastVisibleItemIndex >= books.size - 5 && !isLoading) {
                    isLoading = true
                    onLoadMore()
                    isLoading = false
                }
            }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (books.isEmpty()) {
            Text("No books to display", modifier = Modifier.padding(top = 25.dp))
        } else {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(2), // 2 columns
                modifier = Modifier.padding(8.dp)
            ) {
                items(books) { book ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        if (showDescriptions) {
                            // Show description type
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = book.volumeInfo.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = ("By: " + book.volumeInfo.authors?.joinToString(", ")),
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelMedium,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = book.volumeInfo.description?.take(100) ?: "No Description",
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        } else {
                            // Show image type
                            val imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
                            if (imageUrl != null) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = book.volumeInfo.title,
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

                // Loading indicator at the bottom
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    title: String,
    isDarkMode: Boolean,
    onThemeChange: () -> Unit,
    onSearch: (String) -> Unit,
    onToggleView: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val color = when(isDarkMode){
        true -> Color.DarkGray
        else -> Color.LightGray
    }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = color),
        title = {
            if (isSearching) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search books...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            } else {
                Text(title)
            }
        },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick =  onToggleView) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = stringResource(R.string.toggle)
                )
            }
            if (isSearching) {
                IconButton(onClick = { onSearch(searchQuery) }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(R.string.search)
                    )
                }
                IconButton(onClick = { isSearching = false }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.cancel_search)
                    )
                }
            } else {
                IconButton(onClick = { isSearching = true }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search)
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
        }
    )
}

