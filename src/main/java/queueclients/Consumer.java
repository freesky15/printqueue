package queueclients;

import documents.DocumentStatus;
import documents.Document;
import queues.PrintQueueInterface;

import java.util.List;
import java.util.concurrent.Callable;

public class Consumer implements Callable<List<Document>> {
    private PrintQueueInterface printQueue;
    private Document document;

    public Consumer(PrintQueueInterface printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public List<Document> call() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("queueclients.Consumer was stopped now!");
                return printQueue.getPrintedDocuments();
            }
            document = this.printQueue.getDocument();
            if (document == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                document.setStatus(DocumentStatus.IN_PROGRESS);
                System.out.println("Document with id = " + document.getId() + " is printing(" + document.getStatus().toString() + "). It takes about " + document.getDuration());
                try {
                    printQueue.showAllDocuments();
                    System.out.println();
                    Thread.sleep(document.getDuration());
                    document.setStatus(DocumentStatus.DONE);
                    printQueue.putDocumentToPrintedDocumentsList(document);
                } catch (InterruptedException e) {
                    System.out.println("queueclients.Consumer was stopped!");
                    return printQueue.getPrintedDocuments();
                }
                if (document.getStatus().equals(DocumentStatus.DONE)) {
                    System.out.println("Document with id = " + document.getId() + " was printed");
                    printQueue.printDocument();
                }
            }
        }
    }
}
