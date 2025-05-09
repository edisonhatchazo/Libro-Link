import 'package:flutter/material.dart';

import 'package:libro_link/data/database/favorite_book_database.dart';
import 'package:libro_link/data/database/favorite_book_repository.dart';
import 'package:libro_link/data/database/favorite_book.dart';
class Books extends StatefulWidget {
  final Function(ThemeMode) onThemeChange;
  final Function(String) navigateToBookDetailScreen;
  const Books({
    required this.onThemeChange,
    required this.navigateToBookDetailScreen,
    super.key
  });

  @override
  State<Books> createState() => _BooksState();
}

class _BooksState extends State<Books> {
  String _viewMode = "image";

  void _toggleView(){
    setState(() {
      _viewMode = _viewMode == "image" ? "description" : "image";
    });
  }

  Future<FavoriteBookRepository> _initRepository() async {
    final db = await $FloorFavoriteBookDatabase
        .databaseBuilder('favorite_book_database.db')
        .build();
    return FavoriteBookRepository(db.favoriteBookDao);
  }


  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;

    return FutureBuilder<FavoriteBookRepository>(
      future: _initRepository(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return const Center(child: CircularProgressIndicator());
        }

        if (snapshot.hasError) {
          return Center(child: Text('Error: ${snapshot.error}'));
        }

        final repository = snapshot.data!;

        return Scaffold(
          appBar: BooksTopAppBar(
            title: "Favorite Books",
            isDarkMode: isDark,
            onThemeChange: widget.onThemeChange,
            onToggleView: _toggleView,
          ),
          body: StreamBuilder<List<FavoriteBook>>(
            stream: repository.getAllFavoriteBooks(),
            builder: (context, snapshot) {
              if (!snapshot.hasData) {
                return const Center(child: CircularProgressIndicator());
              }

              final favorites = snapshot.data ?? [];
              if (favorites.isEmpty) {
                return const Center(child: Text("No favorites yet."));
              }

              return GridView.builder(
                padding: const EdgeInsets.all(8),
                itemCount: favorites.length,
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  crossAxisSpacing: 8.0,
                  mainAxisSpacing: 4.0,
                  childAspectRatio: 0.6,
                ),
                itemBuilder: (context, index) {
                  final book = favorites[index];
                  final imageUrl = book.imageUrl?.replaceFirst("http://", "https://");
                  return GestureDetector(
                      onTap: () => widget.navigateToBookDetailScreen(book.id),
                      child: Card(
                        elevation: 4,
                        shape: RoundedRectangleBorder(
                          side: BorderSide(
                            color: isDark ? Colors.white : Colors.black,
                            width: 1,
                          ),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: _viewMode == "image"
                                ? [
                              Expanded(
                                child: Image.network(
                                  imageUrl ?? '',
                                  fit: BoxFit.cover,
                                  width: double.infinity,
                                  errorBuilder: (context, error, stackTrace) =>
                                  const Icon(Icons.book, size: 32),
                                ),
                              ),
                              const SizedBox(height: 8),

                              Text(
                                book.title,
                                maxLines: 2,
                                overflow: TextOverflow.ellipsis,
                                textAlign: TextAlign.center,
                                style: const TextStyle(fontWeight: FontWeight.bold),
                              ),
                            ]
                                : [
                              Text(
                                book.title,
                                textAlign: TextAlign.center,
                                maxLines: 2,
                                overflow: TextOverflow.ellipsis,
                                style: const TextStyle(fontWeight: FontWeight.bold),
                              ),
                              const SizedBox(height: 8),
                              Text(
                                book.authors ?? "Unknown Author",
                                textAlign: TextAlign.center,
                                maxLines: 1,
                                overflow: TextOverflow.ellipsis,
                                style: const TextStyle(fontSize: 12),
                              ),
                              const SizedBox(height: 8),
                              Text(
                                book.description ?? "No Description",
                                textAlign: TextAlign.center,
                                maxLines: 5,
                                overflow: TextOverflow.ellipsis,
                                style: const TextStyle(fontSize: 12),
                              )
                            ],
                          ),
                        ),
                      )
                  );
                },
              );
            },
          ),
        );
      },
    );
  }

}

class BooksTopAppBar extends StatefulWidget implements PreferredSizeWidget {
  final String title;
  final bool isDarkMode;
  final Function(ThemeMode) onThemeChange;
  final Function() onToggleView;
  const BooksTopAppBar({
    required this.title,
    required this.isDarkMode,
    required this.onThemeChange,
    required this.onToggleView,
    super.key,
  });

  @override
  State<BooksTopAppBar> createState() => _BooksTopAppBarState();

  @override
  Size get preferredSize => const Size.fromHeight(50);
}

class _BooksTopAppBarState extends State<BooksTopAppBar> {
  bool isSearching = false;
  String searchQuery = '';

  void _toggleTheme() {
    final newThemeMode = widget.isDarkMode ? ThemeMode.light : ThemeMode.dark;
    widget.onThemeChange(newThemeMode);
  }

  @override
  Widget build(BuildContext context) {
    bool isDarkMode = Theme.of(context).brightness == Brightness.dark;
    return AppBar(
      backgroundColor: isDarkMode ? Colors.grey[600] : Colors.grey[800],
      foregroundColor: Color(0xFFCCC2DC),
      title: Text(widget.title),
      actions: [
        IconButton(
          icon: const Icon(Icons.list),
          tooltip: 'Toggle view',
          onPressed: widget.onToggleView,
        ),
        IconButton(
          icon: Icon(
            widget.isDarkMode ? Icons.dark_mode : Icons.light_mode,
            color: Colors.white,
          ),
          tooltip: 'Toggle theme',
          onPressed: _toggleTheme,
        ),
      ],
    );
  }
}