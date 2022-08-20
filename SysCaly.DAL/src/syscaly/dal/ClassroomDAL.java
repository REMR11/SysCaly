/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.util.*;
import java.util.HashMap;
import java.sql.*;
import syscaly.el.*;

/**
 *
 * @author ronal
 */
public class ClassroomDAL {

    static String ObtenerCampos() {
        return "c.IdClassroom, c.IdStudent, c.IdQualification, c.NumberOfStudent, c.Section ";
    }

    private static String ObtenerSelect(Classroom pClassroom) {
         return "SELECT " + ObtenerCampos() + "FROM Classroom c ";
    }

    private static String AgregarOrderBy(Classroom pClassroom) {
        String sql = "ORDER BY c.IdClassroom DESC ";

        return sql;
    }

    private static boolean ExistsClassroom(Classroom pClassroom) throws SQLException {
        boolean existsC = false;
        ArrayList<Classroom> Classrooms = new ArrayList<>();
        try ( Connection conn = DBContext.obtenerConexion();) {

            String sql = ObtenerSelect(pClassroom);
            sql += " WHERE c.IdClassroom<>? ";
            try {
                PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);
                ps.setInt(1, pClassroom.getIdClassroom());
                //ObtenerDatos(ps, Classrooms);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (Classrooms.size() > 0) {
            Classroom classroom;

            classroom = Classrooms.get(0);
            if (classroom.getIdClassroom() > 0) {
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
            try ( Connection conn = DBContext.obtenerConexion();) {

                sql = "INSERT INTO Classroom(IdStudent, IdQualification, NumberOfStudent, Section) VALUES(?,?,?,?)";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pClassroom.getIdStudent());
                    ps.setInt(2, pClassroom.getIdQualification());
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
        } else {
            result = 0;
            throw new RuntimeException("Grado existente");
        }
        return result;
    }

    public static int modificar(Classroom pClassroom) throws Exception {

        int result;
        String sql;
        boolean existsC = ExistsClassroom(pClassroom);

        if (existsC == false) {
            try ( Connection conn = DBContext.obtenerConexion();) {
                sql = "UPDATE  Classroom SET IdStudent=?, IdQualification=?, NumberOfStudent=?, Section=? WHERE IdClassroom=?";
                try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pClassroom.getIdStudent());
                    ps.setInt(2, pClassroom.getIdQualification());
                    ps.setInt(3, pClassroom.getNumberOfStudent());
                    ps.setString(4, pClassroom.getSection());
                    ps.setInt(5,pClassroom.getIdClassroom());
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
            throw new RuntimeException("Modificacion exitosa");
        }
        return result;
    }

    public static int Eliminar(Classroom pClassroom) throws Exception {
        int result;
        String sql;
        try ( Connection conn = DBContext.obtenerConexion();) {
            sql = "DELETE FROM Classroom WHERE IdClassroom=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql);) {
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

    static int AsignarDatosResultSet(Classroom pClassroom, ResultSet pResulset, int pIndex) throws Exception {
        pIndex++;
        pClassroom.setIdClassroom(pResulset.getInt(pIndex));
        pIndex++;
        pClassroom.setIdStudent(pResulset.getInt(pIndex));
        pIndex++;
        pClassroom.setIdQualification(pResulset.getInt(pIndex));
        pIndex++;
        pClassroom.setNumberOfStudent(pResulset.getInt(pIndex));
        pIndex++;
        pClassroom.setSection(pResulset.getString(pIndex));
        return pIndex;
    }

    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Classroom> pClassrooms) throws Exception {
        try ( ResultSet resultset = DBContext.obtenerResultSet(pPS);) {
            while (resultset.next()) {
                Classroom classroom = new Classroom();
                AsignarDatosResultSet(classroom, resultset, 0);
                pClassrooms.add(classroom);

            }
            resultset.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    //posible futuro problema obtener datos ArrayList<Student>
    private static void ObtenerDatosIncluirStudent(PreparedStatement pPS, ArrayList<Classroom> pClassroom) throws Exception {
        try ( ResultSet resultset = DBContext.obtenerResultSet(pPS);) {
            HashMap<Integer, ArrayList<Student>> StudentMap = new HashMap();
            while (resultset.next()) {
                Classroom classroom = new Classroom();
                int Index = AsignarDatosResultSet(classroom, resultset, 0);
                if (StudentMap.containsKey(classroom.getIdStudent()) == false) {
                    ArrayList<Student> students = new ArrayList();
                    //Metodo en revision, favor no utulizar, hasta que el autor lo descomente
                    //StudentDAL.AsignarDatosResultSet(pStudent, resultset, Index)
                    StudentMap.put(classroom.getIdClassroom(), students);
                    //Posible problema futuro, relacion de uno a muchos
                    //Revisar tambien base de datos, averiguar si los campos de la bd coinciden realmente con las clases creadas en este proyecto
                    classroom.setStudents(students);
                } else {
                    classroom.setStudents(StudentMap.get(classroom.getStudents()));
                }
                pClassroom.add(classroom);
            }
            resultset.close();

        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Classroom ObtenerPorId(Classroom pClassroom) throws Exception {
        Classroom classroom = new Classroom();
        ArrayList<Classroom> classrooms = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(classroom);
            sql += " WHERE c.IdClassroom=?";
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql)) {
                ps.setInt(1, pClassroom.getIdClassroom());
                ObtenerDatos(ps, classrooms);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (classrooms.size() > 0) {
            classroom = classrooms.get(0);
        }
        return classroom;
    }

    public static ArrayList<Classroom> ObtenerTodos() throws Exception {
        ArrayList<Classroom> classrooms;
        classrooms = new ArrayList<>();

        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = ObtenerSelect(new Classroom());
            sql += AgregarOrderBy(new Classroom());
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql)) {
                ObtenerDatos(ps, classrooms);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return classrooms;
    }

    static void QuerySelect(Classroom pClassroom, DBContext.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pClassroom.getIdClassroom() > 0) {
            pUtilQuery.AgregarWhereAnd(" c.IdClassroom=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pClassroom.getIdClassroom());
            }
        }

        if (pClassroom.getIdClassroom() > 0) {
            pUtilQuery.AgregarWhereAnd(" c.IdStudent ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pClassroom.getIdStudent());
            }
        }

        if (pClassroom.getSection() != null) {
            pUtilQuery.AgregarWhereAnd(" c.Section=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pClassroom.getSection());
            }

        }
    }

    public static ArrayList<Classroom> BurscarIncluirStudent(Classroom pClassroom) throws Exception {
        ArrayList<Classroom> classroom = new ArrayList();
        try ( Connection conn = DBContext.obtenerConexion();) {
            String sql = "SELECT ";
            sql += ObtenerCampos();
            sql += ", ";
            sql += StudentDAL.ObtenerCampos();
            sql += " FROM Classroom c";
            sql += " JOIN Student s on (c.IdStudent = s.IdStudent)";

            DBContext db = new DBContext();
            DBContext.UtilQuery utilQuery = db.new UtilQuery(sql, null, 0);
            QuerySelect(pClassroom, utilQuery);
            sql = utilQuery.getSQL();
            try ( PreparedStatement ps = DBContext.createPreparedStatement(conn, sql)) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pClassroom, utilQuery);
                ObtenerDatosIncluirStudent(ps, classroom);
                ps.close();

            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return classroom;
    }

}