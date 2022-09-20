package queues;

import documents.DocumentStatus;
import documents.Document;
import java.util.AbstractQueue;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PrintQueue implements PrintQueueInterface {
    private AbstractQueue<Document> printQueue = new ConcurrentLinkedQueue<Document>();

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

    public AbstractQueue<Document> getPrintQueue() {
        return printQueue;
    }
    public void setPrintQueue(AbstractQueue<Document> printQueue) {
        this.printQueue = printQueue;
    }

}
