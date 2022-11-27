/**
 * visitor interface that visit projects,tasks and intervals that provides a template printer/searchTag
 * */
public interface Visitor {
     void visitTask(Task task);
     void visitProject(Project project);
     void visitInterval(Interval interval);
}
