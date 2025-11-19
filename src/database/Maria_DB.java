package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Maria_DB extends Common_DB {

  public Maria_DB(String codepage) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    super(DbProducts.MARIADB, "jdbc:%s://%s:%s/%s", "org.mariadb.jdbc.Driver"); // https://github.com/mariadb-corporation/mariadb-connector-j
    this.props.put("characterEncoding", codepage);
  }

}
