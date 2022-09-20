package comparators;

import documents.Document;

import java.util.Comparator;

public class CompareDocumentsByDuration implements Comparator<Document> {
    @Override
    public int compare(Document o1, Document o2) {
        return (int) (o1.getDuration() - o2.getDuration());
    }
}
