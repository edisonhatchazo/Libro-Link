package com.edison.librolink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.edison.librolink.navigation.BottomNavigation
import com.edison.librolink.ui.theme.LibroLinkTheme
import com.edison.librolink.ui.theme.ThemeMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }

            LibroLinkTheme(themeMode = themeMode) {
                val navController = rememberNavController()
                BottomNavigation(
                    navController = navController,
                    themeMode = themeMode,
                    onThemeChange = {newTheme ->
                        themeMode = newTheme

                    }
                )
            }
        }
    }
}
