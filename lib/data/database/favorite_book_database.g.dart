// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'favorite_book_database.dart';

// **************************************************************************
// FloorGenerator
// **************************************************************************

abstract class $FavoriteBookDatabaseBuilderContract {
  /// Adds migrations to the builder.
  $FavoriteBookDatabaseBuilderContract addMigrations(
      List<Migration> migrations);

  /// Adds a database [Callback] to the builder.
  $FavoriteBookDatabaseBuilderContract addCallback(Callback callback);

  /// Creates the database and initializes it.
  Future<FavoriteBookDatabase> build();
}

// ignore: avoid_classes_with_only_static_members
class $FloorFavoriteBookDatabase {
  /// Creates a database builder for a persistent database.
  /// Once a database is built, you should keep a reference to it and re-use it.
  static $FavoriteBookDatabaseBuilderContract databaseBuilder(String name) =>
      _$FavoriteBookDatabaseBuilder(name);

  /// Creates a database builder for an in memory database.
  /// Information stored in an in memory database disappears when the process is killed.
  /// Once a database is built, you should keep a reference to it and re-use it.
  static $FavoriteBookDatabaseBuilderContract inMemoryDatabaseBuilder() =>
      _$FavoriteBookDatabaseBuilder(null);
}

class _$FavoriteBookDatabaseBuilder
    implements $FavoriteBookDatabaseBuilderContract {
  _$FavoriteBookDatabaseBuilder(this.name);

  final String? name;

  final List<Migration> _migrations = [];

  Callback? _callback;

  @override
  $FavoriteBookDatabaseBuilderContract addMigrations(
      List<Migration> migrations) {
    _migrations.addAll(migrations);
    return this;
  }

  @override
  $FavoriteBookDatabaseBuilderContract addCallback(Callback callback) {
    _callback = callback;
    return this;
  }

  @override
  Future<FavoriteBookDatabase> build() async {
    final path = name != null
        ? await sqfliteDatabaseFactory.getDatabasePath(name!)
        : ':memory:';
    final database = _$FavoriteBookDatabase();
    database.database = await database.open(
      path,
      _migrations,
      _callback,
    );
    return database;
  }
}

class _$FavoriteBookDatabase extends FavoriteBookDatabase {
  _$FavoriteBookDatabase([StreamController<String>? listener]) {
    changeListener = listener ?? StreamController<String>.broadcast();
  }

  FavoriteBookDao? _favoriteBookDaoInstance;

  Future<sqflite.Database> open(
    String path,
    List<Migration> migrations, [
    Callback? callback,
  ]) async {
    final databaseOptions = sqflite.OpenDatabaseOptions(
      version: 1,
      onConfigure: (database) async {
        await database.execute('PRAGMA foreign_keys = ON');
        await callback?.onConfigure?.call(database);
      },
      onOpen: (database) async {
        await callback?.onOpen?.call(database);
      },
      onUpgrade: (database, startVersion, endVersion) async {
        await MigrationAdapter.runMigrations(
            database, startVersion, endVersion, migrations);

        await callback?.onUpgrade?.call(database, startVersion, endVersion);
      },
      onCreate: (database, version) async {
        await database.execute(
            'CREATE TABLE IF NOT EXISTS `favorites` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `subtitle` TEXT, `authors` TEXT, `publisher` TEXT, `publishedDate` TEXT, `pageCount` INTEGER, `description` TEXT, `imageUrl` TEXT, PRIMARY KEY (`id`))');

        await callback?.onCreate?.call(database, version);
      },
    );
    return sqfliteDatabaseFactory.openDatabase(path, options: databaseOptions);
  }

  @override
  FavoriteBookDao get favoriteBookDao {
    return _favoriteBookDaoInstance ??=
        _$FavoriteBookDao(database, changeListener);
  }
}

class _$FavoriteBookDao extends FavoriteBookDao {
  _$FavoriteBookDao(
    this.database,
    this.changeListener,
  )   : _queryAdapter = QueryAdapter(database, changeListener),
        _favoriteBookInsertionAdapter = InsertionAdapter(
            database,
            'favorites',
            (FavoriteBook item) => <String, Object?>{
                  'id': item.id,
                  'title': item.title,
                  'subtitle': item.subtitle,
                  'authors': item.authors,
                  'publisher': item.publisher,
                  'publishedDate': item.publishedDate,
                  'pageCount': item.pageCount,
                  'description': item.description,
                  'imageUrl': item.imageUrl
                },
            changeListener),
        _favoriteBookDeletionAdapter = DeletionAdapter(
            database,
            'favorites',
            ['id'],
            (FavoriteBook item) => <String, Object?>{
                  'id': item.id,
                  'title': item.title,
                  'subtitle': item.subtitle,
                  'authors': item.authors,
                  'publisher': item.publisher,
                  'publishedDate': item.publishedDate,
                  'pageCount': item.pageCount,
                  'description': item.description,
                  'imageUrl': item.imageUrl
                },
            changeListener);

  final sqflite.DatabaseExecutor database;

  final StreamController<String> changeListener;

  final QueryAdapter _queryAdapter;

  final InsertionAdapter<FavoriteBook> _favoriteBookInsertionAdapter;

  final DeletionAdapter<FavoriteBook> _favoriteBookDeletionAdapter;

  @override
  Future<FavoriteBook?> getFavoriteBook(String id) async {
    return _queryAdapter.query('SELECT * FROM favorites WHERE id = ?1',
        mapper: (Map<String, Object?> row) => FavoriteBook(
            id: row['id'] as String,
            title: row['title'] as String,
            subtitle: row['subtitle'] as String?,
            authors: row['authors'] as String?,
            publisher: row['publisher'] as String?,
            publishedDate: row['publishedDate'] as String?,
            pageCount: row['pageCount'] as int?,
            description: row['description'] as String?,
            imageUrl: row['imageUrl'] as String?),
        arguments: [id]);
  }

  @override
  Stream<List<FavoriteBook>> getAllFavoriteBooks() {
    return _queryAdapter.queryListStream(
        'SELECT * FROM favorites ORDER BY title ASC',
        mapper: (Map<String, Object?> row) => FavoriteBook(
            id: row['id'] as String,
            title: row['title'] as String,
            subtitle: row['subtitle'] as String?,
            authors: row['authors'] as String?,
            publisher: row['publisher'] as String?,
            publishedDate: row['publishedDate'] as String?,
            pageCount: row['pageCount'] as int?,
            description: row['description'] as String?,
            imageUrl: row['imageUrl'] as String?),
        queryableName: 'favorites',
        isView: false);
  }

  @override
  Future<void> insertFavoriteBook(FavoriteBook book) async {
    await _favoriteBookInsertionAdapter.insert(book, OnConflictStrategy.abort);
  }

  @override
  Future<void> deleteFavoriteBook(FavoriteBook book) async {
    await _favoriteBookDeletionAdapter.delete(book);
  }
}
