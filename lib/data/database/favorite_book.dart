import 'package:floor/floor.dart';

@Entity(tableName: 'favorites')
class FavoriteBook {
  @PrimaryKey()
  final String id;
  final String title;
  final String? subtitle;
  final String? authors;
  final String? publisher;
  final String? publishedDate;
  final int? pageCount;
  final String? description;
  final String? imageUrl;

  FavoriteBook({
    required this.id,
    required this.title,
    this.subtitle,
    this.authors,
    this.publisher,
    this.publishedDate,
    this.pageCount,
    this.description,
    this.imageUrl,
  });


  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'subtitle': subtitle,
      'authors': authors,
      'publisher': publisher,
      'publishedDate': publishedDate,
      'pageCount': pageCount,
      'description': description,
      'imageUrl': imageUrl,
    };
  }

  factory FavoriteBook.fromMap(Map<String, dynamic> map) {
    return FavoriteBook(
      id: map['id'],
      title: map['title'],
      subtitle: map['subtitle'],
      authors: map['authors'],
      publisher: map['publisher'],
      publishedDate: map['publishedDate'],
      pageCount: map['pageCount'],
      description: map['description'],
      imageUrl: map['imageUrl'],
    );
  }
}