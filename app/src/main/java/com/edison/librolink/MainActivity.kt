package com.edison.librolink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                BottomNavigation(
                    themeMode = themeMode,
                    onThemeChange = {newTheme ->
                        themeMode = newTheme

                    }
                )
            }
        }
    }
}
