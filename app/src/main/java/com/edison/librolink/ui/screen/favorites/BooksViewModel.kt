package com.edison.librolink.ui.screen.favorites

import androidx.lifecycle.ViewModel
import com.edison.librolink.data.FavoriteBook
import com.edison.librolink.data.FavoriteBookRepository
import kotlinx.coroutines.flow.Flow

class BooksViewModel(
    favoriteBookRepository: FavoriteBookRepository
) : ViewModel() {
    val favoriteBooks: Flow<List<FavoriteBook>> = favoriteBookRepository.getAllFavoriteBooks()
}
