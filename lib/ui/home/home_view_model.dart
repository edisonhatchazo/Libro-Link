import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:libro_link/data/retrofit/google_books_api.dart';
import 'package:libro_link/data/retrofit/book_response.dart';

class HomeViewModel extends ChangeNotifier {
  final GoogleBooksApi _api;
  final dio = Dio(BaseOptions(
    baseUrl: 'https://www.googleapis.com/books/v1/',
  ));
  HomeViewModel(Dio dio) : _api = GoogleBooksApi(dio);
  final List<BookItem> _books = [];

  List<BookItem> get books => _books;
  int _currentIndex = 0;
  final int _pageSize = 10;
  bool _isLoading = false;

  Future<void> loadMoreBooks(String query) async {
    if (_isLoading) return;
    _isLoading = true;
    try {
      final response = await _api.searchBooks(
        query: query,
        startIndex: _currentIndex,
        maxResults: _pageSize,
      );
      debugPrint(response.toString());
      final newBooks = response.items ?? [];
      final uniqueBooks = newBooks.where((newBook) =>
      !_books.any((existing) => existing.volumeInfo.title == newBook.volumeInfo.title)
      ).toList();
      _books.addAll(uniqueBooks);
      _currentIndex += _pageSize;
      notifyListeners();
    } catch (e) {
      debugPrint("Error loading books: $e");
    } finally {
      _isLoading = false;
    }
  }

  void clearBooks() {
    _books.clear();
    _currentIndex = 0;
    notifyListeners();
  }
}