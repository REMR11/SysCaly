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
public class Qualification {

    private int IdQualification;
    private int IdMatter;
    private int IdStudent;
    private int Period;
    private int Cycle;
    private int Qualification;
    private ArrayList<Student> students;
    private ArrayList<Matter> Matters;

    public ArrayList<Matter> getMatters() {
        return Matters;
    }

    public void setIdQualification(int IdMarks) {
        this.IdQualification = IdMarks;
    }

    public int getIdMatter() {
        return IdMatter;
    }

    public void setIdMatter(int IdMatter) {
        this.IdMatter = IdMatter;
    }

    public int getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(int IdStudent) {
        this.IdStudent = IdStudent;
    }

    public int getPeriod() {
        return Period;
    }

    public void setPeriod(int Period) {
        this.Period = Period;
    }

    public int getCycle() {
        return Cycle;
    }

    public void setCycle(int Cycle) {
        this.Cycle = Cycle;
    }

    public int getQualification() {
        return Qualification;
    }

    public void setQualification(int Qualification) {
        this.Qualification = Qualification;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setMatters(ArrayList<Matter> Matters) {
        this.Matters = Matters;
    }

    public int getIdQualification() {
        return IdQualification;
    }

    public Qualification() {
    }

    public Qualification(int IdQualification, int IdStudent, int Period, int Cycle, int Qualification) {
        this.IdQualification = IdQualification;
        this.IdStudent = IdStudent;
        this.Period = Period;
        this.Cycle = Cycle;
        this.Qualification = Qualification;
    }

}
