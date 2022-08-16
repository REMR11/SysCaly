/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.util.*;
import java.sql.*;
import syscaly.el.*;

/**
 *
 * @author ronal
 */
public class ClassroomDAL {

    static String ObtenerCampos() {
        return "c.IdClassroom, c.IdStudent, c.IdMatter, c.NumberOfStudent, c.Section ";
    }

    private static String ObtenerSelect(Classroom pClassroom) {
        String sql;
        sql = "SELECT " + ObtenerCampos() + "FROM Classroom c ";

        return sql;
    }

    private static String AgregarOrderBy(Classroom pClassroom) {
        String sql = "ORDER BY c.IdClassroom DESC ";

        return sql;
    }

    private static boolean ExistsClassroom(Classroom pClassroom) throws SQLException {
        boolean existsC = false;
        ArrayList<Classroom> Classrooms = new ArrayList<Classroom>();
        try (Connection conn = DBContext.obtenerConexion();) {
            
            String sql = ObtenerSelect(pClassroom);
            sql += " WHERE c.IdClassroom<>? ";
            try {
                PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);
                ps.setInt(1, pClassroom.getIdClassroom());
                ObtenerDatos(ps, Classrooms);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (Classrooms.size()>0) {
            Classroom classroom;
            
            classroom = Classrooms.get(0);
            if (classroom.getIdClassroom()>0) {
                existsC = true;
            }
        }
        return existsC;
    }

    public static int Crear(Classroom pClassroom) throws Exception {
        int result;
        String sql;
        boolean existsC = ExistsClassroom(pClassroom);
        if (existsC == false) {
            try (Connection conn = DBContext.obtenerConexion();){
                
                sql = "INSERT INTO Classroom(IdStudent, IdMatter, NumberOfstudent, Section) VALUES(?,?,?,?)";
                try(PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pClassroom.getIdStudent());
                    ps.setInt(2, pClassroom.getIdMatter());
                    ps.setInt(3, pClassroom.getNumberOfStudent());
                    ps.setString(4, pClassroom.getSection());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } catch (SQLException ex) {
                throw ex;
            }
        }else{
        result = 0;
        throw new RuntimeException("Grado existente");
        }
        return result;
    }

    
    public static int modificar(Classroom pClassroom) throws Exception{
    
        int result;
        String sql;
        boolean existsC = ExistsClassroom(pClassroom);
        
        if (existsC == false) {
            try (Connection conn = DBContext.obtenerConexion();){
                sql = "UPDATE  Classroom SET IdStudent, IdMatter, NumberOfstudent, Section WHERE IdClassroom=?";
                try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);){
                    ps.setInt(1, pClassroom.getIdStudent());
                    ps.setInt(2, pClassroom.getIdMatter());
                    ps.setInt(3, pClassroom.getNumberOfStudent());
                    ps.setString(4,pClassroom.getSection());
                    result = ps.executeUpdate();
                    ps.close();
                    
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } catch (SQLException ex) {
                throw ex;
            }            
        }
        else{
        result = 0;
        throw new RuntimeException("Modificacion exitosa");
        }
        return result;
    }
    
    public static int Eliminar(Classroom pClassroom)throws Exception{
    int result;
    String sql;
        try (Connection conn = DBContext.obtenerConexion();){
            sql = "DELETE FROM Classroom WHERE Id=?";
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);){
                ps.setInt(1, pClassroom.getIdClassroom());
                result = ps.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    static int AsignarDatosResultSet;

    private static void ObtenerDatos(PreparedStatement ps, ArrayList<Classroom> Classrooms) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
