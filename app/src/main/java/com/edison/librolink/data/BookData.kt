package com.edison.librolink.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteBook(
    @PrimaryKey val id: String, // Book ID
    val title: String,
    val subtitle: String?,
    val authors: String?, // Save as a comma-separated string
    val publisher: String?,
    val publishedDate: String?,
    val pageCount: Int?,
    val description: String?,
    val imageUrl: String?, // Thumbnail URL
)
