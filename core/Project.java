package core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final Logger log = LoggerFactory.getLogger("Project");

    public Project(String tagName) {


        super(tagName);

        id = nextId.incrementAndGet();
        //assert (tagName==null): "error it must have a name";
        assert invariant();
        log.info("Adding Project "+ tagName);


    }

    public Project(String tagName, Project parentProject){


        super(tagName,parentProject);
        id = nextId.incrementAndGet();
        assert invariant();
        log.info("Adding Project "+ tagName);
        log.debug("Adding Project "+ tagName+ " to " + "Project "+parentProject.getTagName());


    }

    /**
     * Update the initial date , its self and parent projects
     * @param InitialDate the new initial date when a child task is started
     */
    public void updateInitialDate(LocalDateTime InitialDate){
        assert invariant();
        if(this.initialDate==null){
            setInitialDate(InitialDate);
        }
        if(this.parentProject!=null){
            this.parentProject.updateInitialDate(initialDate);
        }
        assert invariant();

    }
    /**
     * Update the duration and final date, its self and parent projects
     * @param newDuration the new duration that will update
     * @param clock instance the clock
     * @param newFinalDate  the new final date for updating
     */
    @Override
    void updateDurationAndFinalDate(Duration newDuration, Clock clock, LocalDateTime newFinalDate) {


        assert clock!=null || newDuration!=null || newFinalDate!=null: "error updating datas";
        assert invariant();

        Printer pi = new Printer(this);
        //si duracion es 0 set newduration
        setDateFinal(newFinalDate);
        if(this.duration.getSeconds()==0){

            this.setDuration(newDuration);
            pi.visitProject(this);
        }
        //mientras sea diferente de 0 sumar la duracion anterior con el ciclo del reloj(2).
        else{
            assert clock != null;
            this.duration=this.duration.plusSeconds(clock.getSeconds());
                //this.setDuration(this.duration);
                pi.visitProject(this);
        }

        //mientras el proyecto tenga un padre actualizar las duracions.
        if(this.parentProject!=null) {
                this.parentProject.updateDurationAndFinalDate(this.duration, clock,newFinalDate);
            }

        assert this!=null:"error, updating project";
        //log.warn("it could not update this project");
        assert invariant();

    }
    /**
     * @param v type visitor for visiting the Project
     */
    @Override
    protected void acceptVisitor(Visitor v) {

        assert v !=null: "error, visit project cannot be null";
        assert invariant();
        v.visitProject(this);
        for(Component i :childrenProject){
            i.acceptVisitor(v);
        }

        assert this !=null: "error visiting project";
        assert invariant();

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
    public void addComponent(Component component){
        assert invariant();
        childrenProject.add(component);
        component.setParentProject(this);
        assert invariant();
       //component.setTagParentProject(this.tagName);

    }

    private boolean invariant(){
        if(this.getTagName().equals("") || this.getTagName().equals(null) || this==null){
            log.error("error, project must have a name");
            return false;
        }
        return true;

    }

    public JSONObject toJson(int depth) {
        JSONObject json = new JSONObject();
        json.put("class", "project");
        super.toJson(json);
        if (depth>0) {
            JSONArray jsonActivities = new JSONArray();
            for (Component activity : childrenProject) {
                if(activity instanceof Task){
                    jsonActivities.put(((Task)activity).toJson(depth - 1));
                }else {
                    jsonActivities.put(((Project)activity).toJson(depth - 1));
                }
                // important: decrement depth
            }
            json.put("activities", jsonActivities);
        }
        return json;
    }

    @Override
    public Component findActivityById(int id) {
        final List<Component> components = this.getChildrenProject();

        if(id == this.getId()){
            return this;
        }else{
            for(Component i : components){
                Component c= i.findActivityById(id);


                if(c !=null){
                    return c;
                }
            }
        }
        return null;
    }


}
