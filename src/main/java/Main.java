import dispatchers.PrintDispatcher;
import documents.*;

import queueclients.Consumer;
import queueclients.Producer;
import queues.PrintQueue;
import queues.PrintQueueInterface;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        //Creating queue and printdispatcher
        PrintQueueInterface printQueue = new PrintQueue();
        PrintDispatcher printDispatcher = new PrintDispatcher(printQueue);

        //Creating producers
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new PdfDocument(1, "pdf1", 1000L, new PrintPaper("A4", 200, 400))));
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new XlsDocument(2, "xls", 1000L, new PrintPaper("A3", 300, 600))));
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new PlaintextDocument(3, "txt", 4000L, new PrintPaper("A1", 500, 1000))));
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new WordDocument(4, "wrd", 2000L, new PrintPaper("A2", 400, 800))));

        //Creating consumer
        printDispatcher.setExecutorServiceConsumer(new Consumer(printQueue));

        //Creating more producers
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new PdfDocument(5, "pdf1", 4000L, new PrintPaper("A4", 200, 400))));
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new PdfDocument(6, "pdf1", 6000L, new PrintPaper("A4", 200, 400))));
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new PdfDocument(1, "pdf1", 1000L, new PrintPaper("A3", 300, 600))));
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new XlsDocument(8, "xls", 2000L, new PrintPaper("A4", 200, 400))));
        printDispatcher.putProducerToThreatPool(new Producer(printQueue, new PlaintextDocument(9, "txt", 3000L, new PrintPaper("A5", 100, 200))));

        try {
            //To get more printed documents
            Thread.sleep(10000);

            System.out.println("QUEUE: ");
            printDispatcher.stop().stream().forEach(element -> System.out.println(element.toString()));

            System.out.println("PRINTED DOCUMENTS: ");
            printDispatcher.getPrintedDocuments().forEach(element -> System.out.println(element.toString()));

            System.out.println("AVERAGE: " + printDispatcher.getAverageDuration());

            System.out.println("SORTED BY DURATION PRINTED DOCUMENTS: ");
            printDispatcher.sortByPrintingDuration().stream().forEach(element -> System.out.println(element.toString()));

            System.out.println("SORTED BY PAPER SIZE PRINTED DOCUMENTS: ");
            printDispatcher.sortByPaperSize().stream().forEach(element -> System.out.println(element.toString()));

            System.out.println("SORTED BY DOCUMENT TYPE PRINTED DOCUMENTS: ");
            printDispatcher.sortByDocumentType().stream().forEach(element -> System.out.println(element.toString()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
