package com.edison.librolink.data

import kotlinx.coroutines.flow.Flow

interface FavoriteBookRepository {
    fun getAllFavoriteBooks(): Flow<List<FavoriteBook>>

    suspend fun getFavoriteBook(id: String): FavoriteBook?

    suspend fun insertFavoriteBook(favoriteBook: FavoriteBook)

    suspend fun deleteFavoriteBook(favoriteBook: FavoriteBook)


}
