import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:libro_link/ui/home/home.dart';
import 'package:libro_link/ui/favorites/books.dart';
import 'package:libro_link/ui/home/home_view_model.dart';
import 'package:provider/provider.dart';

class BottomNavigation extends StatefulWidget {
  final Function(ThemeMode) onThemeChange;

  const BottomNavigation({required this.onThemeChange, super.key});

  @override
  State<BottomNavigation> createState() => _BottomNavigationState();
}

class _BottomNavigationState extends State<BottomNavigation> {
  int _selectedIndex = 0;
  final dio = Dio(BaseOptions(
    baseUrl: 'https://www.googleapis.com/books/v1/',
  ));
  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    bool isDarkMode = Theme.of(context).brightness == Brightness.dark;
    final List<Widget> screens = [
      ChangeNotifierProvider(
        create: (_) => HomeViewModel(dio),
        child: Home(
          onThemeChange: widget.onThemeChange,
          navigateToBookDetailScreen: (String id) {
            Navigator.pushNamed(
            context,
            '/details',
            arguments: id,
            );
          }
        ),
      ),
      Books(
          onThemeChange: widget.onThemeChange,
          navigateToBookDetailScreen: (String id) {
            Navigator.pushNamed(
              context,
              '/details',
              arguments: id,
            ).then((_){
              setState(() {});
            });
          }
      ),
    ];

    return Scaffold(
      body: screens[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        backgroundColor: isDarkMode ? Colors.grey[600] : Colors.grey[800],
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
        selectedItemColor: Colors.white,
        unselectedItemColor: Colors.grey,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.favorite), label: 'Books'),
        ],
      ),
    );
  }
}
