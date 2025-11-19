package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Excel_DB extends Common_DB {

  public Excel_DB() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    super(DbProducts.EXCEL, "jdbc:%s://server=%s;database=%s", "JDBCExcel.Driver"); // https://github.com/rondunn/JDBCExcel
  }

}
