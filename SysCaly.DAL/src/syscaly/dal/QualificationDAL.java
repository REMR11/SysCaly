/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import syscaly.el.*;

/**
 *
 * @author ronal
 */
public class QualificationDAL {

    static String ObtenerCampos() {
        return "q.IdMatter, q.IdStudent, q.Period, q.Cycle, q.Qualification";
    }

    private static String ObtenerSelect(Qualification pQualification) {
        return "SELECT " + ObtenerCampos() + " FROM Qualification q";
    }

    private static String AgregarOrderBy(Qualification pQualification) {
        return " ORDER BY q.IdQualification DESC";
    }

    private static boolean ExistsQualification(Qualification pQualification) throws Exception {
        boolean exists = false;
        ArrayList<Qualification> qualifications = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(pQualification);
            sql += " WHERE q.IdQualification";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pQualification.getIdMatter());
                ps.setInt(2, pQualification.getIdStudent());
                ps.setInt(3, pQualification.getPeriod());
                ps.setInt(4, pQualification.getCycle());
                ps.setInt(5, pQualification.getQualification());
                ps.setInt(6, pQualification.getIdQualification());
                ObtenerDatos(ps, qualifications);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (qualifications.size() > 0) {
            Qualification qualification;
            qualification = qualifications.get(0);
            if (qualification.getIdQualification() > 0) {
                exists = true;
            }
        }
        return exists;
    }

    public static int crear(Qualification pqualification) throws Exception {
        int result;
        String sql;
        boolean existe = ExistsQualification(pqualification);
        if (existe == false) {
            try ( Connection conn = DBContext.obtenerConexion();) {
                sql = "INSERT INTO Qualification(IdMatter, IdStudent, Period, Cycle, Qualification) VALUES(?,?,?,?,?)";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pqualification.getIdMatter());
                    ps.setInt(2, pqualification.getIdStudent());
                    ps.setInt(3, pqualification.getPeriod());
                    ps.setInt(4, pqualification.getCycle());
                    ps.setInt(5, pqualification.getQualification());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Registro Existente");
        }
        return result;
    }

    public static int modificar(Qualification pquaQualification) throws Exception {
        int result;
        String sql;
        boolean existe = ExistsQualification(pquaQualification);
        if (existe == false) {
            try ( Connection conn = DBContext.obtenerConexion();) {
                sql = "UPDATE Usuario SET IdMatter=?,  IdStudent=?, Period=?, Cycle=?, Qualification=? WHERE IdQualification=?";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pquaQualification.getIdMatter());
                    ps.setInt(2, pquaQualification.getIdStudent());
                    ps.setInt(3, pquaQualification.getPeriod());
                    ps.setInt(4, pquaQualification.getCycle());
                    ps.setInt(5, pquaQualification.getQualification());
                    ps.setInt(6, pquaQualification.getIdQualification());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Calificacion realizada");
        }
        return result;
    }

    public static int eliminar(Qualification pQualification) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "DELETE FROM Qualification q WHERE q.IdQualification=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pQualification.getIdQualification());
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
                
    static int AsignarDatosResultSet(Qualification pQualification, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pQualification.setIdQualification(pResultSet.getInt(pIndex));
        pIndex++;
        pQualification.setIdMatter(pResultSet.getInt(pIndex));
        pIndex++;
        pQualification.setIdStudent(pResultSet.getInt(pIndex));
        pIndex++;
        pQualification.setPeriod(pResultSet.getInt(pIndex));
        pIndex++;
        pQualification.setCycle(pResultSet.getInt(pIndex));
        pIndex++;
        pQualification.setQualification(pResultSet.getInt(pIndex));
        return pIndex;
    }

    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Qualification> pQualifications) throws Exception {
        try ( ResultSet resultSet = DBContext.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Qualification qualification = new Qualification();
                AsignarDatosResultSet(qualification, resultSet, 0);
                pQualifications.add(qualification);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Qualification obtenerPorId(Qualification pQualification) throws Exception {
        Qualification qualification = new Qualification();
        ArrayList<Qualification> qualifications = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(pQualification);
            sql += " WHERE q.IdQualification=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pQualification.getIdQualification());
                ObtenerDatos(ps, qualifications);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (qualifications.size() > 0) {
            qualification = qualifications.get(0);
        }
        return qualification;
    }

    public static ArrayList<Qualification> obtenerTodos() throws Exception {
        ArrayList<Qualification> qualification;
        qualification = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(new Qualification()); //
            sql += AgregarOrderBy(new Qualification()); 
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) { 
                ObtenerDatos(ps, qualification); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } catch (SQLException ex) {
            throw ex; 
        }
        return qualification;
    }
    
    static void QuerySelect(Qualification pQualification, DBContext.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // obtener el PreparedStatement al cual aplicar los parametros
        if (pQualification.getIdQualification() > 0) { // verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Usuario
            pUtilQuery.AgregarWhereAnd(" q.IdQualification=? "); // agregar el campo Id al filtro de la consulta SELECT y agregar el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Id a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pQualification.getIdQualification());
            }
        }
        
// verificar si se va incluir el campo IdRol en el filtro de la consulta SELECT de la tabla de Usuario
        if (pQualification.getIdMatter()> 0) {
            pUtilQuery.AgregarWhereAnd(" q.IdMatter=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pQualification.getIdMatter());
            }
        }
        
        // verificar si se va incluir el campo IdRol en el filtro de la consulta SELECT de la tabla de Usuario
        if (pQualification.getIdStudent()> 0) {
            pUtilQuery.AgregarWhereAnd(" q.IdStudent=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pQualification.getIdStudent());
            }
        }
        // verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Usuario
        if (pQualification.getPeriod() >0) {
            pUtilQuery.AgregarWhereAnd(" q.Period LIKE ? "); // agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pQualification.getPeriod() + "%");
            }
        }
        // Verificar si se va incluir el campo Apellido en el filtro de la consulta SELECT de la tabla de Usuario
        if (pQualification.getCycle() >0) {
            pUtilQuery.AgregarWhereAnd(" q.Cycle LIKE ? "); // agregar el campo Apellido al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Apellido a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pQualification.getCycle() + "%");
            }
        }
        // Verificar si se va incluir el campo Login en el filtro de la consulta SELECT de la tabla de Usuario
        if (pQualification.getQualification() >0) {
            pUtilQuery.AgregarWhereAnd(" q.Qualification=? "); // agregar el campo Login al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Login a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pQualification.getQualification());
            }
        }
    }
    
     public static ArrayList<Qualification> buscar(Qualification pQualification) throws Exception {
        ArrayList<Qualification> qualifications = new ArrayList();
        try (Connection conn = DBContext.obtenerConexion();) { 
            String sql = ObtenerSelect(pQualification); 
            DBContext comundb = new DBContext();
            DBContext.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pQualification, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pQualification); 
            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pQualification, utilQuery); 
                ObtenerDatos(ps, qualifications);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return qualifications; 
    }
     
     
}
