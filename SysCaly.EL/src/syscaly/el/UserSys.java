/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.el;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author ronal
 */
public class UserSys {
    
    private int IdUser;
    private int IdRol;
    private int IdClassroom;
    private int IdStudent;
    private int NIE;
    private String NameUser;
    private String LastName;
    private String Login;
    private String Password;
    private String ConfirmPassword;
    private byte StatusUser;
    private LocalDate DateCreateUser;
    private int Top_aux;
    private Rol rol;
    private ArrayList<Classroom> classrooms;
    private ArrayList<Student> students;
    
    public UserSys() {
    }

    public UserSys(int IdUser, int IdRol, int IdClassroom, int IdStudent, int NIE, String NameUser, String LastName, String Login, String Password, byte StatusUser, LocalDate DateCreateUser) {
        this.IdUser = IdUser;
        this.IdRol = IdRol;
        this.IdClassroom = IdClassroom;
        this.IdStudent = IdStudent;
        this.NIE = NIE;
        this.NameUser = NameUser;
        this.LastName = LastName;
        this.Login = Login;
        this.Password = Password;
        this.StatusUser = StatusUser;
        this.DateCreateUser = DateCreateUser;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int Id) {
        this.IdUser = Id;
    }

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
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

    public int getNIE() {
        return NIE;
    }

    public void setNIE(int NIE) {
        this.NIE = NIE;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String NameUser) {
        this.NameUser = NameUser;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String ConfirmPassword) {
        this.ConfirmPassword = ConfirmPassword;
    }

    public byte getStatusUser() {
        return StatusUser;
    }

    public void setStatusUser(byte StatusUser) {
        this.StatusUser = StatusUser;
    }

    public LocalDate getDateCreateUser() {
        return DateCreateUser;
    }

    public void setDateCreateUser(LocalDate DateCreateUser) {
        this.DateCreateUser = DateCreateUser;
    }

    public int getTop_aux() {
        return Top_aux;
    }

    public void setTop_aux(int Top_aux) {
        this.Top_aux = Top_aux;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(ArrayList<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public class StatusUser{
    public static final byte ACTIVO =1;
    public static final byte INACTIVO =2;    
    }
   
}
