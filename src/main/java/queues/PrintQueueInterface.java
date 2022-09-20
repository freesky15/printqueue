package queues;

import documents.Document;

import java.util.List;

public interface PrintQueueInterface {
    void putDocumentInQueue(Document document);

    boolean deleteDocumentById(int num);

    List getAllDocumentsFromQueue();

    Document printDocument();

    Document getDocument();

}
