package queues;

import documents.DocumentStatus;
import documents.Document;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PrintQueue implements PrintQueueInterface {

    private List<Document> printedDocuments = new ArrayList<>();
    private AbstractQueue<Document> printQueue = new ConcurrentLinkedQueue<Document>();

    public AbstractQueue<Document> getPrintQueue() {
        return printQueue;
    }

    public void setPrintQueue(AbstractQueue<Document> printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public List<Document> getPrintedDocuments() {
        return printedDocuments.stream().toList();
    }

    @Override
    public void putDocumentToPrintedDocumentsList(Document printedDocument) {
            this.printedDocuments.add(printedDocument);
    }

    @Override
    public void putDocumentInQueue(Document document) {
        this.printQueue.add(document);
        document.setStatus(DocumentStatus.QUEUED);
    }

    @Override
    public boolean deleteDocumentById(int id) {
        boolean deleted = false;
        for (Document document : this.printQueue) {
            if (document.getId() == id && !document.getStatus().equals(DocumentStatus.IN_PROGRESS)) {
                deleted = this.printQueue.remove(document);
            }
        }
        return deleted;
    }

    @Override
    public Document getDocument() {
        return this.printQueue.peek();
    }

    @Override
    public Document printDocument() {
        return this.printQueue.poll();
    }

    @Override
    public List<Document> getAllDocumentsFromQueue() {
        return this.printQueue.stream().toList();
    }

    @Override
    public void showAllDocuments() {
        this.printQueue.stream().forEach((element) -> System.out.println(new StringBuilder()
                .append(element.getId())
                .append(":")
                .append(element.getType())
                .append(":")
                .append(element.getDuration())
                .append(":")
                .append(element.getPaper().toString())
                .append(element.getStatus().toString())

        ));
    }
}
