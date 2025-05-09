import 'package:flutter/material.dart';
import 'package:libro_link/data/retrofit/google_books_api.dart';
import 'package:libro_link/data/retrofit/book_response.dart';
import 'package:libro_link/data/database/favorite_book_repository.dart';
import 'package:libro_link/data/database/favorite_book.dart';
class BookDetailsViewModel extends ChangeNotifier {
  final GoogleBooksApi api;
  final String bookId;
  final FavoriteBookRepository repository;

  BookItem? _book;
  bool _isFavorite = false;

  BookItem? get book => _book;
  bool get isFavorite => _isFavorite;

  BookDetailsViewModel({
    required this.api,
    required this.bookId,
    required this.repository,
  }) {
    fetchBookDetails();
  }

  Future<void> fetchBookDetails() async {
    try {
      final bookItem = await api.getBookById(bookId);
      _book = bookItem;

      final favorite = await repository.getFavoriteBook(bookItem.id);
      _isFavorite = favorite != null;

      notifyListeners();
    } catch (e) {
      debugPrint("Error fetching book details: $e");
    }
  }

  Future<void> toggleFavorite() async {
    if (_book == null) return;

    final bookItem = _book!;
    final favoriteBook = FavoriteBook(
      id: bookItem.id,
      title: bookItem.volumeInfo.title ?? '',
      subtitle: bookItem.volumeInfo.subtitle,
      authors: bookItem.volumeInfo.authors?.join(', '),
      publisher: bookItem.volumeInfo.publisher,
      publishedDate: bookItem.volumeInfo.publishedDate,
      pageCount: bookItem.volumeInfo.pageCount,
      description: bookItem.volumeInfo.description,
      imageUrl: bookItem.volumeInfo.imageLinks?.thumbnail,
    );

    if (_isFavorite) {
      await repository.deleteFavoriteBook(favoriteBook);
      _isFavorite = false;
    } else {
      await repository.insertFavoriteBook(favoriteBook);
      _isFavorite = true;
    }

    notifyListeners();
  }
}