package com.edison.librolink.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteBook::class], version = 1, exportSchema = false)
abstract class FavoriteBookDatabase : RoomDatabase(){
    abstract fun FavoriteBookDao(): FavoriteBookDao

    companion object{
        @Volatile
        private var Instance: FavoriteBookDatabase? = null

        fun getDatabase(context: Context): FavoriteBookDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, FavoriteBookDatabase::class.java,"favorite_book_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }
        }
    }
}
