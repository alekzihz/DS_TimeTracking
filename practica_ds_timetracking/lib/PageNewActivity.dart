import 'package:flutter/material.dart';
import 'package:practica_ds_timetracking/page_activities.dart';
import 'package:practica_ds_timetracking/tree.dart' as Tree hide getTree;
import 'package:practica_ds_timetracking/requests.dart';

import 'generated/l10n.dart';


class PageNewActivity extends StatefulWidget {
  late int idParentActivity;
  PageNewActivity(int idParentActivity){
    this.idParentActivity=idParentActivity;

  }

  @override
  State<PageNewActivity> createState() => _PageNewActivityState();
}

class _PageNewActivityState extends State<PageNewActivity> {

  late int idParent = widget.idParentActivity;
  late String nameActivity;
  String tag="none";
  int typeActivity=0;


  //lenguage


  @override
  Widget build(BuildContext context) {
    String name = S.of(context).appbar_createActivity.toString();
    String firstElementContent;
    List<String> list_activity = <String>
      [S.of(context).typeactivity.toString(),
      S.of(context).typeactivity2.toString()];

    if(typeActivity==0){
      firstElementContent = list_activity.first;
    }else{
      firstElementContent = list_activity.last;
    }






    return Scaffold(
      appBar: AppBar(
        title: Text(S.of(context).appbar_createActivity),
      ),
      body: Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[

        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
          child: TextFormField(

            decoration: InputDecoration(
              border: UnderlineInputBorder(),
              labelText: name,

            ),
            onChanged: (texto){
              nameActivity=texto;
            },
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
          child: TextFormField(
            decoration: const InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Tag',
            ),
            onChanged: (texto){
              tag=texto;
            },
          ),
        ),
        Row(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
              child: Text(S.of(context).type),
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
                      //dateFrom = DateTime.now();
                      if(newValue==list_activity.elementAt(0)){

                        typeActivity=0;
                        //Proyecto
                      }
                      if(newValue==list_activity.elementAt(1)){
                        typeActivity=1;
                        //task

                      }
                      print(newValue);
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

                    addActivity(nameActivity, tag, typeActivity,idParent);
                    Navigator.of(context)
                        .push(MaterialPageRoute<void>(
                      builder: (context) => PageActivities(idParent),
                    ));


                    //_showMyDialog();
                },
                child: Text(S.of(context).button_create),
              ),

            ),

          ],

        ),




    ],
    ),
    );
  }








}
