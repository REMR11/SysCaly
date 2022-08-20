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
public class StudentDAL {

    static String ObtenerCampos() {
        return "s.IdStudent, s.NIE, s.NameStudent, s.LastName, s.Turn";
    }

    private static String ObtenerSelect(Student pStudent) {
        return "SELECT " + ObtenerCampos()+ " From Student s";
    }

    private static String AgregarOrderBy(Student pStudent) {
        String sql = " ORDER BY s.IdStudent DESC";

        return sql;
    }

    public static int crear(Student pStudent) throws SQLException {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "INSERT INTO Student(NIE, NameStudent, LastName, Turn)VALUES(?,?,?,?)";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pStudent.getNIE());
                ps.setString(2, pStudent.getNameStudent());
                ps.setString(3, pStudent.getLastName());
                ps.setString(4, pStudent.getTurn());
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

    public static int modificar(Student pStudent) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "UPDATE Student SET NIE=?, NameStudent=?, LastName=?, Turn=? WHERE IdStudent=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pStudent.getNIE());
                ps.setString(2, pStudent.getNameStudent());
                ps.setString(3, pStudent.getLastName());
                ps.setString(4, pStudent.getTurn());
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

    public static int Eliminar(Student pStudent) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "DELETE FROM Student WHERE IdStudent=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pStudent.getIdStudent());
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

    static int AsignarDatosResultSet(Student pStudent, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pStudent.setIdStudent(pResultSet.getInt(pIndex));
        pIndex++;
        pStudent.setNIE(pResultSet.getInt(pIndex));
        pIndex++;
        pStudent.setNameStudent(pResultSet.getString(pIndex));
        pIndex++;
        pStudent.setLastName(pResultSet.getString(pIndex));
        pIndex++;
        pStudent.setTurn(pResultSet.getString(pIndex));
        return pIndex;
    }

    private static void ObtenerDatos(PreparedStatement pPs, ArrayList<Student> pStudents) throws Exception {
        try ( ResultSet resultSet = DBContext.obtenerResultSet(pPs);) {
            while (resultSet.next()) {
                Student student = new Student();
                AsignarDatosResultSet(student, resultSet, 0);
                pStudents.add(student);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Student ObtenerPorId(Student PStudent) throws Exception {
        Student student = new Student();
        ArrayList<Student> students = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(PStudent);
            sql += " WHERE s.IdStudent=? ";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ps.setInt(1, PStudent.getIdStudent());
                ObtenerDatos(ps, students);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (students.size() > 0) {
            student = students.get(0);
        }
        return student;
    }

    public static ArrayList<Student> ObtenerTodos() throws Exception {
        ArrayList<Student> Students;
        Students = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(new Student());
            sql += AgregarOrderBy(new Student());
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                ObtenerDatos(ps, Students);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return Students;
    }

    static void QuerySelect(Student pstudent, DBContext.UtilQuery pUtilQuery) throws Exception {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pstudent.getIdStudent() > 0) {
            pUtilQuery.AgregarWhereAnd(" s.IdStudent=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pstudent.getIdStudent());
            }
        }
    }

    public static ArrayList<Student> Buscar(Student pStudent) throws Exception {
        ArrayList<Student> students = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(pStudent);
            DBContext dBContext = new DBContext();
            DBContext.UtilQuery utilQuery = dBContext.new UtilQuery(sql, null, 0);
            QuerySelect(pStudent, utilQuery);
            sql = AgregarOrderBy(pStudent);
            sql += AgregarOrderBy(pStudent);

            try (PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pStudent, utilQuery);
                ObtenerDatos(ps, students);
                ps.close();

            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return students;
    }
}
