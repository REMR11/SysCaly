/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.util.*; // Utilizar la utileria de java https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html
import java.sql.*;
import syscaly.el.*;

/**
 *
 * @author ronal
 */
public class RolDAL {
    
    static String obtenerCampos() {
        return "r.IdRol, r.NameRol, r.DescriptionRol";
    }

    private static String obtenerSelect(Rol pRol) {
        String sql;
        sql = "SELECT ";
        if (pRol.getTop_aux() > 0 && DBContext.TIPODB == DBContext.TipoDB.SQLSERVER) {
            sql += "TOP " + pRol.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Rol r"); 
        return sql;
    }

    private static String agregarOrderBy(Rol pRol) {
        String sql = " ORDER BY r.Id DESC";
        if (pRol.getTop_aux() > 0 && DBContext.TIPODB == DBContext.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Rol en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pRol.getTop_aux() + " ";
        }
        return sql;
    }

    public static int crear(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = DBContext.obtenerConexion();) { 
            sql = "INSERT INTO Rol(NameRol, DescriptionRol) VALUES(?,?)"; 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { 
                ps.setString(1, pRol.getNameRol()); 
                ps.setString(2, pRol.getDescriptionRol());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
                
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex; 
        }
        return result;
        
    }


    
    public static int modificar(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = DBContext.obtenerConexion();) { 
            sql = "UPDATE Rol SET NameRol=? DescriptionRol=? WHERE IdRol=?"; 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { 
                ps.setString(1, pRol.getNameRol());
                ps.setString(2,pRol.getDescriptionRol());
                ps.setInt(3, pRol.getIdRol());  
                result = ps.executeUpdate(); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }


    public static int eliminar(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = DBContext.obtenerConexion();) {
            sql = "DELETE FROM Rol WHERE IdRol=?"; 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pRol.getIdRol());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex; 
        }
        return result;
    }    
    

    static int asignarDatosResultSet(Rol pRol, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pRol.setIdRol(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pRol.setNameRol(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pRol.setDescriptionRol(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Rol> pRoles) throws Exception {
        try (ResultSet resultSet = DBContext.obtenerResultSet(pPS);) { 
            while (resultSet.next()) { 
                Rol rol = new Rol(); 
                asignarDatosResultSet(rol, resultSet, 0);
                pRoles.add(rol); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Rol obtenerPorId(Rol pRol) throws Exception {
        Rol rol = new Rol();
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = DBContext.obtenerConexion();) { 
            String sql = obtenerSelect(pRol); 
            sql += " WHERE r.IdRol=?"; 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { 
                ps.setInt(1, pRol.getIdRol());  
                obtenerDatos(ps, roles); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close();  
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (roles.size() > 0) { // Verificar si el ArrayList de Rol trae mas de un registro en tal caso solo debe de traer uno
            rol = roles.get(0); // Si el ArrayList de Rol trae un registro o mas obtenemos solo el primero 
        }
        return rol; // Devolver el rol encontrado por Id 
    }

    
    public static ArrayList<Rol> obtenerTodos() throws Exception {
        ArrayList<Rol> roles;
        roles = new ArrayList<>();
        try (Connection conn = DBContext.obtenerConexion();) {
            String sql = obtenerSelect(new Rol());  
            sql += agregarOrderBy(new Rol());  
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { 
                obtenerDatos(ps, roles); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return roles; 
    }
    
    static void querySelect(Rol pRol, DBContext.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); 
        if (pRol.getIdRol() > 0) { 
            pUtilQuery.AgregarWhereAnd(" r.IdRol=? "); 
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pRol.getIdRol()); 
            }
        }
        if (pRol.getNameRol() != null && pRol.getNameRol().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" r.NameRol LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pRol.getNameRol() + "%"); 
            }
        }
    }

    public static ArrayList<Rol> buscar(Rol pRol) throws Exception {
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = DBContext.obtenerConexion();) { 
            String sql = obtenerSelect(pRol); 
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pRol, utilQuery); 
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pRol); 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pRol, utilQuery);  
                obtenerDatos(ps, roles); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return roles; 
    }
}
