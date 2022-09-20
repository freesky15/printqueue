package documents;

public abstract class Document {
    private String type;
    private Long duration; //ms
    private PrintPaper paper;
    private int id;
    private DocumentStatus status;


    public Document(int id, String type, Long duration, PrintPaper paper) {
        this.id = id;
        this.type = type;
        this.duration = duration;
        this.paper = paper;
        this.status = DocumentStatus.NEW;
    }

    public Long getDuration() {
        return duration;
    }

    public PrintPaper getPaper() {
        return paper;
    }

    public String getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Documents{" +
                "type='" + type + '\'' +
                ", printingDuration=" + duration +
                ", paper=" + paper +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
