package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class SQLite_DB extends Common_DB {

  public SQLite_DB() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    super(DbProducts.SQLITE, "jdbc:%s:%s", "org.sqlite.JDBC"); // https://github.com/xerial/sqlite-jdbc
  }

}
