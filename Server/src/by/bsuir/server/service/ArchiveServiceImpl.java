package by.bsuir.server.service;

import by.bsuir.server.dao.ArchiveDAO;
import by.bsuir.server.dao.DAOFactory;
import by.bsuir.server.entity.Student;

import java.util.ArrayList;

public class ArchiveServiceImpl implements ArchiveService{

    private final ArchiveDAO archiveDAO;
    public ArchiveServiceImpl() {
        archiveDAO = DAOFactory.getInstance().getArchiveDAO();
    }

    public ArrayList<Student> findStudents(String tagName, String searchSurname)  {

        return archiveDAO.findStudents(tagName, searchSurname);
    }

    public boolean changeStudent(int searchId, String tagName, String newValue, boolean isAdmin) {
        return archiveDAO.changeStudent(searchId, tagName, newValue, isAdmin);
    }

    public boolean createStudent(String name, String surname, String faculty, String speciality, String group, boolean isAdmin) {
       return archiveDAO.createStudent(name, surname, faculty, speciality, group, isAdmin);
    }

    public boolean authorization(String login, String passwordHash) {
        return archiveDAO.authorization(login, passwordHash);
    }


}
