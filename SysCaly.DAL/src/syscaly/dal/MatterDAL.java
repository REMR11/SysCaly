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

    static String ObtenerCampos() {
        return " m.IdQualification, m.NameMatter, m.NameTeacher ";
    }

    private static String ObtenerSelect(Matter pMatter) {
        return "SELECT " + ObtenerCampos() + "FROM Matter m";
    }

    private static String AgregarOrderBy(Matter pMatter) {
        return " ORDER BY m.IdMatter DESC";
    }

    private static boolean ExisteMatter(Matter pMatter) throws Exception {
        boolean existe = false;
        ArrayList<Matter> matters = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = ObtenerSelect(pMatter);  // Obtener la consulta SELECT de la tabla Matter
            // Concatenar a la consulta SELECT de la tabla Matter el WHERE y el filtro para saber si existe el login
            sql += " WHERE u.IdMatter<>?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pMatter.getIdMatter());  // Agregar el parametros a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, matters); // Llenar el ArrayList de USuario con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement el en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        if (matters.size() > 0) { // Verificar si el ArrayList de Matter trae mas de un registro en tal caso solo debe de traer uno
            Matter matter;
            matter = matters.get(0); // Si el ArrayList de Matter trae un registro o mas obtenemos solo el primero 
            if (matter.getIdMatter() > 0) {
                existe = true;
            }
        }
        return existe; //Devolver la variable "existe" con el valor true o false si existe o no el Login en la tabla de Matter de la base de datos

    }

    // Metodo para poder insertar un nuevo registro en la tabla de Matter
    public static int crear(Matter pMatter) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteMatter(pMatter); // verificar si el usuario que se va a crear ya existe en nuestra base de datos
        if (existe == false) {
            try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
                //Definir la consulta INSERT a la tabla de Matter utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO Matter(IdQualification,NameMatter,NameTeacher) VALUES(?,?,?)";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                    ps.setInt(1, pMatter.getIdQualification()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    ps.setString(2, pMatter.getNameMatter()); // Agregar el parametro a la consulta donde estan el simbolo "?" #2 
                    ps.setString(3, pMatter.getNameTeacher()); // agregar el parametro a la consulta donde estan el simbolo "?" #3 
                    result = ps.executeUpdate(); // ejecutar la consulta INSERT en la base de datos
                    ps.close(); // cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
                }
                conn.close();
            } // Handle any errors that may have occurred.
            catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda
            }
        } else {
            result = 0;
            throw new RuntimeException("Materia ya existe"); // enviar una exception para notificar que el login existe
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }

    // Metodo para poder actualizar un registro en la tabla de Matter
    public static int modificar(Matter pMatter) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteMatter(pMatter); // verificar si el usuario que se va a modificar ya existe en nuestra base de datos
        if (existe == false) {
            try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
                //Definir la consulta UPDATE a la tabla de Matter utilizando el simbolo ? para enviar parametros
                sql = "UPDATE Matter SET IdQualification=?, `NameMatter=?, NameTeacher=? WHERE IdMatter=?";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                    ps.setInt(1, pMatter.getIdQualification()); // agregar el parametro a la consulta donde estan el simbolo ? #1  
                    ps.setString(2, pMatter.getNameMatter()); // agregar el parametro a la consulta donde estan el simbolo ? #2  
                    ps.setString(3, pMatter.getNameTeacher()); // agregar el parametro a la consulta donde estan el simbolo ? #3  
                    ps.setInt(6, pMatter.getIdMatter()); // agregar el parametro a la consulta donde estan el simbolo ? #6  
                    result = ps.executeUpdate(); // ejecutar la consulta UPDATE en la base de datos
                    ps.close(); // cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
                }
                conn.close(); // cerrar la conexion a la base de datos
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe"); // enviar una exception para notificar que el login existe
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }

    // Metodo para poder eliminar un registro en la tabla de Matter
    public static int eliminar(Matter pMatter) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            sql = "DELETE FROM Matter WHERE IdMatter=?"; //definir la consulta DELETE a la tabla de Matter utilizando el simbolo ? para enviar parametros
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {  // obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pMatter.getIdMatter()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate(); // ejecutar la consulta DELETE en la base de datos
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex;  // enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el DELETE en la base de datos 
    }

    // Metodo para llenar las propiedades de la entidad de Matter con los datos que viene en el ResultSet,
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int AsignarDatosResultSet(Matter pMatter, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT u.Id(indice 1), u.IdRol(indice 2), u.Nombre(indice 3), u.Apellido(indice 4), u.Login(indice 5), 
        // u.Estatus(indice 6), u.FechaRegistro(indice 7) * FROM Matter
        pIndex++;
        pMatter.setIdQualification(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pMatter.setNameMatter(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pMatter.setNameTeacher(pResultSet.getString(pIndex)); // index 3
        return pIndex;
    }

    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Matter
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Matter> pMatters) throws Exception {
        try ( ResultSet resultSet = DBContext.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase DBContext
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Matter
                Matter matter = new Matter();
                AsignarDatosResultSet(, resultSet, 0)
// Llenar las propiedaddes de la Entidad Matter con los datos obtenidos de la fila en el ResultSet
                asignarDatosResultSet(matter, resultSet, 0);
                pMatters.add(matter); // agregar la entidad Matter al ArrayList de Matter
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase DBContext   en el caso que suceda 
        }
        
    }
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Matter y JOIN a la tabla de Rol

    private static void obtenerDatosIncluirQualification(PreparedStatement pPS, ArrayList<Matter> pMatters) throws Exception {
        try ( ResultSet resultSet = DBContext.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase DBContext
            HashMap<Integer, ArrayList<Qualification>> QualificationMap = new HashMap(); //crear un HashMap para automatizar la creacion de instancias de la clase Rol
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Matter JOIN a la tabla de Rol
                Matter matter = new Matter();
                // Llenar las propiedaddes de la Entidad Matter con los datos obtenidos de la fila en el ResultSet
                int index = AsignarDatosResultSet(matter, resultSet, 0);
                if (QualificationMap.containsKey(matter.getIdQualification()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    ArrayList<Qualification> qualifications = new ArrayList<>();
                    // en el caso que el Rol no este en el HashMap se asignara
                    QualificationDAL.AsignarDatosResultSet(matter, resultSet, 0);
                    QualificationMap.put(matter.getIdMatter(), qualifications); // agregar el Rol al  HashMap
                    matter.setQualifications(qualifications); // agregar el Rol al Matter
                } else {
                    matter.setQualifications(QualificationMap.get(matter.getQualifications()));
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Matter
                }
                pMatters.add(matter); // Agregar el Matter de la fila actual al ArrayList de Matter
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase DBContext   en el caso que suceda 
        }
    }

    // Metodo para obtener por Id un registro de la tabla de Matter 
    public static Matter obtenerPorId(Matter pMatter) throws Exception {
        Matter usuario = new Matter();
        ArrayList<Matter> matters = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = ObtenerSelect(pMatter); // obtener la consulta SELECT de la tabla Matter
            // Concatenar a la consulta SELECT de la tabla Matter el WHERE  para comparar el campo Id
            sql += " WHERE m.IdMatter=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pMatter.getIdMatter()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, matters); // Llenar el ArrayList de Matter con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        if (matters.size() > 0) { // verificar si el ArrayList de Matter trae mas de un registro en tal caso solo debe de traer uno
            usuario = matters.get(0); // Si el ArrayList de Matter trae un registro o mas obtenemos solo el primero
        }
        return usuario; // devolver el Matter encontrado por Id 
    }

    // Metodo para obtener todos los registro de la tabla de Matter
    public static ArrayList<Matter> obtenerTodos() throws Exception {
        ArrayList<Matter> usuarios;
        usuarios = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = ObtenerSelect(new Matter()); // obtener la consulta SELECT de la tabla Matter
            sql += AgregarOrderBy(new Matter()); // concatenar a la consulta SELECT de la tabla Matter el ORDER BY por Id 
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de Matter con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return usuarios; // devolver el ArrayList de Matter
    }

    // Metodo para asignar los filtros de la consulta SELECT de la tabla de Matter de forma dinamica
    static void querySelect(Matter pMatter, DBContext.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // obtener el PreparedStatement al cual aplicar los parametros
        if (pMatter.getIdMatter() > 0) { // verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Matter
            pUtilQuery.AgregarWhereAnd(" m.IdMatter=? "); // agregar el campo Id al filtro de la consulta SELECT y agregar el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Id a la consulta SELECT de la tabla de Matter
                statement.setInt(pUtilQuery.getNumWhere(), pMatter.getIdMatter());
            }
        }
        // verificar si se va incluir el campo IdRol en el filtro de la consulta SELECT de la tabla de Matter
        if (pMatter.getIdQualification() > 0) {
            pUtilQuery.AgregarWhereAnd(" q.IdQualification=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Matter
                statement.setInt(pUtilQuery.getNumWhere(), pMatter.getIdQualification());
            }
        }
        // verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Matter
        if (pMatter.getNameMatter() != null && pMatter.getNameMatter().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" m.NameMatter LIKE ? "); // agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Matter
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMatter.getNameMatter() + "%");
            }
        }
        // Verificar si se va incluir el campo Apellido en el filtro de la consulta SELECT de la tabla de Matter
        if (pMatter.getNameTeacher() != null && pMatter.getNameTeacher().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" m.NameTeacher LIKE ? "); // agregar el campo Apellido al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Apellido a la consulta SELECT de la tabla de Matter
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMatter.getNameTeacher() + "%");
            }
        }

    }

    // Metodo para obtener todos los registro de la tabla de Matter que cumplan con los filtros agregados 
    // a la consulta SELECT de la tabla de Matter 
    public static ArrayList<Matter> buscar(Matter pMatter) throws Exception {
        ArrayList<Matter> usuarios = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = ObtenerSelect(pMatter); // obtener la consulta SELECT de la tabla Matter
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pMatter, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Matter 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pMatter); // Concatenar a la consulta SELECT de la tabla Matter el ORDER BY por Id
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pMatter, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Matter
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de Matter con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return usuarios; // Devolver el ArrayList de Matter
    }

    // Metodo para obtener todos los registro de la tabla de Matter que cumplan con los filtros agregados 
    // a la consulta SELECT de la tabla de Matter 
    public static ArrayList<Matter> buscarIncluirRol(Matter pMatter) throws Exception {
        ArrayList<Matter> usuarios = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = "SELECT "; // Iniciar la variables para el String de la consulta SELECT
            sql += ObtenerCampos(); // Obtener los campos de la tabla de Matter que iran en el SELECT
            sql += ",";
            sql += QualificationDAL.ObtenerCampos(); // Obtener los campos de la tabla de Rol que iran en el SELECT
            sql += " FROM Matter m";
            sql += " JOIN Qualification q on (m.IdQualificaton =q.IdQualificaton)"; // agregar el join para unir la tabla de Matter con Rol
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pMatter, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Matter 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pMatter); // Concatenar a la consulta SELECT de la tabla Matter el ORDER BY por Id
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pMatter, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Matter
                obtenerDatosIncluirQualification(ps, usuarios);// Llenar el ArrayList de Matter con las fila que devolvera la consulta SELECT a la tabla de Matter
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex;// Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return usuarios; // Devolver el ArrayList de Matter
    }

}
