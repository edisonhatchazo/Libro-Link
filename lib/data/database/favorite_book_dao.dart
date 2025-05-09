import 'package:floor/floor.dart';
import '../database/favorite_book.dart';

@dao
abstract class FavoriteBookDao {
  @insert
  Future<void> insertFavoriteBook(FavoriteBook book);

  @delete
  Future<void> deleteFavoriteBook(FavoriteBook book);

  @Query('SELECT * FROM favorites WHERE id = :id')
  Future<FavoriteBook?> getFavoriteBook(String id);

  @Query('SELECT * FROM favorites ORDER BY title ASC')
  Stream<List<FavoriteBook>> getAllFavoriteBooks();
}
