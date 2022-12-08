package by.bsuir.server.service;

import by.bsuir.server.entity.Student;

import java.util.ArrayList;

public interface ArchiveService {

    boolean authorization(String login, String passwordHash);
    ArrayList<Student> findStudents(String tagName, String searchValue);
    boolean changeStudent(int searchId, String tagName, String newValue, boolean isAdmin);
    boolean createStudent(String name, String surname, String faculty, String speciality, String group, boolean isAdmin);

}
