/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.util.*;
import java.sql.*;
import syscaly.el.*;
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

    private static String agregarOrderBy(Matter pMatter) {
        return " ORDER BY m.IdMatter DESC";
    }

    public static int crear(Matter pMatter) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "INSERT INTO Matter(NameMatter, NameTeaher) VALUES(?,?)";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setString(1, pMatter.getNameMatter());
                ps.setString(2, pMatter.getNameTeacher());
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


      public static int modificar(Matter pMatter) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "UPDATE Matter SET NameMatter=? NameTeacher=? WHERE IdMatter=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setString(1, pMatter.getNameMatter());
                ps.setString(2, pMatter.getNameTeacher());
                ps.setInt(3, pMatter.getIdMatter());
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
    public static int eliminar(Matter pMatter) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "DELETE FROM Matter WHERE IdMatter=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMatter.getIdMatter());
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

    static int asignarDatosResultSet(Matter pMatter, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pMatter.setIdMatter(pResultSet.getInt(pIndex));
        pIndex++;
        pMatter.setNameMatter(pResultSet.getString(pIndex));
        pIndex++;
        pMatter.setNameTeacher(pResultSet.getString(pIndex));
        return pIndex;
    }

    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Matter> pMatteres) throws Exception {
        try ( ResultSet resultSet = DBContext.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Matter rol = new Matter();
                asignarDatosResultSet(rol, resultSet, 0);
                pMatteres.add(rol);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Matter obtenerPorId(Matter pMatter) throws Exception {
        Matter rol = new Matter();
        ArrayList<Matter> matters = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = obtenerSelect(pMatter);
            sql += " WHERE m.IdMatter=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMatter.getIdMatter());
                obtenerDatos(ps, matters);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (matters.size() > 0) {
            rol = matters.get(0);
        }
        return rol;
    }

    // Metodo para obtener todos los registro de la tabla de Matter
    public static ArrayList<Matter> obtenerTodos() throws Exception {
        ArrayList<Matter> matters;
        matters = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = obtenerSelect(new Matter());
            sql += agregarOrderBy(new Matter());
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, matters);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return matters;
    }

    static void querySelect(Matter pMatter, DBContext.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pMatter.getIdMatter() > 0) {
            pUtilQuery.AgregarWhereAnd(" m.IdMatter=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pMatter.getIdMatter());
            }
        }

        if (pMatter.getNameMatter() != null && pMatter.getNameMatter().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" m.NameMatter LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMatter.getNameMatter() + "%");
            }
        }

        if (pMatter.getNameTeacher() != null && pMatter.getNameTeacher().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" m.NameTeacher LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMatter.getNameTeacher() + "%");
            }
        }

    }

    public static ArrayList<Matter> buscar(Matter pMatter) throws Exception {
        ArrayList<Matter> matters = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = obtenerSelect(pMatter);
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pMatter, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pMatter);
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pMatter, utilQuery);
                obtenerDatos(ps, matters);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return matters;
    }
}
