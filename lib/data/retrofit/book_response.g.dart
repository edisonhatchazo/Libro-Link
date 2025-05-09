// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'book_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BookResponse _$BookResponseFromJson(Map<String, dynamic> json) => BookResponse(
      items: (json['items'] as List<dynamic>?)
          ?.map((e) => BookItem.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$BookResponseToJson(BookResponse instance) =>
    <String, dynamic>{
      'items': instance.items,
    };

BookItem _$BookItemFromJson(Map<String, dynamic> json) => BookItem(
      id: json['id'] as String,
      volumeInfo:
          VolumeInfo.fromJson(json['volumeInfo'] as Map<String, dynamic>),
      accessInfo:
          AccessInfo.fromJson(json['accessInfo'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$BookItemToJson(BookItem instance) => <String, dynamic>{
      'id': instance.id,
      'volumeInfo': instance.volumeInfo,
      'accessInfo': instance.accessInfo,
    };

VolumeInfo _$VolumeInfoFromJson(Map<String, dynamic> json) => VolumeInfo(
      title: json['title'] as String,
      subtitle: json['subtitle'] as String?,
      authors:
          (json['authors'] as List<dynamic>?)?.map((e) => e as String).toList(),
      publisher: json['publisher'] as String?,
      publishedDate: json['publishedDate'] as String?,
      printType: json['printType'] as String?,
      industryIdentifiers: (json['industryIdentifiers'] as List<dynamic>?)
          ?.map((e) => IndustryIdentifier.fromJson(e as Map<String, dynamic>))
          .toList(),
      pageCount: json['pageCount'],
      categories: (json['categories'] as List<dynamic>?)
          ?.map((e) => e as String)
          .toList(),
      description: json['description'] as String?,
      imageLinks: json['imageLinks'] == null
          ? null
          : ImageLinks.fromJson(json['imageLinks'] as Map<String, dynamic>),
      previewLink: json['previewLink'] as String?,
      maturityRating: json['maturityRating'] as String?,
      language: json['language'] as String?,
    );

Map<String, dynamic> _$VolumeInfoToJson(VolumeInfo instance) =>
    <String, dynamic>{
      'title': instance.title,
      'subtitle': instance.subtitle,
      'authors': instance.authors,
      'publisher': instance.publisher,
      'publishedDate': instance.publishedDate,
      'printType': instance.printType,
      'industryIdentifiers': instance.industryIdentifiers,
      'pageCount': instance.pageCount,
      'categories': instance.categories,
      'description': instance.description,
      'imageLinks': instance.imageLinks,
      'previewLink': instance.previewLink,
      'maturityRating': instance.maturityRating,
      'language': instance.language,
    };

AccessInfo _$AccessInfoFromJson(Map<String, dynamic> json) => AccessInfo(
      viewability: json['viewability'] as String?,
      webReaderLink: json['webReaderLink'] as String?,
    );

Map<String, dynamic> _$AccessInfoToJson(AccessInfo instance) =>
    <String, dynamic>{
      'viewability': instance.viewability,
      'webReaderLink': instance.webReaderLink,
    };

IndustryIdentifier _$IndustryIdentifierFromJson(Map<String, dynamic> json) =>
    IndustryIdentifier(
      type: json['type'] as String,
      identifier: json['identifier'] as String,
    );

Map<String, dynamic> _$IndustryIdentifierToJson(IndustryIdentifier instance) =>
    <String, dynamic>{
      'type': instance.type,
      'identifier': instance.identifier,
    };

ImageLinks _$ImageLinksFromJson(Map<String, dynamic> json) => ImageLinks(
      thumbnail: json['thumbnail'] as String?,
    );

Map<String, dynamic> _$ImageLinksToJson(ImageLinks instance) =>
    <String, dynamic>{
      'thumbnail': instance.thumbnail,
    };
