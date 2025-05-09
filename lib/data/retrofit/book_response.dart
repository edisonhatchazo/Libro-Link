import 'package:json_annotation/json_annotation.dart';

part 'book_response.g.dart';

@JsonSerializable()
class BookResponse {
  final List<BookItem>? items;

  BookResponse({this.items});

  factory BookResponse.fromJson(Map<String, dynamic> json) => _$BookResponseFromJson(json);
  Map<String, dynamic> toJson() => _$BookResponseToJson(this);
}

@JsonSerializable()
class BookItem {
  final String id;
  final VolumeInfo volumeInfo;
  final AccessInfo accessInfo;

  BookItem({required this.id, required this.volumeInfo, required this.accessInfo});

  factory BookItem.fromJson(Map<String, dynamic> json) => _$BookItemFromJson(json);
  Map<String, dynamic> toJson() => _$BookItemToJson(this);
}

@JsonSerializable()
class VolumeInfo {
  final String title;
  final String? subtitle;
  final List<String>? authors;
  final String? publisher;
  final String? publishedDate;
  final String? printType;
  final List<IndustryIdentifier>? industryIdentifiers;
  final dynamic pageCount;
  final List<String>? categories;
  final String? description;
  final ImageLinks? imageLinks;
  final String? previewLink;
  final String? maturityRating;
  final String? language;

  VolumeInfo({
    required this.title,
    this.subtitle,
    this.authors,
    this.publisher,
    this.publishedDate,
    this.printType,
    this.industryIdentifiers,
    this.pageCount,
    this.categories,
    this.description,
    this.imageLinks,
    this.previewLink,
    this.maturityRating,
    this.language,
  });

  factory VolumeInfo.fromJson(Map<String, dynamic> json) {
    return VolumeInfo(
      title: json['title'] as String,
      subtitle: json['subtitle'] as String?,
      authors: (json['authors'] as List?)?.map((e) => e as String).toList(),
      publisher: json['publisher'] as String?,
      publishedDate: json['publishedDate'] as String?,
      printType: json['printType'] as String?,
      industryIdentifiers: (json['industryIdentifiers'] as List?)
          ?.map((e) => IndustryIdentifier.fromJson(e as Map<String, dynamic>))
          .toList(),
      pageCount: json['pageCount'],  // Let it be dynamic (could be int or String)
      categories: (json['categories'] as List?)?.map((e) => e as String).toList(),
      description: json['description'] as String?,
      imageLinks: json['imageLinks'] == null
          ? null
          : ImageLinks.fromJson(json['imageLinks'] as Map<String, dynamic>),
      previewLink: json['previewLink'] as String?,
      maturityRating: json['maturityRating'] as String?,
      language: json['language'] as String?,
    );
  }
  Map<String, dynamic> toJson() => _$VolumeInfoToJson(this);
}

@JsonSerializable()
class AccessInfo {
  final String? viewability;
  final String? webReaderLink;

  AccessInfo({this.viewability, this.webReaderLink});

  factory AccessInfo.fromJson(Map<String, dynamic> json) => _$AccessInfoFromJson(json);
  Map<String, dynamic> toJson() => _$AccessInfoToJson(this);
}

@JsonSerializable()
class IndustryIdentifier {
  final String type;
  final String identifier;

  IndustryIdentifier({required this.type, required this.identifier});

  factory IndustryIdentifier.fromJson(Map<String, dynamic> json) => _$IndustryIdentifierFromJson(json);
  Map<String, dynamic> toJson() => _$IndustryIdentifierToJson(this);
}

@JsonSerializable()
class ImageLinks {
  final String? thumbnail;

  ImageLinks({this.thumbnail});

  factory ImageLinks.fromJson(Map<String, dynamic> json) => _$ImageLinksFromJson(json);
  Map<String, dynamic> toJson() => _$ImageLinksToJson(this);
}
