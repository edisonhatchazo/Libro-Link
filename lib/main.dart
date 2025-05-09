import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:provider/provider.dart';
import 'package:libro_link/navigation/bottom_navigation.dart';
import 'package:libro_link/ui/home/home.dart';
import 'package:libro_link/ui/home/book_details.dart';
import 'package:libro_link/ui/favorites/books.dart';
import 'package:libro_link/theme/theme.dart';
import 'package:libro_link/data/retrofit/google_books_api.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        // Provide googleBooksApi if needed
        Provider<GoogleBooksApi>(
          create: (_) => GoogleBooksApi(Dio()),
        ),
      ],
      child: LibroLinkApp(),
    ),
  );
}

class LibroLinkApp extends StatefulWidget {
  const LibroLinkApp({super.key});

  @override
  _LibroLinkAppState createState() => _LibroLinkAppState();
}

class _LibroLinkAppState extends State<LibroLinkApp> {
  ThemeMode _themeMode = ThemeMode.system;

  void _changeTheme(ThemeMode newTheme) {
    setState(() {
      _themeMode = newTheme;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Libro Link',
      theme: lightTheme,
      darkTheme: darkTheme,
      themeMode: _themeMode, // Dynamic theme switching
      initialRoute: '/',
      onGenerateRoute: (settings) {
        switch (settings.name) {
          case '/':
            return MaterialPageRoute(
              builder: (_) => BottomNavigation(onThemeChange: _changeTheme),
            );
          case '/home':
            return MaterialPageRoute(
              builder: (_) => Home(
                onThemeChange: _changeTheme,
                navigateToBookDetailScreen: (id) {
                  Navigator.pushNamed(
                    _,
                    '/details',
                    arguments: id,
                  );
                },
              ),
            );
          case '/details':
            final id = settings.arguments as String;
            return MaterialPageRoute(
              builder: (_) => BookDetails(
                onThemeChange: _changeTheme,
                bookId: id,
                navigateToBookReadingScreen: (id) {
                  Navigator.pushNamed(
                    _,
                    '/reading',
                    arguments: id,
                  );
                },
              ),
            );
          case '/favorites':
            return MaterialPageRoute(
              builder: (_) => Books(
                onThemeChange: _changeTheme,
                navigateToBookDetailScreen: (id) {
                  Navigator.pushNamed(
                    _,
                    '/details',
                    arguments: id,
                  ).then((_){
                    setState(() {});
                  });
                },
              ),

            );
          default:
            return null;
        }
      },
    );
  }
}

