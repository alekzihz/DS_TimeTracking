import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *  class Component for saving task and projects
 */
abstract class Component {
    protected LocalDateTime initialDate;
    protected LocalDateTime dateFinal;
    protected String tagName;
    protected Duration duration=Duration.ofSeconds(0);
    protected Project parentProject;

    protected List<String> tag = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger("Component");


    //protected String tagParentProject;


    public Component(String tagName){
        setTagName(tagName);
        log.info("adding component "+tagName);
        log.debug("Adding Component "+ tagName);

    }
    public Component(String tagName, Project parentProject){
        setTagName(tagName);
        setParentProject(parentProject);
        log.debug("Adding Component "+ tagName + " to " + parentProject.getTagName());
    }


    public String getTagName() {
        return tagName;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public LocalDateTime getDateFinal() {
        return dateFinal;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public void setDateFinal(LocalDateTime dateFinal) {
        this.dateFinal = dateFinal;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }
    //public void setTagParentProject(String tagParentProject){this.tagParentProject=tagParentProject;}


    abstract void updateDurationAndFinalDate(Duration newDuration, Clock newTimer, LocalDateTime finalDate);

    public void setTag(String tag) {
        this.tag.add(tag);
    }

    public List<String> getTag() {
        return tag;
    }

    protected  abstract void acceptVisitor(Visitor v);

}
