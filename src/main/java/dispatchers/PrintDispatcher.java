package dispatchers;

import comparators.CompareDocumentsByDocumentType;
import comparators.CompareDocumentsByDuration;
import comparators.CompareDocumentsByPaperSize;
import documents.Document;
import queueclients.Consumer;
import queues.PrintQueueInterface;

import java.util.List;
import java.util.concurrent.*;

public class PrintDispatcher {
    private final int AWAITE_TIME = 5;
    //get count of processors to set threat count
    private final int THREAT_COUNT = Runtime.getRuntime().availableProcessors();
    private ExecutorService executorServiceProducer = Executors.newFixedThreadPool(THREAT_COUNT);
    ;
    private ExecutorService executorServiceConsumer;
    Future<List<Document>> executorServiceConsumerResults;
    private PrintQueueInterface printQueue;

    public PrintDispatcher(PrintQueueInterface printQueue) {
        this.printQueue = printQueue;
    }

    public void setExecutorServiceConsumer(Consumer consumer) {
        executorServiceConsumer = Executors.newSingleThreadExecutor();
        executorServiceConsumerResults = this.executorServiceConsumer.submit(consumer);
    }

    public PrintQueueInterface getPrintQueue() {
        return printQueue;
    }

    public List<Document> stop() throws InterruptedException, ExecutionException {
        this.executorServiceConsumer.shutdownNow();
        this.executorServiceProducer.shutdown();
        this.executorServiceProducer.awaitTermination(AWAITE_TIME, TimeUnit.SECONDS);
        return printQueue.getAllDocumentsFromQueue()
                .stream()
                .toList();
    }

    public List<Document> getQueuedDocuments() {
        return printQueue.getAllDocumentsFromQueue();
    }

    public void putProducerToThreatPool(Runnable threat) {
        executorServiceProducer.execute(threat);
    }

    public void putConsumerToPrintQueue(Callable<List<Document>> threat) {
        executorServiceProducer.submit(threat);
    }

    public Double getAverageDuration() throws ExecutionException, InterruptedException {
        return executorServiceConsumerResults.get()
                .stream()
                .mapToDouble(element -> element.getDuration())
                .average()
                .getAsDouble();
    }

    public List<Document> getPrintedDocuments() throws ExecutionException, InterruptedException {
        return executorServiceConsumerResults.get()
                .stream()
                .toList();
    }

    public void deleteDocument(int number) {
        printQueue.deleteDocumentById(number);
    }

    public List<Document> sortByPrintingOrder() throws ExecutionException, InterruptedException {
        return executorServiceConsumerResults.get()
                .stream()
                .toList();
    }

    public List<Document> sortByDocumentType() throws ExecutionException, InterruptedException {
        return executorServiceConsumerResults.get()
                .stream()
                .sorted(new CompareDocumentsByDocumentType())
                .toList();
    }

    public List<Document> sortByPaperSize() throws ExecutionException, InterruptedException {
        return executorServiceConsumerResults.get()
                .stream()
                .sorted(new CompareDocumentsByPaperSize())
                .toList();
    }

    public List<Document> sortByPrintingDuration() throws ExecutionException, InterruptedException {
        return executorServiceConsumerResults.get()
                .stream()
                .sorted(new CompareDocumentsByDuration())
                .toList();
    }

}