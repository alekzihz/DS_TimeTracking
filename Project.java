import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * The class Project inherits from Component. It saves an array of Components
 * and has functions to update its attributes. It updates all of its parentProjects with new information from
 * the observable Clock.
 */
public class Project extends Component{
    private List<Component> childrenProject=new ArrayList<>();
    public Project(String tagName) {

        super(tagName);
        //assert (tagName==null): "error it must have a name";
        invariant();


    }

    public void invariant(){
        assert this.getTagName() != "": "error, project must have a name";
        assert this.getTagName() != null: "error, project must have a name";

        assert this != null: "error ";


    }
    public Project(String tagName, Project parentProject){


        super(tagName,parentProject);
    }

    /**
     * Update the initial date , its self and parent projects
     * @param InitialDate the new initial date when a child task is started
     */
    public void updateInitialDate(LocalDateTime InitialDate){
        if(this.initialDate==null){
            setInitialDate(InitialDate);
        }
        if(this.parentProject!=null){
            this.parentProject.updateInitialDate(initialDate);
        }

    }
    /**
     * Update the duration and final date, its self and parent projects
     * @param newDuration the new duration that will update
     * @param clock instance the clock
     * @param newFinalDate  the new final date for updating
     */
    @Override
    void updateDurationAndFinalDate(Duration newDuration, Clock clock, LocalDateTime newFinalDate) {

        Printer pi = new Printer(this);
        //si duracion es 0 set newduration
        setDateFinal(newFinalDate);
        if(this.duration.getSeconds()==0){

            this.setDuration(newDuration);
            pi.visitProject(this);
        }
        //mientras sea diferente de 0 sumar la duracion anterior con el ciclo del reloj(2).
        else{
                this.duration=this.duration.plusSeconds(clock.getSeconds());
                //this.setDuration(this.duration);
                pi.visitProject(this);
        }

        //mientras el proyecto tenga un padre actualizar las duracions.
        if(this.parentProject!=null) {
                this.parentProject.updateDurationAndFinalDate(this.duration, clock,newFinalDate);
            }
    }
    /**
     * @param v type visitor for visiting the Project
     */
    @Override
    protected void acceptVisitor(Visitor v) {
        v.visitProject(this);
        for(Component i :childrenProject){
            i.acceptVisitor(v);
        }
    }
    public List<Component> getChildrenProject() {
        return childrenProject;
    }
    public void setChildrenProject(List<Component> childrenProject) {
        this.childrenProject = childrenProject;
    }

    /**
     * initialize a new component and addd it to the childrenProject list
     * */
    protected void addComponent(Component component){
        childrenProject.add(component);
        component.setParentProject(this);
       //component.setTagParentProject(this.tagName);

    }


}
