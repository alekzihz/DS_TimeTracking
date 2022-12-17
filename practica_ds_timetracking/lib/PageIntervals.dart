import 'package:flutter/material.dart';
import 'package:practica_ds_timetracking/tree.dart' as Tree hide getTree;
import 'package:practica_ds_timetracking/requests.dart';

class PageIntervals extends StatefulWidget {
  int id;
  PageIntervals(this.id);
  @override
  _PageIntervalsState createState() => _PageIntervalsState();
}

class _PageIntervalsState extends State<PageIntervals> {

  late int id;
  late Future<Tree.Tree> futureTree;

  @override
  void initState() {
    super.initState();
    id = widget.id;
    futureTree = getTree(id);
    // the root is a task and the children its intervals
  }
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree.Tree>(
      future: futureTree,
      // this makes the tree of children, when available, go into snapshot.data
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          int numChildren = snapshot.data!.root.children.length; // updated 16-dec-2022
          return Scaffold(
            appBar: AppBar(
              title: Text(snapshot.data!.root.name), // updated 16-dec-2022
              actions: <Widget>[
                IconButton(icon: Icon(Icons.home),
                  onPressed: () {
                    while(Navigator.of(context).canPop()) {
                      print("pop");
                      Navigator.of(context).pop();
                    }
                    /* this works also:
                        Navigator.popUntil(context, ModalRoute.withName('/'));
                      */
                    PageIntervals(1);

                  }, // TODO
                )
              ],
            ),
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: numChildren,
              itemBuilder: (BuildContext context, int index) =>
                  _buildRow(snapshot.data!.root.children[index], index), // updated 16-dec-2022
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(),
            ),
          );
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        // By default, show a progress indicator
        return Container(
            height: MediaQuery.of(context).size.height,
            color: Colors.white,
            child: Center(
              child: CircularProgressIndicator(),
            ));
      },
    );
  }

  Widget _buildRow(Tree.Interval interval, int index) {
    String strDuration = Duration(seconds: interval.duration).toString().split('.').first;
    String strInitialDate = interval.initialDate.toString().split('.')[0];
    // this removes the microseconds part
    String strFinalDate = interval.finalDate.toString().split('.')[0];
    return ListTile(
      title: Text('from ${strInitialDate} to ${strFinalDate}'),
      trailing: Text('$strDuration'),
    );
  }
}