package by.bsuir.client.dao;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();
    private final ServerDAO serverDAO = new ServerDAONetwork();

    private DAOFactory() {
    }

    public ServerDAO getServerDAO() {
        return this.serverDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
