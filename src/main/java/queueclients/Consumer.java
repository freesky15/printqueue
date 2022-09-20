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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                document = this.printQueue.getDocument();
                if (document == null) {
                    Thread.sleep(1000);
                } else {
                    document.setStatus(DocumentStatus.IN_PROGRESS);
                    System.out.println("Document with id = " + document.getId() + " is printing(" + document.getStatus().toString() + "). It takes about " + document.getDuration());

                    //printQueue.showAllDocuments();
                    printQueue.getAllDocumentsFromQueue().stream()
                            .forEach(element -> System.out.println(element.toString()));
                    System.out.println();

                    Thread.sleep(document.getDuration());
                    document.setStatus(DocumentStatus.DONE);
                    printQueue.putDocumentToPrintedDocumentsList(document);
                    if (document.getStatus().equals(DocumentStatus.DONE)) {
                        System.out.println("Document with id = " + document.getId() + " was printed");
                        printQueue.printDocument();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer was stopped!");
                return printQueue.getPrintedDocuments();
            }
        }
        return printQueue.getPrintedDocuments();
    }
}
