package com.edison.librolink.data

import kotlinx.coroutines.flow.Flow


class OfflineFavoriteBookRepository(private val favoriteBookDao: FavoriteBookDao):
    FavoriteBookRepository {
    override fun getAllFavoriteBooks(): Flow<List<FavoriteBook>> = favoriteBookDao.getAllFavoriteBooks()

    override suspend fun getFavoriteBook(id: String): FavoriteBook? = favoriteBookDao.getFavoriteBook(id)

    override suspend fun insertFavoriteBook(favoriteBook: FavoriteBook) = favoriteBookDao.insertFavoriteBook(favoriteBook)

    override suspend fun deleteFavoriteBook(favoriteBook: FavoriteBook) = favoriteBookDao.deleteFavoriteBook(favoriteBook)
}
