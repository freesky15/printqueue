package comparators;

import documents.Document;

import java.util.Comparator;

public class CompareDocumentsByPaperSize implements Comparator<Document> {
    @Override
    public int compare(Document o1, Document o2) {
        return (o1.getPaper().getHeight() * o1.getPaper().getWidth()) -
                (o2.getPaper().getHeight() * o2.getPaper().getWidth());
    }
}
