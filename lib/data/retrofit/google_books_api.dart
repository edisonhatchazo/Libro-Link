import 'package:dio/dio.dart';
import 'package:libro_link/data/retrofit/book_response.dart';
class GoogleBooksApi {
  final Dio _dio;

  GoogleBooksApi([Dio? dio])
      : _dio = dio ?? Dio(BaseOptions(baseUrl: 'https://www.googleapis.com/books/v1/'));

  Future<BookResponse> searchBooks({
    required String query,
    int startIndex = 0,
    int maxResults = 10,
  }) async {
    final response = await _dio.get('volumes', queryParameters: {
      'q': query,
      'startIndex': startIndex,
      'maxResults': maxResults,
    });

    return BookResponse.fromJson(response.data);
  }

  Future<BookItem> getBookById(String id) async {
    final response = await _dio.get('volumes/$id');
    return BookItem.fromJson(response.data);
  }
}

