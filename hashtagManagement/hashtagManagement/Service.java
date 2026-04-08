package hashtagManagement;


abstract class Service {
    abstract void execute() throws Exception;

    void log() {
        System.out.println("Service running...");
    }
}