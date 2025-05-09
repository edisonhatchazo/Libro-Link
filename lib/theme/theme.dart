// theme.dart
import 'package:flutter/material.dart';
import 'package:libro_link/theme/colors.dart';

final ThemeData lightTheme = ThemeData(
  brightness: Brightness.light,
  colorScheme: ColorScheme.light(
    primary: purpleGrey40,
    secondary: purple40,
    tertiary: pink40,
    surface: Color(0xFFFFFBFE),
    onPrimary: Colors.white,
    onSecondary: Colors.white,
    onTertiary: Colors.white,
    onSurface: Color(0xFF1C1B1F),
  ),
  appBarTheme: AppBarTheme(
    backgroundColor: purple40,
    foregroundColor: Colors.white,
  ),
);

final ThemeData darkTheme = ThemeData(
  brightness: Brightness.dark,
  colorScheme: ColorScheme.dark(
    primary: purpleGrey40,
    secondary: purple80,
    tertiary: pink80,
    surface: Color(0xFF121212),
    onPrimary: Colors.black,
    onSecondary: Colors.black,
    onTertiary: Colors.black,
    onSurface: Colors.white,
  ),
  appBarTheme: AppBarTheme(
    backgroundColor: purple80,
    foregroundColor: Colors.black,
  ),
);
