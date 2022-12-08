package by.bsuir.server.dao;

import by.bsuir.server.entity.Student;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArchiveDAOXml implements ArchiveDAO{

    private final ReentrantReadWriteLock studentsLock = new ReentrantReadWriteLock(true);
    private final ReentrantReadWriteLock administratorsLock = new ReentrantReadWriteLock(true);
    private static final String XML_STUDENTS_PATH = "Server/src/by/bsuir/server/resources/students.xml";
    private static final String XML_ADMINISTRATORS_PATH = "Server/src/by/bsuir/server/resources/administrators.xml";

    private Document xmlDocument;

    public ArchiveDAOXml() {
    }

    public ArrayList<Student> findStudents(String tagName, String searchValue) {

        this.studentsLock.readLock().lock();
        xmlDocument = getDocument(XML_STUDENTS_PATH);
        this.studentsLock.readLock().unlock();

        ArrayList<Student> findStudents = new ArrayList<>();
        NodeList data = xmlDocument.getDocumentElement().getElementsByTagName(tagName);
        Node student;

        for (int i = 0; i < data.getLength(); i++) {
            if (data.item(i).getFirstChild().getNodeValue().equals(searchValue)) {

                student = data.item(i).getParentNode();

                findStudents.add(new Student(student));
            }
        }
        return findStudents;
    }

    public boolean changeStudent(int searchId, String tagName, String newValue, boolean isAdmin) {

        if (isAdmin) {
            this.studentsLock.readLock().lock();
            xmlDocument = getDocument(XML_STUDENTS_PATH);
            this.studentsLock.readLock().unlock();

            NodeList data = xmlDocument.getDocumentElement().getElementsByTagName("id");

            for (int i = 0; i < data.getLength(); i++) {

                if (Integer.parseInt(data.item(i).getFirstChild().getNodeValue()) == searchId) {

                    NodeList studentNodes = data.item(i).getParentNode().getChildNodes();

                    for (int temp = 0; temp < studentNodes.getLength(); temp++) {
                        Node node = studentNodes.item(temp);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) node;
                            if (tagName.equals(eElement.getNodeName())) {
                                eElement.setTextContent(newValue);
                                setStudentsDocument();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean createStudent(String name, String surname, String faculty, String speciality, String group, boolean isAdmin) {
        if (isAdmin) {
            this.studentsLock.readLock().lock();
            xmlDocument = getDocument(XML_STUDENTS_PATH);
            this.studentsLock.readLock().unlock();

            NodeList data = xmlDocument.getDocumentElement().getElementsByTagName("id");
            int maxId = 0;
            int value;

            for (int i = 0; i < data.getLength(); i++) {
                value = Integer.parseInt(data.item(i).getFirstChild().getNodeValue());
                if (maxId < value)
                    maxId = value;
            }

            Element students = xmlDocument.getDocumentElement();
            Element student = xmlDocument.createElement("student");
            students.appendChild(student);

            Element eId = xmlDocument.createElement("id");
            eId.appendChild(xmlDocument.createTextNode(Integer.toString(maxId + 1)));
            student.appendChild(eId);

            Element eName = xmlDocument.createElement("name");
            eName.appendChild(xmlDocument.createTextNode(name));
            student.appendChild(eName);

            Element eSurname = xmlDocument.createElement("surname");
            eSurname.appendChild(xmlDocument.createTextNode(surname));
            student.appendChild(eSurname);

            Element eFaculty = xmlDocument.createElement("faculty");
            eFaculty.appendChild(xmlDocument.createTextNode(faculty));
            student.appendChild(eFaculty);

            Element eSpeciality = xmlDocument.createElement("speciality");
            eSpeciality.appendChild(xmlDocument.createTextNode(speciality));
            student.appendChild(eSpeciality);

            Element eGroup = xmlDocument.createElement("group");
            eGroup.appendChild(xmlDocument.createTextNode(group));
            student.appendChild(eGroup);
            setStudentsDocument();

            return true;
        }
        return false;
    }

    public boolean authorization(String login, String passwordHash) {
        this.administratorsLock.readLock().lock();
        Document adminDocument = getDocument(XML_ADMINISTRATORS_PATH);
        this.administratorsLock.readLock().unlock();

        NodeList data = adminDocument.getDocumentElement().getElementsByTagName("login");

        for (int i = 0; i < data.getLength(); i++) {
            if (data.item(i).getFirstChild().getNodeValue().equals(login) ) {

                NodeList nodes = data.item(i).getParentNode().getChildNodes();
                for (int j = 0; j < nodes.getLength(); j++) {
                    String field = nodes.item(j).getNodeName();
                    String value = nodes.item(j).getTextContent();

                    if (field.equals("passwordHash") && value.equals(passwordHash)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Document getDocument(String filePath) {
        Document document = null;
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(filePath);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
        return document;
    }

    private void setStudentsDocument() {
        this.studentsLock.writeLock().lock();
        try {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(xmlDocument);
        StreamResult streamResult = new StreamResult(new File(XML_STUDENTS_PATH));
            transformer.transform(domSource, streamResult);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } finally {
            this.studentsLock.writeLock().unlock();
        }
    }
}
