package com.edison.librolink.data

import android.content.Context

interface AppContainer {
    val favoriteBookRepository: FavoriteBookRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val favoriteBookRepository: FavoriteBookRepository by lazy{
        OfflineFavoriteBookRepository(FavoriteBookDatabase.getDatabase(context).FavoriteBookDao())
    }
}