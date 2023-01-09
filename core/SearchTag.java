package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchTag implements Visitor {


  private String searchTag;

  private final List<Component> result = new ArrayList<>();

  private final Logger log = LoggerFactory.getLogger("SearchTag");


  public SearchTag(String tagSearch){
    setSearchTag(tagSearch);
    assert invariant();
  }


  public List<Component> getResult() {
    return result;
  }

  public void setSearchTag(String searchTag) {
    this.searchTag = searchTag;
  }
  private boolean invariant(){
    return searchTag!=null;
  }

  @Override
  public void visitTask(Task task) {

    for (String t: task.getTag()){
      if (t.equalsIgnoreCase(searchTag)){
        if(!result.contains(task)) {
          result.add(task);
        }
      }
    }
    //if(task.getId() == Component.nextId.get()){
    //break;
    //if(task.getTagName().equals("transpotation")){
    //  log.info("Tag: "+searchTag + " :"+result);

    //}
  }

  @Override
  public void visitProject(Project project) {

    for (String t: project.getTag()){
      if (t.equalsIgnoreCase(searchTag)){

        if(!result.contains(project)) {
          result.add(project);
        }

      }

      if(project.getChildrenProject()!=null){
        for(Component c: project.getChildrenProject()){
          if(c instanceof Project){
            visitProject((Project) (c));
          }
          else{
            visitTask((Task)(c));
          }
        }
      }

      //si el ultimo hijo de root es proyecto y  no tiene hijos imprimir resultado
      //si el ultimo hijo de root es proyecto y tiene hijos



    }
  }




  @Override
  public void visitInterval(Interval interval) {

  }
}
