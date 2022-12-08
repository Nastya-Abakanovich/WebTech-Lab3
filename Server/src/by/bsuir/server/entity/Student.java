package by.bsuir.server.entity;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String faculty;
    private String speciality;
    private String group;

    public Student(int id, String name, String surname, String faculty, String speciality, String group) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
        this.speciality = speciality;
        this.group = group;
    }

    public Student() {
        this.id = 0;
        this.name = "";
        this.surname = "";
        this.faculty = "";
        this.speciality = "";
        this.group = "";
    }

    public Student(Student student) {
        this.id = student.id;
        this.name = student.name;
        this.surname = student.surname;
        this.faculty = student.faculty;
        this.speciality = student.speciality;
        this.group = student.group;
    }

    public Student(Node student) {
        NodeList nodes = student.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            String field = nodes.item(i).getNodeName();
            String value = nodes.item(i).getTextContent();

            switch (field) {
                case "id" -> id = Integer.parseInt(value);
                case "name" -> name = value;
                case "surname" -> surname = value;
                case "faculty" -> faculty = value;
                case "speciality" -> speciality = value;
                case "group" -> group = value;
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", faculty='" + faculty + '\'' +
                ", speciality='" + speciality + '\'' +
                ", group='" + group + '\'' +
                '}';
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getGroup() {
        return group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean setValue(String tagName, String value)
    {
        boolean result = true;
        switch (tagName) {
            case "name" -> name = value;
            case "surname" -> surname = value;
            case "faculty" -> faculty = value;
            case "speciality" -> speciality = value;
            case "group" -> group = value;
            default -> result = false;
        }

        return result;
    }
}
