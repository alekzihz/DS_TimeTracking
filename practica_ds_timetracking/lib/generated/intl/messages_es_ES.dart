// DO NOT EDIT. This is code generated via package:intl/generate_localized.dart
// This is a library that provides messages for a es_ES locale. All the
// messages from the main program should be duplicated here with the same
// function name.

// Ignore issues from commonly used lints in this file.
// ignore_for_file:unnecessary_brace_in_string_interps, unnecessary_new
// ignore_for_file:prefer_single_quotes,comment_references, directives_ordering
// ignore_for_file:annotate_overrides,prefer_generic_function_type_aliases
// ignore_for_file:unused_import, file_names, avoid_escaping_inner_quotes
// ignore_for_file:unnecessary_string_interpolations, unnecessary_string_escapes

import 'package:intl/intl.dart';
import 'package:intl/message_lookup_by_library.dart';

final messages = new MessageLookup();

typedef String MessageIfAbsent(String messageStr, List<dynamic> args);

class MessageLookup extends MessageLookupByLibrary {
  String get localeName => 'es_ES';

  static String m0(date) => "Fecha Final: ${date}";

  static String m1(date) => "Fecha Inicial: ${date}";

  final messages = _notInlinedMessages(_notInlinedMessages);
  static Map<String, Function> _notInlinedMessages(_) => <String, Function>{
        "Duration": MessageLookupByLibrary.simpleMessage("Duracion"),
        "appbar_createActivity":
            MessageLookupByLibrary.simpleMessage("Creando una Actividad"),
        "button_create":
            MessageLookupByLibrary.simpleMessage("Crear Actividad"),
        "commonFinalDateFormat": m0,
        "commonInitialDateFormat": m1,
        "language": MessageLookupByLibrary.simpleMessage("es"),
        "nameactivity":
            MessageLookupByLibrary.simpleMessage("Nombre de la Actividad"),
        "title": MessageLookupByLibrary.simpleMessage("Proyectos Principales"),
        "type": MessageLookupByLibrary.simpleMessage("Tipo"),
        "typeactivity": MessageLookupByLibrary.simpleMessage("Proyecto"),
        "typeactivity2": MessageLookupByLibrary.simpleMessage("Tarea")
      };
}
