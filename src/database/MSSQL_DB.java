package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class MSSQL_DB extends Common_DB {

  public MSSQL_DB() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    super(DbProducts.MSSQL, "jdbc:%s://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true;", "com.microsoft.sqlserver.jdbc.SQLServerDriver"); // mssql-jdbc-13.2.1.jre8.jar
  }

}
