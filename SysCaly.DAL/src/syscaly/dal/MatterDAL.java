/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.util.*;
import java.sql.*;
import syscaly.el.*;
import java.util.HashMap;
import java.sql.ResultSet;
/**
 *
 * @author ronal
 */
public class MatterDAL {

    static String obtenerCampos() {
        return " m.NameMtter, m.NameTeacher ";
    }

    private static String obtenerSelect(Matter pMatter) {
        return "SELECT " + obtenerCampos() + " FROM Matter m"; 
    }

    // Metodo para obtener Order by a la consulta SELECT de la tabla Matter y ordene los registros de mayor a menor 
    private static String agregarOrderBy(Matter pMatter) {
        return " ORDER BY m.IdMatter DESC";
    }
    // Metodo para poder insertar un nuevo registro en la tabla de Matter
    public static int crear(Matter pMatter) throws Exception {
        int result;
        String sql;
        try (Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            sql = "INSERT INTO Matter(Nombre) VALUES(?)"; // Definir la consulta INSERT a la tabla de Matter utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                ps.setString(1, pMatter.getNameMatter()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                result = ps.executeUpdate(); // Ejecutar la consulta INSERT en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }

    // Metodo para poder actualizar un registro en la tabla de Matter
    public static int modificar(Matter pMatter) throws Exception {
        int result;
        String sql;
        try (Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            sql = "UPDATE Matter SET Nombre=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Matter utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                ps.setString(1, pMatter.getNameMatter()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                ps.setInt(2, pMatter.getIdMatter()); // Agregar el parametro a la consulta donde estan el simbolo ? #2  
                result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }

    // Metodo para poder eliminar un registro en la tabla de Matter
    public static int eliminar(Matter pMatter) throws Exception {
        int result;
        String sql;
        try (Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            sql = "DELETE FROM Matter WHERE Id=?";  // Definir la consulta DELETE a la tabla de Matter utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pMatter.getIdMatter()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate();  // Ejecutar la consulta DELETE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el DELETE en la base de datos 
    }    
    
    // Metodo para llenar las propiedades de la entidad de Matter con los datos que viene en el ResultSet,
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int asignarDatosResultSet(Matter pMatter, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Matter
        pIndex++;
        pMatter.setIdMatter(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pMatter.setNameMatter(pResultSet.getString(pIndex)); // index 2
        return pIndex;
    }
    
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Matter 
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Matter> pMatteres) throws Exception {
        try (ResultSet resultSet = DBContext.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase DBContext
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Matter
                Matter rol = new Matter(); 
                asignarDatosResultSet(rol, resultSet, 0); // Llenar las propiedaddes de la Entidad Matter con los datos obtenidos de la fila en el ResultSet
                pMatteres.add(rol); // Agregar la entidad Matter al ArrayList de Matter
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase DBContext   en el caso que suceda 
        }
    }
    
   // Metodo para obtener por Id un registro de la tabla de Matter 
    public static Matter obtenerPorId(Matter pMatter) throws Exception {
        Matter rol = new Matter();
        ArrayList<Matter> roles = new ArrayList();
        try (Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pMatter); // Obtener la consulta SELECT de la tabla Matter
            sql += " WHERE r.Id=?"; // Concatenar a la consulta SELECT de la tabla Matter el WHERE 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pMatter.getIdMatter()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, roles); // Llenar el ArrayList de Matter con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        if (roles.size() > 0) { // Verificar si el ArrayList de Matter trae mas de un registro en tal caso solo debe de traer uno
            rol = roles.get(0); // Si el ArrayList de Matter trae un registro o mas obtenemos solo el primero 
        }
        return rol; // Devolver el rol encontrado por Id 
    }

    // Metodo para obtener todos los registro de la tabla de Matter
    public static ArrayList<Matter> obtenerTodos() throws Exception {
        ArrayList<Matter> roles;
        roles = new ArrayList<>();
        try (Connection conn = DBContext.obtenerConexion();) {// Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Matter());  // Obtener la consulta SELECT de la tabla Matter
            sql += agregarOrderBy(new Matter());  // Concatenar a la consulta SELECT de la tabla Matter el ORDER BY por Id 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                obtenerDatos(ps, roles); // Llenar el ArrayList de Matter con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return roles; // Devolver el ArrayList de Matter
    }
    
    // Metodo para asignar los filtros de la consulta SELECT de la tabla de Matter de forma dinamica
    static void querySelect(Matter pMatter, DBContext.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pMatter.getIdMatter()> 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Matter
            pUtilQuery.AgregarWhereAnd(" r.Id=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Matter
                statement.setInt(pUtilQuery.getNumWhere(), pMatter.getIdMatter()); 
            }
        }
        // Verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Matter
        if (pMatter.getNameMatter()!= null && pMatter.getNameMatter().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" r.Nombre LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Matter
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMatter.getNameTeacher()+ "%"); 
            }
        }
    }

     // Metodo para obtener todos los registro de la tabla de Matter que cumplan con los filtros agregados 
     // a la consulta SELECT de la tabla de Matter 
    public static ArrayList<Matter> buscar(Matter pMatter) throws Exception {
        ArrayList<Matter> roles = new ArrayList();
        try (Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pMatter); // Obtener la consulta SELECT de la tabla Matter
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pMatter, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Matter 
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pMatter); // Concatenar a la consulta SELECT de la tabla Matter el ORDER BY por Id
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pMatter, utilQuery);  // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Matter
                obtenerDatos(ps, roles); // Llenar el ArrayList de Matter con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return roles; // Devolver el ArrayList de Matter
    }

}
