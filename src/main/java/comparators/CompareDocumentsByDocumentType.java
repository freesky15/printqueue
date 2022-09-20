package comparators;

import documents.Document;

import java.util.Comparator;

public class CompareDocumentsByDocumentType implements Comparator<Document> {

    @Override
    public int compare(Document o1, Document o2) {
        return o1.getType().compareTo(o2.getType());
    }
}
