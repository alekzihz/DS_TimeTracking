import 'package:flutter/material.dart';
import 'package:practica_ds_timetracking/page_activities.dart';
import 'package:practica_ds_timetracking/tree.dart' as Tree hide getTree;
import 'package:practica_ds_timetracking/requests.dart';


List<String> list_period = <String>['Today','Yesterday','Last week', 'This week','Other'];
List<String> list_activity = <String>['Proyecto','Tarea'];
List<String> list_format = <String>['Web page','PDF','Text'];

class PageNewActivity extends StatefulWidget {

  @override
  State<PageNewActivity> createState() => _PageNewActivityState();
}

class _PageNewActivityState extends State<PageNewActivity> {

  String firstElementPeriod = list_period.first;
  String firstElementContent = list_activity.first;
  //String firstElementFormat = list_format.first;
  DateTime dateFrom  = DateTime.now();
  DateTime dateTo  = DateTime.now();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Creando Nueva Actividad'),
      ),
      body: Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[

        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
          child: TextFormField(
            decoration: const InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Nombre de la Actividad',
            ),
          ),
        ),


        Row(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
              child: Text("Tipo"),
            ),
            Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: DropdownButton(
                  value: firstElementContent,
                  items: list_activity.map((String i) {
                    return DropdownMenuItem(
                      value: i,
                      child: Text(i),
                    );
                  }).toList(),
                  onChanged: (String? newValue) {
                    setState(() {
                      dateFrom = DateTime.now();
                      if(newValue==list_activity.elementAt(0)){
                        //Proyecto
                        dateFrom=DateTime.now();
                        dateTo=DateTime.now();
                      }
                      if(newValue==list_activity.elementAt(1)){
                        //task

                      }
                      firstElementContent = newValue!;
                    });
                  },)

            ),
          ],
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              child: TextButton(
                style: TextButton.styleFrom(
                  textStyle: const TextStyle(fontSize: 20),
                ),
                onPressed: () {

                    //_showMyDialog();

                },
                child: const Text('Crear Actividad'),),

            ),

          ],

        ),




    ],
    ),
    );
  }








}
