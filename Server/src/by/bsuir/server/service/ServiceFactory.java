package by.bsuir.server.service;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final ArchiveService archiveService = new ArchiveServiceImpl();

    private ServiceFactory() {
    }

    public ArchiveService getArchiveService() {
        return this.archiveService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
