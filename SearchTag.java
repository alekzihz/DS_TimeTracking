import java.util.ArrayList;
import java.util.List;

public class SearchTag implements Visitor{


  String searchTag;

  List<String> result = new ArrayList<>();

  public SearchTag(String tagSearch){
    setSearchTag(tagSearch);
  }

  public void setSearchTag(String searchTag) {
    this.searchTag = searchTag;
  }

  @Override
  public void visitTask(Task task) {

    for (String t: task.getTag()){
      if (t.toLowerCase().equals(searchTag.toLowerCase())){
        result.add(task.getTagName());
        //System.out.println("Activity: " + task.getTagName());
      }
    }
    if(task.getTagName()=="transpotation"){
      System.out.println("Tag: "+searchTag + " :"+result);
    }
  }

  @Override
  public void visitProject(Project project) {

    for (String t: project.getTag()){
      if (t.toLowerCase().equals(searchTag.toLowerCase())){
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
