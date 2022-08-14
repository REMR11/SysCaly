/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.util.*;
import java.sql.*;
import syscaly.el.*; // Agregar la referencia al proyecto de entidades de negocio y poder utilizar las entidades de Rol y UserSys
import java.time.LocalDate;

/**
 *
 * @author ronal
 */
public class UserSysDal {

    public static String encriptarMD5(String txt) throws Exception {
        try {
            StringBuffer sb;
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw ex;
        }
    }

    static String obtenerCampos() {
        return "u.Id, u.IdRol, u.IdClassroom, u.IdStudent, u.NIE u.Name, u.LastName, u.Login, u.StatusUser, u.DateCreateUser";
    }

    private static String obtenerSelect(UserSys pUserSys) {
        String sql;
        sql = "SELECT ";
        if (pUserSys.getTop_aux() > 0 && DBContext.TIPODB == DBContext.TipoDB.SQLSERVER) {
            sql += "TOP " + pUserSys.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM UserSys u");
        return sql;
    }

    private static String agregarOrderBy(UserSys pUserSys) {
        String sql = " ORDER BY u.Id DESC";
        if (pUserSys.getTop_aux() > 0 && DBContext.TIPODB == DBContext.TipoDB.MYSQL) {
            // sea MYSQL
            sql += " LIMIT " + pUserSys.getTop_aux() + " ";
        }
        return sql;
    }
    
    // ya existe en la base de datos

    private static boolean existeLogin(UserSys pUserSys) throws Exception {
        boolean existe = false;
        ArrayList<UserSys> usuarios = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { 
            String sql = obtenerSelect(pUserSys);  // Obtener la consulta SELECT de la tabla UserSys
            // Concatenar a la consulta SELECT de la tabla UserSys el WHERE y el filtro para saber si existe el login
            sql += " WHERE u.Id<>? AND u.Login=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pUserSys.getId());  // Agregar el parametros a la consulta donde estan el simbolo ? #1 
                ps.setString(2, pUserSys.getLogin());  // Agregar el parametros a la consulta donde estan el simbolo ? #2 
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de USuario con las fila que devolvera la consulta SELECT a la tabla de UserSys
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement el en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        if (usuarios.size() > 0) { // Verificar si el ArrayList de UserSys trae mas de un registro en tal caso solo debe de traer uno
            UserSys usuario;
            // Se solucciono tenia valor de 1 cuando debe de ser cero
            usuario = usuarios.get(0); // Si el ArrayList de UserSys trae un registro o mas obtenemos solo el primero 
            if (usuario.getId() > 0 && usuario.getLogin().equals(pUserSys.getLogin())) {
                // Si el Id de UserSys es mayor a cero y el Login que se busco en la tabla de UserSys es igual al que solicitamos
                // en los parametros significa que el login ya existe en la base de datos y devolvemos true en la variable "existe"
                existe = true;
            }
        }
        return existe; //Devolver la variable "existe" con el valor true o false si existe o no el Login en la tabla de UserSys de la base de datos

    }

    // Metodo para poder insertar un nuevo registro en la tabla de UserSys
    public static int crear(UserSys pUserSys) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pUserSys); // verificar si el usuario que se va a crear ya existe en nuestra base de datos
        if (existe == false) {
            try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
                //Definir la consulta INSERT a la tabla de UserSys utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO UserSys(IdRol, IdClassroom, IdStudent, Name,LastName,Login,Password, StatusUser,DateCreateUser) VALUES(?,?,?,?,?,?,?,?,?,?)";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                    ps.setInt(1, pUserSys.getIdRol()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    // Agregar el parametro a la consulta donde estan el simbolo "?" #2 
                    ps.setInt(2, pUserSys.getIdClassroom());
                    ps.setInt(3, pUserSys.getIdStudent());
                    ps.setString(4, pUserSys.getName());
                    ps.setString(5, pUserSys.getLastName());
                    ps.setString(6, pUserSys.getLogin());
                    ps.setString(7, encriptarMD5(pUserSys.getPassword()));
                    ps.setByte(8, pUserSys.getStatusUser());
                    ps.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
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
            throw new RuntimeException("Login ya existe"); // enviar una exception para notificar que el login existe
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }

    // Metodo para poder actualizar un registro en la tabla de UserSys
    public static int modificar(UserSys pUserSys) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pUserSys); // verificar si el usuario que se va a modificar ya existe en nuestra base de datos
        if (existe == false) {
            try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
                //Definir la consulta UPDATE a la tabla de UserSys utilizando el simbolo ? para enviar parametros
                sql = "UPDATE UserSys SET IdRol=?, IdClassroom=?, IdStudent=?, NIE =? Name=?, LastName=?, Login=?, StatusUser=? WHERE Id=?";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                    ps.setInt(1, pUserSys.getIdRol()); // agregar el parametro a la consulta donde estan el simbolo ? #1  
                    ps.setInt(2, pUserSys.getIdClassroom());
                    ps.setInt(3, pUserSys.getIdStudent());
                    ps.setInt(4, pUserSys.getNIE());
                    ps.setString(5, pUserSys.getName());
                    ps.setString(6, pUserSys.getLastName());
                    ps.setString(7, pUserSys.getLogin());
                    ps.setByte(8, pUserSys.getStatusUser()); // agregar el parametro a la consulta donde estan el simbolo ? #5  
                    ps.setInt(6, pUserSys.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #6  
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

    // Metodo para poder eliminar un registro en la tabla de UserSys
    public static int eliminar(UserSys pUserSys) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            sql = "DELETE FROM UserSys WHERE Id=?"; //definir la consulta DELETE a la tabla de UserSys utilizando el simbolo ? para enviar parametros
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {  // obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pUserSys.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
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

    // Metodo para llenar las propiedades de la entidad de UserSys con los datos que viene en el ResultSet,
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int asignarDatosResultSet(UserSys pUserSys, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT u.Id(indice 1), u.IdRol(indice 2), u.Nombre(indice 3), u.Apellido(indice 4), u.Login(indice 5), 
        // u.Estatus(indice 6), u.FechaRegistro(indice 7) * FROM UserSys
        pIndex++;
        pUserSys.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pUserSys.setIdRol(pResultSet.getInt(pIndex)); // index 2
        pIndex++;
        pUserSys.setIdClassroom(pResultSet.getInt(pIndex)); // index 3
        pIndex++;
        pUserSys.setIdStudent(pResultSet.getInt(pIndex));
        pIndex++;
        pUserSys.setNIE(pResultSet.getInt(pIndex));
        pIndex++;
        pUserSys.setName(pResultSet.getString(pIndex));
        pIndex++;
        pUserSys.setLastName(pResultSet.getString(pIndex));
        pIndex++;
        pUserSys.setLogin(pResultSet.getString(pIndex)); // index 5
        pIndex++;
        pUserSys.setStatusUser(pResultSet.getByte(pIndex)); // index 6
        pIndex++;
        pUserSys.setDateCreateUser(pResultSet.getDate(pIndex).toLocalDate()); // index 7
        return pIndex;
    }

    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de UserSys
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<UserSys> pUserSys) throws Exception {
        try ( ResultSet resultSet = DBContext.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase DBContext
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla UserSys
                UserSys usuario = new UserSys();
                // Llenar las propiedaddes de la Entidad UserSys con los datos obtenidos de la fila en el ResultSet
                asignarDatosResultSet(usuario, resultSet, 0);
                pUserSys.add(usuario); // agregar la entidad UserSys al ArrayList de UserSys
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase DBContext   en el caso que suceda 
        }
    }
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de UserSys y JOIN a la tabla de Rol

    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<UserSys> pUserSyss) throws Exception {
        try ( ResultSet resultSet = DBContext.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase DBContext
            HashMap<Integer, Rol> rolMap = new HashMap(); //crear un HashMap para automatizar la creacion de instancias de la clase Rol
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla UserSys JOIN a la tabla de Rol
                UserSys usuario = new UserSys();
                // Llenar las propiedaddes de la Entidad UserSys con los datos obtenidos de la fila en el ResultSet
                int index = asignarDatosResultSet(usuario, resultSet, 0);
                if (rolMap.containsKey(usuario.getIdRol()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Rol rol = new Rol();
                    // en el caso que el Rol no este en el HashMap se asignara
                    RolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); // agregar el Rol al  HashMap
                    usuario.setRol(rol); // agregar el Rol al UserSys
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al UserSys
                    usuario.setRol(rolMap.get(usuario.getIdRol()));
                }
                pUserSyss.add(usuario); // Agregar el UserSys de la fila actual al ArrayList de UserSys
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase DBContext   en el caso que suceda 
        }
    }

    // Metodo para obtener por Id un registro de la tabla de UserSys 
    public static UserSys obtenerPorId(UserSys pUserSys) throws Exception {
        UserSys usuario = new UserSys();
        ArrayList<UserSys> usuarios = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pUserSys); // obtener la consulta SELECT de la tabla UserSys
            // Concatenar a la consulta SELECT de la tabla UserSys el WHERE  para comparar el campo Id
            sql += " WHERE u.Id=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                ps.setInt(1, pUserSys.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de UserSys con las fila que devolvera la consulta SELECT a la tabla de UserSys
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        if (usuarios.size() > 0) { // verificar si el ArrayList de UserSys trae mas de un registro en tal caso solo debe de traer uno
            usuario = usuarios.get(0); // Si el ArrayList de UserSys trae un registro o mas obtenemos solo el primero
        }
        return usuario; // devolver el UserSys encontrado por Id 
    }

