import 'favorite_book.dart';
import 'favorite_book_dao.dart';

class FavoriteBookRepository {
  final FavoriteBookDao dao;

  FavoriteBookRepository(this.dao);

  Future<void> insertFavoriteBook(FavoriteBook book) {
    return dao.insertFavoriteBook(book);
  }

  Future<void> deleteFavoriteBook(FavoriteBook book) {
    return dao.deleteFavoriteBook(book);
  }

  Future<FavoriteBook?> getFavoriteBook(String id) {
    return dao.getFavoriteBook(id);
  }

  Stream<List<FavoriteBook>> getAllFavoriteBooks() {
    return dao.getAllFavoriteBooks();
  }
}
