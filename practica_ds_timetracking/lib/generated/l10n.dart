// GENERATED CODE - DO NOT MODIFY BY HAND
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'intl/messages_all.dart';

// **************************************************************************
// Generator: Flutter Intl IDE plugin
// Made by Localizely
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, lines_longer_than_80_chars
// ignore_for_file: join_return_with_assignment, prefer_final_in_for_each
// ignore_for_file: avoid_redundant_argument_values, avoid_escaping_inner_quotes

class S {
  S();

  static S? _current;

  static S get current {
    assert(_current != null,
        'No instance of S was loaded. Try to initialize the S delegate before accessing S.current.');
    return _current!;
  }

  static const AppLocalizationDelegate delegate = AppLocalizationDelegate();

  static Future<S> load(Locale locale) {
    final name = (locale.countryCode?.isEmpty ?? false)
        ? locale.languageCode
        : locale.toString();
    final localeName = Intl.canonicalizedLocale(name);
    return initializeMessages(localeName).then((_) {
      Intl.defaultLocale = localeName;
      final instance = S();
      S._current = instance;

      return instance;
    });
  }

  static S of(BuildContext context) {
    final instance = S.maybeOf(context);
    assert(instance != null,
        'No instance of S present in the widget tree. Did you add S.delegate in localizationsDelegates?');
    return instance!;
  }

  static S? maybeOf(BuildContext context) {
    return Localizations.of<S>(context, S);
  }

  /// `en`
  String get language {
    return Intl.message(
      'en',
      name: 'language',
      desc: '',
      args: [],
    );
  }

  /// `Projects`
  String get title {
    return Intl.message(
      'Projects',
      name: 'title',
      desc: '',
      args: [],
    );
  }

  /// `Duration`
  String get Duration {
    return Intl.message(
      'Duration',
      name: 'Duration',
      desc: '',
      args: [],
    );
  }

  /// `Project`
  String get typeactivity {
    return Intl.message(
      'Project',
      name: 'typeactivity',
      desc: '',
      args: [],
    );
  }

  /// `Task`
  String get typeactivity2 {
    return Intl.message(
      'Task',
      name: 'typeactivity2',
      desc: '',
      args: [],
    );
  }

  /// `Creating a new Activity`
  String get appbar_createActivity {
    return Intl.message(
      'Creating a new Activity',
      name: 'appbar_createActivity',
      desc: '',
      args: [],
    );
  }

  /// `Name Activity`
  String get nameactivity {
    return Intl.message(
      'Name Activity',
      name: 'nameactivity',
      desc: '',
      args: [],
    );
  }

  /// `Type`
  String get type {
    return Intl.message(
      'Type',
      name: 'type',
      desc: '',
      args: [],
    );
  }

  /// `Create Activity`
  String get button_create {
    return Intl.message(
      'Create Activity',
      name: 'button_create',
      desc: '',
      args: [],
    );
  }

  /// `Initial Date: {date}`
  String commonInitialDateFormat(DateTime date) {
    final DateFormat dateDateFormat =
        DateFormat('EEE, M/d/y, HH:mm:ss', Intl.getCurrentLocale());
    final String dateString = dateDateFormat.format(date);

    return Intl.message(
      'Initial Date: $dateString',
      name: 'commonInitialDateFormat',
      desc: '',
      args: [dateString],
    );
  }

  /// `Final Date: {date}`
  String commonFinalDateFormat(DateTime date) {
    final DateFormat dateDateFormat =
        DateFormat('EEE, M/d/y, HH:mm:ss', Intl.getCurrentLocale());
    final String dateString = dateDateFormat.format(date);

    return Intl.message(
      'Final Date: $dateString',
      name: 'commonFinalDateFormat',
      desc: '',
      args: [dateString],
    );
  }
}

class AppLocalizationDelegate extends LocalizationsDelegate<S> {
  const AppLocalizationDelegate();

  List<Locale> get supportedLocales {
    return const <Locale>[
      Locale.fromSubtags(languageCode: 'en', countryCode: 'US'),
      Locale.fromSubtags(languageCode: 'es', countryCode: 'ES'),
    ];
  }

  @override
  bool isSupported(Locale locale) => _isSupported(locale);
  @override
  Future<S> load(Locale locale) => S.load(locale);
  @override
  bool shouldReload(AppLocalizationDelegate old) => false;

  bool _isSupported(Locale locale) {
    for (var supportedLocale in supportedLocales) {
      if (supportedLocale.languageCode == locale.languageCode) {
        return true;
      }
    }
    return false;
  }
}
