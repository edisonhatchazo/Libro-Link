package com.edison.librolink.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edison.librolink.R
import com.edison.librolink.data.retrofit.BookItem
import com.edison.librolink.navigation.NavigationDestination
import com.edison.librolink.ui.AppViewModelProvider
import com.edison.librolink.ui.theme.ThemeMode

object HomeBookDetailDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_book_details

    const val BOOKDETAILIDARG = "BookDetailId"
    val routeWithArgs = "$route/{$BOOKDETAILIDARG}"
}


@Composable
fun BookDetailScreen(
    navigateBack: () -> Unit,
    navigateToBookReadingScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    viewModel: BookDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val isFavorite = uiState.isFavorite
    val book by viewModel.book.collectAsState()
    val isSystemDarkTheme = isSystemInDarkTheme()
    Scaffold(
        modifier = modifier,
        topBar = {
            BookDetailTopAppBar(
                canNavigateBack = true,
                navigateUp = navigateBack,
                title = "Book Details",
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
        MainBookDetailScreen(
            book = book,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState()),
            navigateToBookReadingScreen = navigateToBookReadingScreen
        )
    }
}

@Composable
fun MainBookDetailScreen(
    book: BookItem?,
    modifier: Modifier,
    navigateToBookReadingScreen: (String) -> Unit,
){
    val formattedDescription = HtmlCompat.fromHtml(
        book?.volumeInfo?.description?: "",
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        BookDetailsCard(
            book = book,
            modifier = Modifier.fillMaxWidth()
        )
        DescriptionCard(
            formattedDescription = formattedDescription
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            contentAlignment = Alignment.Center // Centers the content within the Box
        ) {
            Button(
                onClick = {
                    if (book?.id != null) {
                        navigateToBookReadingScreen(book.id)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Set button width to 50% of the parent width
            ) {
                Text(text = "Explore in Google Books")
            }
        }

    }
}



@Composable
fun DescriptionCard(
    formattedDescription: String,
){
    if(formattedDescription != "") {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.padding_medium)
                )
            ) {
                BookDetailsRow(
                    labelResId = R.string.description,
                    bookDetail = ""
                )

                Row(modifier = Modifier) {
                    Text(
                        text = formattedDescription,
                        modifier = Modifier.weight(0.4f),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun BookDetailsCard(
    book: BookItem?,
    modifier: Modifier,
){
    Card(
        modifier = modifier,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ){
            BookDetailsRow(
                labelResId = R.string.title,
                bookDetail = book?.volumeInfo?.title ?: ""
            )
            if(book?.volumeInfo?.subtitle != null && book.volumeInfo.subtitle != "") {
                BookDetailsRow(
                    labelResId = R.string.subtitle,
                    bookDetail = book.volumeInfo.subtitle
                )
            }
            if(book?.volumeInfo?.publisher != null && book.volumeInfo.publisher != "") {
                BookDetailsRow(
                    labelResId = R.string.publisher,
                    bookDetail = book.volumeInfo.publisher
                )
            }
            if(book?.volumeInfo?.publishedDate != null && book.volumeInfo.publishedDate != "") {
                BookDetailsRow(
                    labelResId = R.string.published_date,
                    bookDetail = book.volumeInfo.publishedDate
                )
            }
            AuthorRow(
                labelResId = R.string.authors,
                authors = book?.volumeInfo?.authors
            )
            if(book?.volumeInfo?.pageCount != null && book.volumeInfo.pageCount != "") {
                BookDetailsRow(
                    labelResId = R.string.page_count,
                    bookDetail = book.volumeInfo.pageCount
                )
            }
            if(book?.volumeInfo?.maturityRating != null && book.volumeInfo.maturityRating != "") {
                BookDetailsRow(
                    labelResId = R.string.maturity,
                    bookDetail = book.volumeInfo.maturityRating
                )
            }
        }
    }

}

@Composable
private fun BookDetailsRow(
    @StringRes labelResId: Int, bookDetail: String, modifier: Modifier = Modifier
){
    Row(modifier = modifier){
        Text(
            text = stringResource(labelResId),
            modifier = Modifier.weight(0.4f),
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
        Text(
            text = bookDetail,
            modifier = modifier.weight(0.8f),
            maxLines = 2
        )
    }
}

@Composable
private fun AuthorRow(
    @StringRes labelResId: Int,
    authors: List<String>?,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = stringResource(labelResId),
            modifier = Modifier.weight(0.4f),
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
        if (!authors.isNullOrEmpty()) {
            Column(modifier = Modifier.weight(0.8f)) {
                authors.forEach { author ->
                    Text(
                        text = author,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_authors), // Fallback text if no authors
                modifier = Modifier.weight(0.8f),
                maxLines = 2
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailTopAppBar(
    title: String,
    isDarkMode: Boolean,
    canNavigateBack: Boolean,
    isFavorite: Boolean,
    navigateUp:() -> Unit,
    onThemeChange: () -> Unit,
    viewModel: BookDetailViewModel

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
                    imageVector = if(!isFavorite)Icons.Filled.FavoriteBorder else Icons.Filled.Favorite,
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
