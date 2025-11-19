package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Oracle_DB extends Common_DB {

  public Oracle_DB() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    super(DbProducts.ORACLE, "jdbc:%s:thin:@%s:%s:%s", null); // ojdbc6.jar
  }

}
