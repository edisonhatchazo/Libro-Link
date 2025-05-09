import 'package:dio/dio.dart';
import 'package:libro_link/data/retrofit/google_books_api.dart';
class ApiClient {
  static final Dio _dio = Dio(BaseOptions(
    baseUrl: 'https://www.googleapis.com/books/v1/',
    connectTimeout: const Duration(seconds: 10),
    receiveTimeout: const Duration(seconds: 10),
  ));

  static final GoogleBooksApi api = GoogleBooksApi(_dio);
}
