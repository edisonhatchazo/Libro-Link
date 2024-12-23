package com.edison.librolink.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

data class BookResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?
)

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0, // Default to 0
        @Query("maxResults") maxResults: Int = 10 // Default to 10
    ): BookResponse
}
data class ImageLinks(
    val thumbnail: String?
)