package webserver;
import core.Component;
import core.Project;
import core.Task;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

public class WebServer {
  private static final int PORT = 8080; // port to listen to

  private Project currentActivity;
  private final Project root;

  public WebServer(Project root) {
    this.root = root;
    System.out.println(root);
    currentActivity = root;
    try {
      ServerSocket serverConnect = new ServerSocket(PORT);
      System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
      // we listen until user halts server execution
      while (true) {
        // each client connection will be managed in a dedicated Thread
        new SocketThread(serverConnect.accept());
        // create dedicated thread to manage the client connection
      }
    } catch (IOException e) {
      System.err.println("Server Connection error : " + e.getMessage());
    }
  }

  private Component findActivityById(int id) {
    return root.findActivityById(id);
  }

  private class SocketThread extends Thread {
    // SocketThread sees WebServer attributes
    private final Socket insocked;
    // Client Connection via Socket Class

    SocketThread(Socket insocket) {
      this.insocked = insocket;
      this.start();
    }

    @Override
    public void run() {
      // we manage our particular client connection
      BufferedReader in;
      PrintWriter out;
      String resource;

      try {
        // we read characters from the client via input stream on the socket
        in = new BufferedReader(new InputStreamReader(insocked.getInputStream()));
        // we get character output stream to client
        out = new PrintWriter(insocked.getOutputStream());
        // get first line of the request from the client
        String input = in.readLine();
        // we parse the request with a string tokenizer

        System.out.println("sockedthread : " + input);

        StringTokenizer parse = new StringTokenizer(input);
        String method = parse.nextToken().toUpperCase();
        // we get the HTTP method of the client
        if (!method.equals("GET")) {
          System.out.println("501 Not Implemented : " + method + " method.");
        } else {
          // what comes after "localhost:8080"
          resource = parse.nextToken();
          System.out.println("input " + input);
          System.out.println("method " + method);
          System.out.println("resource " + resource);

          parse = new StringTokenizer(resource, "/[?]=&");
          int i = 0;
          String[] tokens = new String[20];
          // more than the actual number of parameters
          while (parse.hasMoreTokens()) {
            tokens[i] = parse.nextToken();
            System.out.println("token " + i + "=" + tokens[i]);
            i++;
          }

          // Make the answer as a JSON string, to be sent to the Javascript client
          String answer = makeHeaderAnswer() + makeBodyAnswer(tokens);
          System.out.println("answer\n" + answer);
          // Here we send the response to the client
          out.println(answer);
          out.flush(); // flush character output stream buffer
        }

        in.close();
        out.close();
        insocked.close(); // we close socket connection
      } catch (Exception e) {
        System.err.println("Exception : " + e);
      }
    }


    private String makeBodyAnswer(String[] tokens) {
      String body = "";
      switch (tokens[0]) {
        case "get_tree" : {

          System.out.println("obteniendo tree");
          int id = Integer.parseInt(tokens[1]);

          ///System.out.println("mi id:" + id);
          Component activity = findActivityById(id);

          System.out.println(activity.getTagName());
          assert (activity!=null);

          if(activity instanceof Project){
            body = ((Project)activity).toJson(1).toString();
          }
          else{

            System.out.println("soy una tarea encontrada");
            body = ((Task)activity).toJson(1).toString();
          }
          break;

          //TODO:Chequiar esto
        }
        case "start": {
          int id = Integer.parseInt(tokens[1]);
          Component activity = findActivityById(id);
          System.out.println("mi task"+activity.getTagName());
          assert (activity!=null);
          Task task = (Task) activity;
          task.startTask();
          body = "{}";
          break;
        }
        case "stop": {
          int id = Integer.parseInt(tokens[1]);
          Component activity = findActivityById(id);
          assert (activity!=null);
          Task task = (Task) activity;
          task.stopTask();
          body = "{}";
          break;
        }

        case "add_activity:": {

          System.out.println("estoy creando actividad");
          String nameActivity = String.valueOf(Integer.parseInt(tokens[1]));
          String tag = String.valueOf(Integer.parseInt(tokens[2]));
          int typeActivity = Integer.parseInt(tokens[3]);
          String parent = String.valueOf(Integer.parseInt(tokens[4]));


          System.out.println("sss"+nameActivity);
          System.out.println(typeActivity);
          System.out.println(tag);

          //0 is project
          //1 is task


          System.out.println("creado");
          Project parentProject = new Project(parent);
          if(typeActivity == 0){

            Project activity = new Project(nameActivity,parentProject);
            activity.setTag(tag);
            parentProject.addComponent(activity);
            System.out.println("creado");
            body = activity.toJson(1).toString();

          }else{
            Task activity = new Task(nameActivity,parentProject);
            activity.setTag(tag);
            parentProject.addComponent(activity);
            body = activity.toJson(1).toString();
          }













          break;
        }

        // TODO: add new task, project
        // TODO: edit task, project properties
        default:
          assert false;
      }
      System.out.println(body);
      return body;
    }

    private String makeHeaderAnswer() {
      String answer = "";
      answer += "HTTP/1.0 200 OK\r\n";
      answer += "Content-type: application/json\r\n";
      //answer += "Access-Control-Allow-Origin: *\r\n";
      // IMPORTANT: you may need to uncomment the preceding
      // sentence in order to avoid the CORS error :
      // "Cross-Origin Request Blocked: The Same Origin
      // Policy disallows reading the remote resource..."
      answer += "\r\n";
      // blank line between headers and content, very
      // important !
      return answer;
    }
  } // SocketThread

} // WebServer