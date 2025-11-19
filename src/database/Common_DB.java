package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.ini4j.Ini; // https://ini4j.sourceforge.net/download.html

public class Common_DB {

  protected Properties props = new Properties();
  private String db_prod = null; // семейство СУБД
  private Connection objConn = null;
  private static final String HOST = "host";
  private static final String PORT = "port";
  private static final String DB_NAME = "dbname";
  private static final String LOGIN = "user";
  private static final String PASSWORD = "password";

  /**
   * Конструктор класса
   * @param section
   * @param url_format
   * @param controlString
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public Common_DB(final String section, final String url_format, final String controlString) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    if ((section != null) && (!section.isEmpty()) && (url_format != null) && (!url_format.isEmpty())) {
      File iniFile = new File(System.getProperty("user.dir"), "settings.ini");
      try (InputStreamReader reader = new InputStreamReader(new FileInputStream(iniFile), Charset.forName("CP1251"))) {
        Ini ini = new Ini();
        ini.load(reader);
        Ini.Section pg = ini.get(section);
        this.props.put(HOST, pg.get(HOST));
        this.props.put(PORT, pg.get(PORT));
        this.props.put(DB_NAME, pg.get(DB_NAME));
        this.props.put(LOGIN, pg.get(LOGIN));
        this.props.put(PASSWORD, pg.get(PASSWORD));
      }
      String host = this.props.getProperty(HOST);
      String port = this.props.getProperty(PORT);
      String db = this.props.getProperty(DB_NAME);
      String url = String.format(url_format, section, host, port, db);
      this.db_prod = section;
      this.objConn = this.establishConnection(((controlString == null) || (controlString.isEmpty())) ? "java.sql.Driver" : controlString, url);
    } else {
      throw new IllegalArgumentException("The parameters must be non-null and non-empty");
    }
  }

  /**
   * Установка соединения с базой данных
   * @param controlString
   * @param url
   * @return Соединение к БД
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  private Connection establishConnection(final String controlString, final String url) throws ClassNotFoundException, SQLException {
    Class.forName(controlString);
    Connection con = DriverManager.getConnection(url, this.props);
    return con;
  }

  /**
   * Закрытие соединения с базой данных
   * @throws SQLException
   */
  public void disconnectFromDB() throws SQLException {
    if (this.isConnected()) {
      this.objConn.close();
    }
  }

  /**
   * Признак установки соединения с базой данных
   * @return Истина - соединение с БД установлено; Ложь - в противном случае
   */
  public boolean isConnected() {
    boolean result = false;
    if (this.db_prod.equals(DbProducts.EXCEL)) {
      result = true;
    } else {
      try {
        if ((this.objConn != null) && (!this.objConn.isClosed()) && (this.objConn.isValid(2))) {
          result = true;
        }
      }
      catch (SQLException e) {}
    }
    return result;
  }

  /**
   * Выполнение SQL-запроса SELECT
   * @param sql
   * @return Список строк результата запроса; каждая строка - массив столбцов
   * @throws SQLException
   */
  public List<Object[]> executeQuery(final String sql) throws SQLException {
    final List<Object[]> result = new ArrayList<>();
    if (this.isConnected()) {
      Statement stmt = null;
      ResultSet rs = null;
      try {
        stmt = this.objConn.createStatement();
        rs = stmt.executeQuery(sql);
        ResultSetMetaData md = rs.getMetaData();
        int cols = md.getColumnCount();
        while (rs.next()) {
          Object[] row = new Object[cols];
          for (int i = 0; i < cols; i++) {
            row[i] = rs.getObject(i + 1);
          }
          result.add(row);
        }
      }
      finally {
        if (rs != null) {
          try {
            if (!this.db_prod.equals(DbProducts.EXCEL)) {
              rs.close();
            }
          }
          catch (SQLException e) {}
        }
        if (stmt != null) {
          try {
            if (!this.db_prod.equals(DbProducts.EXCEL)) {
              stmt.close();
            }
          }
          catch (SQLException e) {}
        }
      }
    }
    return result;
  }

  /**
   * Выполнение DML-операции (INSERT, UPDATE, DELETE)
   * @param sql
   * @return Количество затронутых строк
   * @throws SQLException
   */
  public int executeDML(final String sql) throws SQLException {
    int result = 0;
    if (this.isConnected()) {
      Statement stmt = null;
      try {
        stmt = this.objConn.createStatement();
        result = stmt.executeUpdate(sql);
      }
      finally {
        if (stmt != null) {
          try {
            if (!this.db_prod.equals(DbProducts.EXCEL)) {
              stmt.close();
            }
          }
          catch (SQLException e) {}
        }
      }
    }
    return result;
  }

  /**
   * Печать таблицы в консоль
   * @param sql
   * @throws SQLException
   */
  public void printTable(final String sql) throws SQLException {
    if (this.isConnected()) {
      List<Object[]> rows = this.executeQuery(sql);
      for (Object[] row : rows) { // печать таблицы: каждое значение разделено табуляцией
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
          if (i > 0) sb.append('\t');
          sb.append(row[i] == null ? "NULL" : row[i].toString());
        }
        System.out.println(sb.toString());
      }
      System.out.println("Ok.");
    } else {
      System.out.println("Отсутствует подключение к базе данных.");
    }
  }

}
