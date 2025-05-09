# Libro-Link

**Description:**
Libro Link is a Flutter-based application designed to provide a comprehensive digital platform for managing, exploring, and interacting with books. The app allows users to search for books, view detailed descriptions, explore books on Google Books, and manage a personalized favorites library. With seamless navigation, intuitive UI, and support for dark and light modes, Libro Link enhances the user's reading and book management experience across platforms like Android, Windows, and Web.

**Features:**
1. Home Screen:
Search Functionality:
Search for books using keywords and explore the vast Google Books catalog.
Grid and List Views:
Toggle between grid and list views for book display.
Book Previews:
Display book details such as title, author, description, and cover image.
Navigate to Details:
Tap on a book to view its detailed information.
2. Book Details Screen:
Detailed Book Information:
View book details, including title, subtitle, authors, publisher, published date, page count, categories, description, and more.
Favorite Management:
Add or remove books from your favorites.
Explore in Google Books:
Open the book in Google Books to read or preview it (depending on availability).
3. My Books Screen:
Favorites Library:
View and manage your favorite books.
Grid and List Views:
Toggle between grid and list views for easier navigation.
4. Book Reading Screen:
Integrated WebView:
Allows users to read books directly through Google Books' web interface (if available).
5. Offline Features:
Favorite Book Persistence:
Favorites are saved using a local SQLite database (via Floor ORM), ensuring data remains available offline.
6. Dark and Light Mode Support:
Dynamic Theme Switching:
Users can toggle between dark and light modes or use the system's default theme setting.

**Technical Highlights:**
1. Libraries and Frameworks:
- Flutter:
Cross-platform UI toolkit for building natively compiled applications for mobile, web, and desktop from a single codebase.
- Dio:
Powerful HTTP client for making API requests to the Google Books API.
- Drift / Floor / Hive:
Local storage solutions used for persisting favorite books (Drift and Floor for SQL-based, Hive for key-value).
- Provider:
State management library for efficient UI updates.
- WebView:
To render Google Books' reading interface for supported books.
- URL Launcher:
  Opens external book preview links in the default browser.
2. Google Books API Integration:
The app fetches book details and supports reading links directly from the Google Books API. It processes and displays metadata, including titles, authors, publishers, descriptions, and images.
3. Localization:
Uses Flutter’s localization system (.arb files and intl package) to support multiple languages and improve accessibility for a global audience.

**How to Use:**
1. Launch the App:
Open LibroLink on your device.

2. Search for Books:
Use the search bar on the Home screen to find books by keywords.

3. View Book Details:
Tap on any book to see detailed information and options.

4. Manage Favorites:
Mark books as favorites from the details screen and view them in the "My Books" section.

5. Read Books:
Click the "Explore in Google Books" button to read or preview books directly.

6. Switch Themes:
Use the top-right icon to toggle between dark and light modes.

**Acknowledgments:**
Special thanks to:

- Google Books API for providing book data and previews.
- Flutter for enabling a modern UI design.
- Open-source contributors for providing tools and libraries.

**License:**
This application is for educational and non-commercial use. For any other purposes, please seek appropriate permissions.
