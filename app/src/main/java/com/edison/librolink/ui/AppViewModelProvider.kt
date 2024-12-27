package com.edison.librolink.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.edison.librolink.LibroLinkApplication
import com.edison.librolink.ui.screen.favorites.BooksViewModel
import com.edison.librolink.ui.screen.home.BookDetailViewModel
import com.edison.librolink.ui.screen.home.BookReadingViewModel
import com.edison.librolink.ui.screen.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory{
        initializer {
            HomeViewModel()
        }
        initializer {
            BookDetailViewModel(
                this.createSavedStateHandle(),
                libroLinkApplication().container.favoriteBookRepository
            )
        }
        initializer {
            BookReadingViewModel(
                this.createSavedStateHandle(),
                libroLinkApplication().container.favoriteBookRepository
            )
        }
        initializer {
            BooksViewModel(
                libroLinkApplication().container.favoriteBookRepository
            )
        }
    }
}

fun CreationExtras.libroLinkApplication(): LibroLinkApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibroLinkApplication)