    // Metodo para obtener todos los registro de la tabla de UserSys
    public static ArrayList<UserSys> obtenerTodos() throws Exception {
        ArrayList<UserSys> usuarios;
        usuarios = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new UserSys()); // obtener la consulta SELECT de la tabla UserSys
            sql += agregarOrderBy(new UserSys()); // concatenar a la consulta SELECT de la tabla UserSys el ORDER BY por Id 
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de UserSys con las fila que devolvera la consulta SELECT a la tabla de UserSys
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return usuarios; // devolver el ArrayList de UserSys
    }

    // Metodo para asignar los filtros de la consulta SELECT de la tabla de UserSys de forma dinamica
    static void querySelect(UserSys pUserSys, DBContext.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // obtener el PreparedStatement al cual aplicar los parametros
        if (pUserSys.getId() > 0) { // verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de UserSys
            pUtilQuery.AgregarWhereAnd(" u.Id=? "); // agregar el campo Id al filtro de la consulta SELECT y agregar el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Id a la consulta SELECT de la tabla de UserSys
                statement.setInt(pUtilQuery.getNumWhere(), pUserSys.getId());
            }
        }
        // verificar si se va incluir el campo IdRol en el filtro de la consulta SELECT de la tabla de UserSys
        if (pUserSys.getIdRol() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.IdRol=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de UserSys
                statement.setInt(pUtilQuery.getNumWhere(), pUserSys.getIdRol());
            }
        }
        // verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de UserSys
        if (pUserSys.getName() != null && pUserSys.getName().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Nombre LIKE ? "); // agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Nombre a la consulta SELECT de la tabla de UserSys
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUserSys.getName() + "%");
            }
        }
        // Verificar si se va incluir el campo Apellido en el filtro de la consulta SELECT de la tabla de UserSys
        if (pUserSys.getLastName() != null && pUserSys.getLastName().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Apellido LIKE ? "); // agregar el campo Apellido al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Apellido a la consulta SELECT de la tabla de UserSys
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUserSys.getLastName() + "%");
            }
        }
        // Verificar si se va incluir el campo Login en el filtro de la consulta SELECT de la tabla de UserSys
        if (pUserSys.getLogin() != null && pUserSys.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Login=? "); // agregar el campo Login al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Login a la consulta SELECT de la tabla de UserSys
                statement.setString(pUtilQuery.getNumWhere(), pUserSys.getLogin());
            }
        }
        // Verificar si se va incluir el campo Estatus en el filtro de la consulta SELECT de la tabla de UserSys
        if (pUserSys.getStatusUser() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.Estatus=? "); // agregar el campo Estatus al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // agregar el parametro del campo Estatus a la consulta SELECT de la tabla de UserSys
                statement.setInt(pUtilQuery.getNumWhere(), pUserSys.getStatusUser());
            }
        }
    }

    // Metodo para obtener todos los registro de la tabla de UserSys que cumplan con los filtros agregados 
    // a la consulta SELECT de la tabla de UserSys 
    public static ArrayList<UserSys> buscar(UserSys pUserSys) throws Exception {
        ArrayList<UserSys> usuarios = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pUserSys); // obtener la consulta SELECT de la tabla UserSys
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pUserSys, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de UserSys 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pUserSys); // Concatenar a la consulta SELECT de la tabla UserSys el ORDER BY por Id
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase DBContext
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pUserSys, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de UserSys
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de UserSys con las fila que devolvera la consulta SELECT a la tabla de UserSys
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return usuarios; // Devolver el ArrayList de UserSys
    }

    // Metodo para verificar si el UserSys puede ser autorizado en el sistema
    // comparando el Login, Password, Estatus en la base de datos
    public static UserSys login(UserSys pUserSys) throws Exception {
        UserSys usuario = new UserSys();
        ArrayList<UserSys> usuarios = new ArrayList();
        String password = encriptarMD5(pUserSys.getPassword()); // Encriptar el password en MD5
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pUserSys); // Obtener la consulta SELECT de la tabla UserSys
            // Concatenar a la consulta SELECT de la tabla UserSys el WHERE  para comparar los campos de Login, Password, Estatus
            sql += " WHERE u.Login=? AND u.Password=? AND u.Estatus=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                ps.setString(1, pUserSys.getLogin()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                ps.setString(2, password); // Agregar el parametro a la consulta donde estan el simbolo ? #2 
                ps.setByte(3, UserSys.StatusUser.ACTIVO); // Agregar el parametro a la consulta donde estan el simbolo ? #3 
                obtenerDatos(ps, usuarios); // Llenar el ArrayList de UserSys con las fila que devolvera la consulta SELECT a la tabla de UserSys
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        if (usuarios.size() > 0) { // Verificar si el ArrayList de UserSys trae mas de un registro en tal caso solo debe de traer uno
            usuario = usuarios.get(0); // Si el ArrayList de UserSys trae un registro o mas obtenemos solo el primero
        }
        return usuario; // Devolver la instancia de UserSys 
    }

    // Metodo para cambiar el password de un UserSys el cual solo se puede cambiar si envia el password actual correctamente
    public static int cambiarPassword(UserSys pUserSys, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        UserSys usuarioAnt = new UserSys();
        usuarioAnt.setLogin(pUserSys.getLogin());
        usuarioAnt.setPassword(pPasswordAnt);
        UserSys usuarioAut = login(usuarioAnt); // Obtenemos el UserSys autorizado validandolo en el metodo de login
        // Si el usuario que retorno el metodo de login tiene el Id mayor a cero y el Login es igual que el Login del UserSys que viene
        // en el parametro es un UserSys Autorizado
        if (usuarioAut.getId() > 0 && usuarioAut.getLogin().equals(pUserSys.getLogin())) {
            try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
                sql = "UPDATE UserSys SET Password=? WHERE Id=?"; // Crear la consulta Update a la tabla de UserSys para poder modificar el Password
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                    // Agregar el parametro a la consulta donde estan el simbolo ? #1 pero antes encriptar el password para enviarlo encriptado
                    ps.setString(1, encriptarMD5(pUserSys.getPassword())); //
                    ps.setInt(2, pUserSys.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #2 
                    result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                    ps.close(); // Cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
                }
                conn.close(); // Cerrar la conexion a la base de datos
            } catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
            }
        } else {
            result = 0;
            // Enviar la excepcion en el caso que el usuario que intenta modificar el password ingresa un password incorrecto
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }

    // Metodo para obtener todos los registro de la tabla de UserSys que cumplan con los filtros agregados 
    // a la consulta SELECT de la tabla de UserSys 
    public static ArrayList<UserSys> buscarIncluirRol(UserSys pUserSys) throws Exception {
        ArrayList<UserSys> usuarios = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) { // Obtener la conexion desde la clase DBContext y encerrarla en try para cierre automatico
            String sql = "SELECT "; // Iniciar la variables para el String de la consulta SELECT
            if (pUserSys.getTop_aux() > 0 && DBContext.TIPODB == DBContext.TipoDB.SQLSERVER) {
                sql += "TOP " + pUserSys.getTop_aux() + " "; // Agregar el TOP en el caso que se este utilizando SQL SERVER
            }
            sql += obtenerCampos(); // Obtener los campos de la tabla de UserSys que iran en el SELECT
            sql += ",";
            sql += RolDAL.obtenerCampos(); // Obtener los campos de la tabla de Rol que iran en el SELECT
            sql += " FROM UserSys u";
            sql += " JOIN Rol r on (u.IdRol=r.Id)"; // agregar el join para unir la tabla de UserSys con Rol
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pUserSys, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de UserSys 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pUserSys); // Concatenar a la consulta SELECT de la tabla UserSys el ORDER BY por Id
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase DBContext
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pUserSys, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de UserSys
                obtenerDatosIncluirRol(ps, usuarios);// Llenar el ArrayList de UserSys con las fila que devolvera la consulta SELECT a la tabla de UserSys
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex;// Enviar al siguiente metodo el error al obtener la conexion  de la clase DBContext en el caso que suceda
        }
        return usuarios; // Devolver el ArrayList de UserSys
    }
}
