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
public class Rol {

    private int IdRol;
    private String NameRol;
    private String DescriptionRol;
    private int Top_aux;
    private ArrayList<UserSys> Usuario;

    public Rol() {
    }

    public Rol(int IdRol, String NameRol, String DescriptionRol) {
        this.IdRol = IdRol;
        this.NameRol = NameRol;
        this.DescriptionRol = DescriptionRol;
    }

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int Id) {
        this.IdRol = Id;
    }

    public String getNameRol() {
        return NameRol;
    }

    public void setNameRol(String NameRol) {
        this.NameRol = NameRol;
    }

    public String getDescriptionRol() {
        return DescriptionRol;
    }

    public void setDescriptionRol(String DescriptionRol) {
        this.DescriptionRol = DescriptionRol;
    }

    public int getTop_aux() {
        return Top_aux;
    }

    public void setTop_aux(int Top_aux) {
        this.Top_aux = Top_aux;
    }

    public ArrayList<UserSys> getUsuario() {
        return Usuario;
    }

    public void setUsuario(ArrayList<UserSys> Usuario) {
        this.Usuario = Usuario;
    }

}
