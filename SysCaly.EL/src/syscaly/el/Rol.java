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

    private int Id;
    private String Nombre;
    private String DescriptionRol;
    private int Top_aux;
    private ArrayList<UserSys> Usuario;

    public Rol() {
    }

    public Rol(int Id, String nombre, String DescriptionRol) {
        this.Id = Id;
        this.Nombre = nombre;
        this.DescriptionRol = DescriptionRol;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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
