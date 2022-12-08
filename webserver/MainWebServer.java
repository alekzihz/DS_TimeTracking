package webserver;
import core.Project;
import core.Clock;

import java.awt.*;

import static core.Main.makeTreeCourses;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Project root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock

    new WebServer(root);
  }
}