package com.edison.librolink.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.edison.librolink.LibroLinkApplication
import com.edison.librolink.ui.screen.BooksViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory{
        initializer {
            BooksViewModel()
        }
    }
}

fun CreationExtras.libroLinkApplication(): LibroLinkApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibroLinkApplication)
