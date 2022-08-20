/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syscaly.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author ronal
 */
public class DBContext {

    class TipoDB {

        static final int SQLSERVER = 1;
        static final int MYSQL = 2;
    }
    static int TIPODB = TipoDB.SQLSERVER;
    
    //Version SqlServer
    static String connectionUrl = "jdbc:sqlserver://SysCalyR.mssql.somee.com;"
            + "database=SysCalyR;"
            + "User=UserSysCaly_SQLLogin_1;"
            + "password=ic8gl8aft2;"
            + "loginTimeout=30;encrypt=false;trustServerCertificate=false";
    
    //Version MySQL
    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://SysCalyR.mssql.somee.com//SysCalyR";
    static String usuario = "UserSysCaly_SQLLogin_1";
    static String password = "ic8gl8aft2";

    Connection conn = null;

      public static Connection obtenerConexion() throws SQLException {
        
        //En Caso de error la version MySql, Comenta y descomenta la version SQLServer
 
        //Versio MySQL
        DriverManager.getDriver(driver);
        Connection connection = DriverManager.getConnection(connectionUrl); 
        
        //Version SQLServer
        //Connection connection = DriverManager.getConnection(connectionUrl); 
        //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        
        return connection; 
    }
    public static Statement createStatement(Connection pConn) throws SQLException {
        Statement statement = pConn.createStatement();
        return statement;
    }

 
    public static PreparedStatement createPreparedStatement(Connection pConn, String pSql) throws SQLException {
        PreparedStatement statement = pConn.prepareStatement(pSql);
        return statement;
    }

    public static ResultSet obtenerResultSet(Statement pStatement, String pSql) throws SQLException {
        ResultSet resultSet = pStatement.executeQuery(pSql);
        return resultSet;
    }


    public static ResultSet obtenerResultSet(PreparedStatement pPreparedStatement) throws SQLException {
        ResultSet resultSet = pPreparedStatement.executeQuery();
        return resultSet;
    }


    public static int ejecutarSQL(String pSql) throws SQLException {
        int result;
        try (Connection connection = obtenerConexion();) {
            Statement statement = connection.createStatement();
            result = statement.executeUpdate(pSql); 
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }


    class UtilQuery {

        private String SQL;
        private PreparedStatement statement; 
        private int numWhere; 

        public UtilQuery() {
        }

        public UtilQuery(String SQL, PreparedStatement statement, int numWhere) { 
            this.SQL = SQL;
            this.statement = statement;
            this.numWhere = numWhere;
        }

        public String getSQL() {
            return SQL;
        }

        public void setSQL(String SQL) {
            this.SQL = SQL;
        }

        public PreparedStatement getStatement() {
            return statement;
        }

        public void setStatement(PreparedStatement statement) {
            this.statement = statement;
        }

        public int getNumWhere() {
            return numWhere;
        }

        public void setNumWhere(int numWhere) {
            this.numWhere = numWhere;
        }

        public void AgregarWhereAnd(String pSql) {
            if (this.SQL != null) {
                if (this.numWhere == 0) { 
                    this.SQL += " WHERE ";
                } else {
                    this.SQL += " AND ";
                }
                this.SQL += pSql;
            }
            this.numWhere++;
        }
    }
}
