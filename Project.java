import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project extends Component{
    private List<Component> childrenProject=new ArrayList<>();
    public Project(String tagName) {
        super(tagName);
    }
    public Project(String tagName, Project parentProject){
        super(tagName,parentProject);
    }

    @Override
    void updateFinalDate(LocalDateTime newFinalDate) {
        setDateFinal(newFinalDate);
        if(this.parentProject!=null){
            parentProject.updateFinalDate(newFinalDate);
        }
    }

    void updateInitialDate(LocalDateTime InitialDate){
        if(this.initialDate==null){
            setInitialDate(InitialDate);
        }
        if(this.parentProject!=null){
            this.parentProject.updateInitialDate(initialDate);
        }

    }

    @Override
    void updateDuration(Duration newDuration, Time newTimer) {

        Printer pi = new Printer(this);

        //si duracion es 0 set newduration
        if(this.duration.getSeconds()==0){
            this.setDuration(newDuration);
            pi.visitProject(this);
        }
        //mientras sea diferente de 0 sumar la duracion anterior con el ciclo del reloj(2).
        else{
                this.duration=this.duration.plusSeconds(newTimer.getSeconds());
                this.setDuration(this.duration);
                pi.visitProject(this);
        }

        //mientras el proyecto tenga un padre actualizar las duracions.

        if(this.parentProject!=null) {
            //si el proyectoPadre es root y la duracion del proyecto actual sea 0 la duracion del proyecto root + newDuration
            if((this.getParentProject().getTagName()).equals("root") && this.duration.getSeconds()==0){
                this.parentProject.duration=this.parentProject.duration.plusSeconds(newDuration.getSeconds());
                this.setDuration(this.parentProject.duration);
            }else{

                this.parentProject.updateDuration(this.duration, newTimer);

            }

        }
    }

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
    protected void addComponent(Component component){
        childrenProject.add(component);
        component.setParentProject(this);
        component.setTagParentProject(this.tagName);

    }


}
