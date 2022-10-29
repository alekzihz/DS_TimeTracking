public interface Visitor {
    public void visitTask(Task task);
    public void visitProject(Project project);
    public void visitInterval(Interval interval);
}
