import 'dart:async';
import 'package:floor/floor.dart';
import 'package:sqflite/sqflite.dart' as sqflite;
import '../database/favorite_book.dart';
import '../database/favorite_book_dao.dart';
//
part 'favorite_book_database.g.dart'; // Generated file

@Database(version: 1, entities: [FavoriteBook])
abstract class FavoriteBookDatabase extends FloorDatabase {
  FavoriteBookDao get favoriteBookDao;
}
