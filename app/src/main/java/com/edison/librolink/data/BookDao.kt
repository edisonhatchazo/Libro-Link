package com.edison.librolink.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteBookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteBook(favoriteBook: FavoriteBook)

    @Delete
    suspend fun deleteFavoriteBook(favoriteBook: FavoriteBook)

    @Query("SELECT * from favorites WHERE id = :id")
    suspend fun getFavoriteBook(id: String): FavoriteBook?


    @Query("SELECT * from favorites ORDER BY title ASC")
    fun getAllFavoriteBooks(): Flow<List<FavoriteBook>>
}

