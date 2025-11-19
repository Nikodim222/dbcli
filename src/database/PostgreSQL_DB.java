package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class PostgreSQL_DB extends Common_DB {

  public PostgreSQL_DB() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    super(DbProducts.POSTGRESQL, "jdbc:%s://%s:%s/%s", null); // postgresql-42.7.8.jar
  }

}
