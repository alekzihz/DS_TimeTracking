import java.util.ArrayList;
import java.util.List;

public class SearchTag implements Visitor{


  String searchTag;

  List<Component> result = new ArrayList<>();

  public SearchTag(String tagSearch){
    setSearchTag(tagSearch);
  }

  public void setSearchTag(String searchTag) {
    this.searchTag = searchTag;
  }

  @Override
  public void visitTask(Task task) {

    for (String t: task.getTag()){
      if (t==searchTag){
        result.add(task);
        System.out.println("Activity: " + task.getTagName());
      }
    }


    for (int i=0;i<result.size();i++){
     // System.out.println(result.get(i).);

    }

  }

  @Override
  public void visitProject(Project project) {

    for (String t: project.getTag()){
      if (t==searchTag){
        result.add(project);
        System.out.println("Activity: " + project.getTagName());

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
