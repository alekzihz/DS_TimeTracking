package core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *  class Component for saving task and projects
 */
public abstract class Component {
    protected LocalDateTime initialDate;
    protected LocalDateTime dateFinal;
    protected String tagName;
    protected Duration duration = Duration.ofSeconds(0);
    protected Project parentProject;

    protected final List<String> tag = new ArrayList<>();

    protected int id;
    private final Logger log = LoggerFactory.getLogger("Component");
    protected static AtomicInteger nextId = new AtomicInteger(-1);


    //protected String tagParentProject;


    public static AtomicInteger getNextId() {
        return nextId;
    }

    public Component(String tagName) {
        setTagName(tagName);
        log.info("adding component " + tagName);
        log.debug("Adding Component " + tagName);


    }

    public Component(String tagName, Project parentProject) {
        setTagName(tagName);
        setParentProject(parentProject);
        log.debug("Adding Component " + tagName + " to " + parentProject.getTagName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    abstract void updateDurationAndFinalDate(Duration newDuration, Clock newTimer, LocalDateTime finalDate);

    public void setTag(String tag) {
        this.tag.add(tag);
    }

    public List<String> getTag() {
        return tag;
    }

    protected abstract void acceptVisitor(Visitor v);

    public abstract Component findActivityById(int id);

    protected static final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    protected void toJson(JSONObject json) {

        if (this.getTag().size()==0){
            this.setTag("none");
        }
        if (this.getTagName()=="root"){
            this.tag.clear();
        }
        String tag  = this.getTag().toString();

        json.put("id", id);
        json.put("name", tagName);
        json.put("initialDate", initialDate==null
            ? JSONObject.NULL : formatter.format(initialDate));
        json.put("finalDate", dateFinal==null
            ? JSONObject.NULL : formatter.format(dateFinal));
        json.put("duration", duration.toSeconds());
        json.put("tag", tag);

    }
}


