/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.el;

/**
 *
 * @author ronal
 */
public class Student {
    private int IdStudent;
    private int NIE;
    private String Name;
    private String LastName;
    private String Turn;

    public Student() {
    }

    public Student(int IdStudent, int NIE, String Name, String LastName, String Turn) {
        this.IdStudent = IdStudent;
        this.NIE = NIE;
        this.Name = Name;
        this.LastName = LastName;
        this.Turn = Turn;
    }

    public int getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(int IdStudent) {
        this.IdStudent = IdStudent;
    }

    public int getNIE() {
        return NIE;
    }

    public void setNIE(int NIE) {
        this.NIE = NIE;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getTurn() {
        return Turn;
    }

    public void setTurn(String Turn) {
        this.Turn = Turn;
    }
    
}