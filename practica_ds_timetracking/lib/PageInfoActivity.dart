import 'package:flutter/material.dart';
import 'package:practica_ds_timetracking/page_activities.dart';
import 'package:practica_ds_timetracking/tree.dart' as Tree hide getTree;
import 'package:practica_ds_timetracking/requests.dart';


class PageInfoActivity extends StatefulWidget {
  //const PageInfoActivity({Key? key}) : super(key: key);
  late Tree.Activity activity;
  PageInfoActivity(Tree.Activity activity){
    //print(activity.name);
    this.activity = activity;
  }

  @override
  State<PageInfoActivity> createState() => _PageInfoActivityState();
}

class _PageInfoActivityState extends State<PageInfoActivity> {
  late Tree.Activity ac;




  @override
  void initState() {
    super.initState();
    ac = widget.activity;
    // the root is a task and the children its intervals
  }

  //


  @override
  Widget build(BuildContext context) {
    String strDuration = Duration(seconds: ac.duration).toString().split('.').first;
    String strInitialDate = ac.initialDate.toString().split('.')[0];
    String strFinalDate = ac.finalDate.toString().split('.')[0];
    return Scaffold(
      appBar: AppBar(
        title: Text(ac.name),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Row(
            children: [
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: Text(ac.name),
              ),
            ],
          ),
          Row(
            children: [
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: Text(strDuration),
              ),
            ],
          ),

          Row(
            children: [
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: Text(strInitialDate),
              ),
            ],
          ),
          Row(
            children: [
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: Text(strFinalDate),
              ),
            ],
          ),

          Row(
            children: [
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: Text(ac.tag),
              ),
            ],
          ),






        ],
      ),
    );
  }
}
