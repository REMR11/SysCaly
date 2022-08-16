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
public class Matter {

    private int IdMatter;
    private int IdMarks;
    private String NameMatter;
    private String NameTeacher;
    private ArrayList<Qualification> markss;

    public Matter() {
    }

    public Matter(int IdMatter, int IdMarks, String NameMatter, String NameTeacher) {
        this.IdMatter = IdMatter;
        this.IdMarks = IdMarks;
        this.NameMatter = NameMatter;
        this.NameTeacher = NameTeacher;
    }

    public int getIdMatter() {
        return IdMatter;
    }

    public void setIdMatter(int IdMatter) {
        this.IdMatter = IdMatter;
    }

    public int getIdMarks() {
        return IdMarks;
    }

    public void setIdMarks(int IdMarks) {
        this.IdMarks = IdMarks;
    }

    public String getNameMatter() {
        return NameMatter;
    }

    public void setNameMatter(String NameMatter) {
        this.NameMatter = NameMatter;
    }

    public String getNameTeacher() {
        return NameTeacher;
    }

    public void setNameTeacher(String NameTeacher) {
        this.NameTeacher = NameTeacher;
    }

    public ArrayList<Qualification> getMarkss() {
        return markss;
    }

    public void setMarkss(ArrayList<Qualification> markss) {
        this.markss = markss;
    }
    
    
    
}
