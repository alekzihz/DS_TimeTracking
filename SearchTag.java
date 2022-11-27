import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchTag implements Visitor {


  private String searchTag;

  private final List<String> result = new ArrayList<>();

  private final Logger log = LoggerFactory.getLogger("SearchTag");


  public SearchTag(String tagSearch){
    setSearchTag(tagSearch);
    assert invariant();
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
        result.add(task.getTagName());

      }
    }
    if(task.getTagName().equals("transpotation")){
      log.info("Tag: "+searchTag + " :"+result);

    }
  }

  @Override
  public void visitProject(Project project) {

    for (String t: project.getTag()){
      if (t.equalsIgnoreCase(searchTag)){
        result.add(project.getTagName());
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
    }
  }




  @Override
  public void visitInterval(Interval interval) {

  }
}
