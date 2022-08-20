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
    private String NameMatter;
    private String NameTeacher;
    
    public Matter() {
    }

    public Matter(int IdMatter, String NameMatter, String NameTeacher) {
        this.IdMatter = IdMatter;
        this.NameMatter = NameMatter;
        this.NameTeacher = NameTeacher;
    }

    public int getIdMatter() {
        return IdMatter;
    }

    public void setIdMatter(int IdMatter) {
        this.IdMatter = IdMatter;
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

    
    
    
}
