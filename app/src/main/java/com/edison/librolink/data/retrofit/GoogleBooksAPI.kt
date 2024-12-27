package com.edison.librolink.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class BookResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo,
    val accessInfo: AccessInfo
)

data class VolumeInfo(
    val title: String,
    val subtitle: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val printType: String?,
    val industryIdentifiers: List<IndustryIdentifier>?,
    val pageCount: String?,
    val categories: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val previewLink: String?,
    val maturityRating: String?,
    val language: String?,
)

data class AccessInfo(
    val viewability: String?,
    val webReaderLink: String?
)

data class IndustryIdentifier(
    val type: String,
    val identifier: String
)

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0, // Default to 0
        @Query("maxResults") maxResults: Int = 10 // Default to 10
    ): BookResponse

    @GET("volumes/{id}")
    suspend fun getBookById(@Path("id") id: String): BookItem
}
data class ImageLinks(
    val thumbnail: String?
)