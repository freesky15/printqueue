package queueclients;

import documents.Document;
import queues.PrintQueueInterface;

public class Producer implements Runnable {
    private final PrintQueueInterface printQueue;
    private Document document;

    public Producer(PrintQueueInterface printQueue, Document document) {
        this.printQueue = printQueue;
        this.document = document;
    }

    @Override
    public void run() {
        printQueue.putDocumentInQueue(document);
        System.out.println(Thread.currentThread().getName() + "Document with id = " + document.getId() + " was put in queue(" + document.getStatus().toString() + ").");
        printQueue.getAllDocumentsFromQueue().stream()
                .forEach(element -> System.out.println(element.toString()));
        System.out.println();

        /*
            for more usability Thread.sleep(1000) was added here
            this section can be deleted
        */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
