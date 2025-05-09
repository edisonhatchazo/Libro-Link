import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:libro_link/data/database/favorite_book_database.dart';
import 'package:libro_link/data/database/favorite_book_repository.dart';
import 'package:libro_link/ui/home/book_details_view_model.dart';
import 'package:libro_link/data/retrofit/google_books_api.dart';
import 'package:provider/provider.dart';
import 'package:url_launcher/url_launcher.dart';


class BookDetails extends StatefulWidget {
  final Function(ThemeMode) onThemeChange;
  final String bookId;
  final Function(String) navigateToBookReadingScreen;
  const BookDetails({
    required this.onThemeChange,
    required this.bookId,
    required this.navigateToBookReadingScreen,
    super.key
  });

  @override
  State<BookDetails> createState() => _BookDetailsState();
}

class _BookDetailsState extends State<BookDetails> {

  void _launchBookUrl(String? url) async {
    if (url == null) return;

    final uri = Uri.parse(url);

    if (await canLaunchUrl(uri)) {
      final launched = await launchUrl(uri, mode: LaunchMode.externalApplication);
      if (!launched) {
        print('Could not launch: $uri');
      }
    } else {
      print('Cannot launch URL: $uri');
    }
  }


  Future<FavoriteBookRepository> _initRepository() async {
    final database = await $FloorFavoriteBookDatabase
        .databaseBuilder('favorite_book_database.db')
        .build();
    return FavoriteBookRepository(database.favoriteBookDao);
  }


  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;

    final dio = Dio(BaseOptions(
      baseUrl: 'https://www.googleapis.com/books/v1/',
    ));

    return FutureBuilder<FavoriteBookRepository>(
      future: _initRepository(),
      builder: (context,snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return const Center(child: CircularProgressIndicator(),);
        }

        if (snapshot.hasError) {
          return Center(
            child: Text("Error initializing database: ${snapshot.error}"),);
        }

        final repository = snapshot.data!;

        return ChangeNotifierProvider(
          create: (_) =>
              BookDetailsViewModel(
                  api: GoogleBooksApi(dio),
                  bookId: widget.bookId,
                  repository: repository
              ),
          child: Consumer<BookDetailsViewModel>(
            builder: (context, viewModel, _) {
              final book = viewModel.book;
              return Scaffold(
                  appBar: BookDetailsTopAppBar(
                    title: "Book Details",
                    isDarkMode: isDark,
                    onThemeChange: (newTheme) => widget.onThemeChange(newTheme),
                    isFavorite: viewModel.isFavorite,
                    // You should define this in your ViewModel
                    onFavoriteToggle: () {
                      viewModel.toggleFavorite(); // Toggle favorite logic
                    },
                  ),
                  body: book == null
                      ? const Center(child: CircularProgressIndicator())
                      : SingleChildScrollView(
                    padding: const EdgeInsets.all(8.0),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Card(
                          shape: RoundedRectangleBorder(
                            side: BorderSide(
                              color: isDark ? Colors.white : Colors.black,
                              width: 1,
                            ),
                            borderRadius: BorderRadius.circular(8),
                          ),
                          elevation: 4,
                          child: Padding(
                            padding: const EdgeInsets.all(16),
                            child: Table(
                              columnWidths: const{
                                0: FlexColumnWidth(),
                                1: FlexColumnWidth(),
                              },
                              defaultVerticalAlignment: TableCellVerticalAlignment
                                  .middle,
                              children: [
                                _buildTableRow("Title", book.volumeInfo.title),
                                _buildTableRow(
                                    "Subtitle", book.volumeInfo.subtitle),
                                _buildTableRow(
                                    "Publisher", book.volumeInfo.publisher),
                                _buildTableRow("Published Date",
                                    book.volumeInfo.publishedDate),
                                _buildTableRow("Authors",
                                    book.volumeInfo.authors?.join(', ')),
                                _buildTableRow("Page Count",
                                    book.volumeInfo.pageCount?.toString()),
                                _buildTableRow("Maturity Rating",
                                    book.volumeInfo.maturityRating),
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(height: 16),
                        Card(
                          shape: RoundedRectangleBorder(
                            side: BorderSide(
                              color: isDark ? Colors.white : Colors.black,
                              width: 1,
                            ),
                            borderRadius: BorderRadius.circular(8),
                          ),
                          elevation: 4,
                          child: Padding(
                            padding: const EdgeInsets.all(16),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Text(
                                  "Description",
                                  style: Theme
                                      .of(context)
                                      .textTheme
                                      .headlineSmall,
                                ),
                                const SizedBox(height: 8),
                                Text(
                                  book.volumeInfo.description ??
                                      "No Description Available.",
                                  textAlign: TextAlign.justify,
                                )
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(height: 16),
                        TextButton(
                            onPressed: () {
                              _launchBookUrl(book.volumeInfo.previewLink);
                            },
                            style: TextButton.styleFrom(
                              backgroundColor: Colors.brown,
                              foregroundColor: Colors.white,
                            ),
                            child: Text(
                              "Explore in Google Books",
                              style: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyMedium,
                            )
                        )
                      ],
                    ),
                  )
              );
            },
          ),
        );
      },
    );
  }

  TableRow _buildTableRow(String label, String? value) {
    return TableRow(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0),
          child: Text(
            "$label:",
            style: const TextStyle(fontWeight: FontWeight.bold),
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0),
          child: Text(value?.isNotEmpty == true ? value! : "—"),
        ),
      ],
    );
  }


}


class BookDetailsTopAppBar extends StatefulWidget implements PreferredSizeWidget {
  final String title;
  final bool isDarkMode;
  final Function(ThemeMode) onThemeChange;
  final bool isFavorite;
  final VoidCallback onFavoriteToggle;

  const BookDetailsTopAppBar({
    required this.title,
    required this.isDarkMode,
    required this.onThemeChange,
    required this.isFavorite,
    required this.onFavoriteToggle,
    super.key,
  });

  @override
  State<BookDetailsTopAppBar> createState() => _BookDetailsTopAppBarState();

  @override
  Size get preferredSize => const Size.fromHeight(50);
}

class _BookDetailsTopAppBarState extends State<BookDetailsTopAppBar> {
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
            tooltip: widget.isFavorite ? 'Remove from favorites' : 'Add to favorites',
            onPressed: widget.onFavoriteToggle,
            icon: Icon(
              widget.isFavorite ? Icons.favorite: Icons.favorite_border,
              color: Colors.redAccent,
            )
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