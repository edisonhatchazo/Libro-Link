import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:libro_link/ui/home/home_view_model.dart';

class Home extends StatefulWidget {
  final Function(ThemeMode) onThemeChange;
  final Function(String) navigateToBookDetailScreen;
  const Home({
    super.key,
    required this.onThemeChange,
    required this.navigateToBookDetailScreen,
  });

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final ScrollController _scrollController = ScrollController();
  bool isLoading = false;
  String _viewMode = "image";
  String _searchQuery = "";

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  void _toggleView(){
    setState(() {
      _viewMode = _viewMode == "image" ? "description" : "image";
    });
  }

  void _handleSearch(String query) {
    setState(() {
      _searchQuery = query;
    });

    final viewModel = Provider.of<HomeViewModel>(context, listen: false);
    viewModel.clearBooks();
    viewModel.loadMoreBooks(query);
  }

  Future<void> _onLoadMore() async {
    await Provider.of<HomeViewModel>(context, listen: false).loadMoreBooks(_searchQuery);
  }

  void _onScroll() async {
    if (!isLoading &&
        _scrollController.position.pixels >=
            _scrollController.position.maxScrollExtent - 200) {
      setState(() => isLoading = true);

      await _onLoadMore();

      setState(() => isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    Provider.of<HomeViewModel>(context);
    final isDark = Theme.of(context).brightness == Brightness.dark;

    return Scaffold(
      appBar: HomeTopAppBar(
        title: 'Home',
        isDarkMode: isDark,
        onThemeChange: (newTheme) => widget.onThemeChange(newTheme),
        onSearch: _handleSearch,
        onToggleView: _toggleView,
      ),
      body: Consumer<HomeViewModel>(
        builder: (context, viewModel, _) {
          if (viewModel.books.isEmpty) {
            return const Center(child: Text("No books to display."));
          }
          return GridView.builder(
            controller: _scrollController,
            padding: const EdgeInsets.all(8),
            itemCount: viewModel.books.length,
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,          // 2 columns
              crossAxisSpacing: 8.0,      // horizontal spacing between cards
              mainAxisSpacing: 4.0,       // vertical spacing between cards
              childAspectRatio: 0.6,      // height-to-width ratio (adjust to fit your content)
            ),
            itemBuilder: (context, index) {
              final book = viewModel.books[index];
              final imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replaceFirst("http://", "https://");
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
                          book.volumeInfo.title,
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                          textAlign: TextAlign.center,
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ]
                          : [
                        Text(
                          book.volumeInfo.title,
                          textAlign: TextAlign.center,
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(height: 8),
                        Text(
                          book.volumeInfo.authors?.join(", ") ?? "Unknown Author",
                          textAlign: TextAlign.center,
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(fontSize: 12),
                        ),
                        const SizedBox(height: 8),
                        Text(
                          book.volumeInfo.description ?? "No Description",
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
  }
}

class HomeTopAppBar extends StatefulWidget implements PreferredSizeWidget {
  final String title;
  final bool isDarkMode;
  final Function(ThemeMode) onThemeChange;
  final Function(String) onSearch;
  final Function() onToggleView;
  const HomeTopAppBar({
    required this.title,
    required this.isDarkMode,
    required this.onThemeChange,
    required this.onSearch,
    required this.onToggleView,
    super.key,
  });

  @override
  State<HomeTopAppBar> createState() => _HomeTopAppBarState();

  @override
  Size get preferredSize => const Size.fromHeight(50);
}

class _HomeTopAppBarState extends State<HomeTopAppBar> {
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
      title: isSearching
          ? TextField(
        autofocus: true,
        style: const TextStyle(color: Colors.white),
        onChanged: (value) {
          setState(() {
            searchQuery = value;
          });
          widget.onSearch(value);
        },
        decoration: const InputDecoration(
          hintText: 'Search books...',
          border: InputBorder.none,
        ),
      )
          : Text(widget.title),
      actions: [
        IconButton(
          icon: const Icon(Icons.list),
          tooltip: 'Toggle view',
          onPressed: widget.onToggleView,
        ),
        IconButton(
          icon: Icon(isSearching ? Icons.close : Icons.search),
          tooltip: 'Search',
          onPressed: () {
            setState(() {
              isSearching = !isSearching;
              if (!isSearching) {
                searchQuery = '';
                widget.onSearch('');
              }
            });
          },
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