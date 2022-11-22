/**
 * visitor interface that visit projects,tasks and intervals that provides a template printer/searchTag
 * */
public interface Visitor {
    public void visitTask(Task task);
    public void visitProject(Project project);
    public void visitInterval(Interval interval);
}
