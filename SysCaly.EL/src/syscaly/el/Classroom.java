/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.el;

import java.util.ArrayList;

/**
 *
 * @author ronal
 */
public class Classroom {
    private int IdClassroom;
    private int IdStudent;
    private int IdQualification;
    private int NumberOfStudent;
    private String Section;
    private ArrayList<Student> students;
    private ArrayList<Matter> matters;

    public Classroom() {
    }

    public Classroom(int IdClassroom, int IdStudent, int IdQualification, int NumberOfStudent, String Section) {
        this.IdClassroom = IdClassroom;
        this.IdStudent = IdStudent;
        this.IdQualification = IdQualification;
        this.NumberOfStudent = NumberOfStudent;
        this.Section = Section;
    }

    public int getIdClassroom() {
        return IdClassroom;
    }

    public void setIdClassroom(int IdClassroom) {
        this.IdClassroom = IdClassroom;
    }

    public int getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(int IdStudent) {
        this.IdStudent = IdStudent;
    }

    public int getIdQualification() {
        return IdQualification;
    }

    public void setIdQualification(int IdQualification) {
        this.IdQualification = IdQualification;
    }

    public int getNumberOfStudent() {
        return NumberOfStudent;
    }

    public void setNumberOfStudent(int NumberOfStudent) {
        this.NumberOfStudent = NumberOfStudent;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String Section) {
        this.Section = Section;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Matter> getMatters() {
        return matters;
    }

    public void setMatters(ArrayList<Matter> matters) {
        this.matters = matters;
    }
    
    
    
    
}
