import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:practica_ds_timetracking/tree.dart' hide getTree;
import 'package:practica_ds_timetracking/PageIntervals.dart';
import 'package:practica_ds_timetracking/requests.dart';
import 'package:practica_ds_timetracking/PageNewActivity.dart';
import 'package:practica_ds_timetracking/PageInfoActivity.dart';


import 'package:flutter_localizations/flutter_localizations.dart';
import 'generated/l10n.dart';


import 'dart:async';

//TODO: Al volver o dar click al boton HOME te retorna un JSON obsoleto
//TODO: Devolver el Json actual con las tareas/proyectos actuales y el estado de tareas activas.
//TODO: actualizar UML

class PageActivities extends StatefulWidget {
  late int id;
  PageActivities(this.id);
  @override
  _PageActivitiesState createState() => _PageActivitiesState();
}

class _PageActivitiesState extends State<PageActivities> {


  late int  id;
  late Future<Tree> futureTree;
  late Timer  _timer;
  static const int  periodeRefresh = 2;
  List<Activity> lista_activity = [];

  //late String title="Proyectos Principales";

  //button search
  late String value="" ;

  //filtro
  late bool band=false;
  late int indexR;

  //lenguaje
  late Locale c;
  late bool language=false;


  @override
  void initState() {
    super.initState();

    id = widget.id;
    futureTree = getTree(id);
    _activateTimer();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(
      future: futureTree,
      // this makes the tree of children, when available, go into snapshot.data
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          return Scaffold(
            appBar: snapshot.data!.root.name=="root"? AppBar(
              title:Text(S.of(context).title),
              automaticallyImplyLeading: false,
              centerTitle: true,// updated 16-dec-2022
              actions: <Widget>[
                IconButton(onPressed: (){
                  if(language){
                    setState(() {
                      S.load(const Locale('en_US'));
                      language=false;
                    });

                  }else{
                    setState(() {
                      S.load(const Locale('es_ES'));
                      language=true;
                    });

                  }
                },
                    icon: Icon(Icons.language)
                ),
                IconButton(icon: Icon(Icons.home),
                    onPressed: () {
                      while(Navigator.of(context).canPop()) {
                        print("pop");
                        Navigator.of(context).pop();
                      }
                      //MaterialPageRoute(builder: (context) => PageActivities(0));
                    }
                  // TODO go home page = root
                ),
                //TODO other actions
              ],
            ) :AppBar(
              title: Text(snapshot.data!.root.name), // updated 16-dec-2022
              actions: <Widget>[
                IconButton(onPressed: (){
                  if(language){
                    setState(() {
                      S.load(const Locale('en_US'));
                      language=false;
                    });

                  }else{
                    setState(() {
                      S.load(const Locale('es_ES'));
                      //Intl.getCurrentLocale();
                      language=true;
                      });
                    }
                  },
                icon: Icon(Icons.language)
                ),
                IconButton(icon: Icon(Icons.home),
                    onPressed: () {
                      while (Navigator.of(context).canPop()) {
                        print("POP");
                        Navigator.popUntil(context, ModalRoute.withName('/'));
                      }
                      PageActivities(0);
                    } // TODO go home page = root
                ),
                //TODO other actions
              ],
            ),
            body:
                Column(
                  children: <Widget>[
                    TextField(
                      decoration: InputDecoration(
                        prefixIcon: Icon(Icons.search),
                      ),
                      onChanged: (String val){
                        if(val!=""){
                          //title="Buscando...";
                          futureTree=searchByTag(val);
                          value = val;

                          //_refresh();
                          //_activateTimer();
                          setState(() {});

                        }else{
                          //title="Proy_ectos Principales";
                          FocusScope.of(context).unfocus();
                          futureTree=getTree(0);
                          //_activateTimer();
                          //initState();

                          Navigator.push(
                            context,
                            //it returns to updated tree.
                            MaterialPageRoute(builder: (context) => PageActivities(0)),
                          );
                          //_refresh();





                         //setState(() {});
                        }


                      },
                    ),
                    Align(
                      alignment: Alignment.topRight,
                      child: IconButton(onPressed: (){
                        //futureTree.
                        if(band){
                          print(snapshot.data!.root.children.length);
                          print(band);
                          band=false;
                        }else{
                          band=true;
                        }
                        setState(() {
                        });
                        //TODO funcion sort.
                      },
                          icon: Icon(Icons.filter_alt_outlined)),
                    ),

                    Expanded(child:
                    ListView.separated(
                      // it's like ListView.builder() but better because it includes a separator between items
                      //itemCount:10,

                      padding: const EdgeInsets.all(16.0),


                      //reverse: band,
                      itemCount: snapshot.data!.root.children.length, // updated 16-dec-2022
                      itemBuilder: (BuildContext context, int index) =>
                      //activity, index

                     !band ?_buildRow(snapshot.data!.root.children[index], index)
                     :_buildRow(snapshot.data!.root.children[snapshot.data!.root.children.length-1-index], index),


                      separatorBuilder: (BuildContext context, int index) =>
                      const Divider(),
                    )

                    )


                  ],
                ),
            floatingActionButton: FloatingActionButton(
              onPressed: () {
                Navigator.of(context)
                    .push(MaterialPageRoute<void>(
                  builder: (context) => PageNewActivity(this.id),
                ));
              },
              backgroundColor: Colors.green,
              child: const Icon(Icons.more_time),
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
  @override
  void dispose() {
    // "The framework calls this method when this State object will never build again"
    // therefore when going up
    _timer.cancel();
    super.dispose();
  }
  Widget _buildRow(Activity activity, int index) {
    String strDuration = Duration(seconds: activity.duration).toString().split('.').first;
    // split by '.' and taking first element of resulting list removes the microseconds part
    if (activity is Project) {
      return ListTile(
        title: Text('${activity.name}'),
        subtitle: Text(S.of(context).typeactivity),
        trailing: Wrap(
          children: <Widget> [
            (activity as Project).active ? new Text('$strDuration',
              style: TextStyle(color: Colors.green),
            ):
            new Text('$strDuration',
              style: TextStyle(color: Colors.black),
            ),
          ],
        ),
        onTap: () => _navigateDownActivities(activity.id),
        leading: IconButton(
          alignment: Alignment.center,
          icon: const Icon(Icons.info),
          onPressed: () {
            //_navigateDownProjectIntervals(activity.id);
            Navigator.of(context)
                .push(MaterialPageRoute<void>(
              builder: (context) => PageInfoActivity(activity),
            ));
          },
        ),
      );
    } else {


      Task task = activity as Task;
      // at the moment is the same, maybe changes in the future
      Widget trailing;
      Widget trailingButton;
      //trailing = Text('$strDuration');
      //trailingButton = Icon(Icons.not_started_outlined);

      return ListTile(
        title: Text('${activity.name}'),
        subtitle: Text(S.of(context).typeactivity2),
        trailing: Wrap(
          spacing: 50,
          children: <Widget>[
            FloatingActionButton.small(
              onPressed: () {
                // Envuelve la reproducción o pausa en una llamada a `setState`. Esto asegura
                // que se muestra el icono correcto
                setState(() {
                  // Si el vídeo se está reproduciendo, pausalo.
                  if ((activity as Task).active) {
                    stop(activity.id);
                    _refresh();
                  } else {
                    start(activity.id);
                    _refresh();
                  }
                });
              },
              // Muestra el icono correcto dependiendo del estado de la tarea
              child: Icon(
                (activity as Task).active ? Icons.pause_circle_outline : Icons.play_arrow_outlined,
              ),
            ),
            //icon: activity.active ? new Icon(Icons.pause):new Icon(Icons.play_arrow),
            //cambia el color del texto duracion cuando la tarea esta activa
            (activity as Task).active ? new Text('$strDuration',
              style: TextStyle(color: Colors.green),
            ):
            new Text('$strDuration',
              style: TextStyle(color: Colors.black),
            ),
          ],
        ),
        onTap: () => _navigateDownIntervals(activity.id),
        leading: IconButton(
          alignment: Alignment.center,
          icon: const Icon(Icons.info),
          onPressed: () {
            //_navigateDownProjectIntervals(activity.id);
            Navigator.of(context)
                .push(MaterialPageRoute<void>(
              builder: (context) => PageInfoActivity(activity),
            ));
          },
        ),
        onLongPress: () {
          if ((activity as Task).active) {
            stop(activity.id);
            _refresh();
          } else {
            start(activity.id);
            _refresh();
          }
        }, // TODO start/stop counting the time for tis task
      );
    }
  }
  //construye el arbol de hijos del proyecto
  void _navigateDownActivities(int childId) {
    Navigator.of(context).push(MaterialPageRoute<void>(
      builder: (context) => PageActivities(childId),
    )).then((var value) {
      _activateTimer();
      _refresh();

    });
  }
  void _navigateDownIntervals(int childId) {
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => PageIntervals(childId),
    )).then((var value) {
      _activateTimer();
      _refresh();
    });
  }
  void _refresh() async {
    futureTree = getTree(id); // to be used in build()
    setState(() {});
  }

  void _activateTimer() {
    _timer = Timer.periodic(Duration(seconds: periodeRefresh), (Timer t) {
      if(value==""){
        futureTree = getTree(id);
      }
      setState(() {});
    });
  }



}


