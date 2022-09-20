package queues;

import documents.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrintedDocuments {
    private List<Document> documents = Collections.synchronizedList(new ArrayList<>());

    public List<Document> getDocuments() {
        return documents.stream().toList();
    }
    public void putDocumentToPrintedDocumentsList(Document printedDocument) {
        documents.add(printedDocument);
    }

}
