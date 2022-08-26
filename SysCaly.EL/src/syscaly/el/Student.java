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
    private String NameStudent;
    private String LastName;
    private String Turn;
     private int Top_aux;

    public Student() {
    }

    public Student(int IdStudent, int NIE, String NameStudent, String LastName, String Turn) {
        this.IdStudent = IdStudent;
        this.NIE = NIE;
        this.NameStudent = NameStudent;
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

    public String getNameStudent() {
        return NameStudent;
    }

    public void setNameStudent(String NameStudent) {
        this.NameStudent = NameStudent;
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
    public int getTop_aux() {
        return Top_aux;
    }

    public void setTop_aux(int Top_aux) {
        this.Top_aux = Top_aux;
    }
}
