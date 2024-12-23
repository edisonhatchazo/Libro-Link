package com.edison.librolink.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.edison.librolink.navigation.NavigationDestination
import com.edison.librolink.R
import com.edison.librolink.ui.theme.ThemeMode

object DownloadsDestination: NavigationDestination {
    override val route = "downloads"
    override val titleRes = R.string.downloads
}

@Composable
fun DownloadsScreen(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {

    val isSystemDarkTheme = isSystemInDarkTheme()
    Scaffold(
        modifier = modifier,
        topBar = {
            DownloadsTopAppBar(
                title = stringResource(R.string.downloads),
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
                }
            )
        }
    ) {paddingValues ->
        Box(modifier = Modifier.fillMaxSize()
            .padding(paddingValues = paddingValues), contentAlignment = Alignment.Center) {
            Text(text = "Downloads Screen")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsTopAppBar(
    title: String,
    isDarkMode: Boolean,
    onThemeChange: () -> Unit
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
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
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
    )
}

