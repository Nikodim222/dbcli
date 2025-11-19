package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class MySQL_DB extends Common_DB {

  public MySQL_DB(String codepage) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    super(DbProducts.MYSQL, "jdbc:%s://%s:%s/%s", null); // mysql-connector-java-5.1.34-bin.jar
    this.props.put("characterEncoding", codepage);
  }

}
