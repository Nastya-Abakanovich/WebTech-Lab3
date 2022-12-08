package by.bsuir.server.dao;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();
    private final ArchiveDAO archiveDAO = new ArchiveDAOXml();

    private DAOFactory() {
    }

    public ArchiveDAO getArchiveDAO() {
        return this.archiveDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
