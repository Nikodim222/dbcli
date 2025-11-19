/**
 * Просмотр таблиц БД в консоли
 * *************************
 * Данная программа распечатывает содержимое таблицы из базы
 * данных в консоль. Программа использует файл "settings.ini",
 * в котором хранятся параметры подключения к базе данных.
 * Примеры запуска программы:
 * dbcli.bat --dbtype "sqlserver" --sql "select @@version as v"
 * dbcli.bat --dbtype "mariadb" --sql "select version() as v"
 * dbcli.bat --dbtype "JDBCExcel" --sql "select TrackId, Name from Track limit 15"
 * 
 * @author Ефремов А. В., 18.11.2025
 */

package main;

import com.beust.jcommander.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import database.DbProducts;
import database.PostgreSQL_DB;
import database.MSSQL_DB;
import database.SQLite_DB;
import database.Oracle_DB;
import database.MySQL_DB;
import database.Maria_DB;
import database.Excel_DB;

public class startIt {

  private static final String DEFAULT_CODEPAGE = "utf8";

  @Parameter(names = "--help", help = true)
  private boolean help = false;

  @Parameter(names = {"-d", "--dbtype"}, description = "регистр-зависимое название семейства СУБД (postgresql, sqlserver, sqlite и т.д.)")
  private String pDBType;

  @Parameter(names = {"-c", "--codepage"}, description = "кодировка на сервере СУБД (utf8, cp1251 и т.д.)")
  private String pCodepage;

  @Parameter(names = {"-q", "--sql"}, description = "SQL-запрос (без точки с запятой)")
  private String pSQL;

  public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
    startIt main = new startIt();
    JCommander.newBuilder().addObject(main).build().parse(args);
    if (main.help) {
      @SuppressWarnings("deprecation")
      JCommander jCommander = new JCommander(main, args);
      jCommander.usage();
      final String DB_PRODS = DbProducts.allValuesJoined();
      System.out.println("Поддерживаемые семейства СУБД: " + (((DB_PRODS != null) && (!DB_PRODS.isEmpty())) ? DB_PRODS : "отсутствуют") + ".");
    } else {
      if ((main.pSQL != null) && (!main.pSQL.isEmpty())) {
        final String codepage = ((main.pCodepage != null) && (!main.pCodepage.isEmpty())) ? main.pCodepage : DEFAULT_CODEPAGE;
        switch(main.pDBType) {
        case DbProducts.POSTGRESQL:
          PostgreSQL_DB objPostgre = new PostgreSQL_DB();
          objPostgre.printTable(main.pSQL); // select * from personnel where is_active = true
          objPostgre.disconnectFromDB();
          break;
        case DbProducts.MSSQL:
          MSSQL_DB objMSSQL = new MSSQL_DB();
          objMSSQL.printTable(main.pSQL); // select @@version as v
          objMSSQL.disconnectFromDB();
          break;
        case DbProducts.SQLITE:
          SQLite_DB objSQLite = new SQLite_DB();
          objSQLite.printTable(main.pSQL); // select u.user_id, u.first_name, u.last_name, substr(m.msg, 1, 10) as msg, m.date_create from telegram_users u inner join user_messages m using (user_id) order by m.user_id asc, m.date_create desc limit 100
          objSQLite.disconnectFromDB();
          break;
        case DbProducts.ORACLE:
          Oracle_DB objOracle = new Oracle_DB();
          objOracle.printTable(main.pSQL); // select banner as b from v$version
          objOracle.disconnectFromDB();
          break;
        case DbProducts.MYSQL:
          MySQL_DB objMySQL = new MySQL_DB(codepage);
          objMySQL.printTable(main.pSQL); // select version() as v
          objMySQL.disconnectFromDB();
          break;
        case DbProducts.MARIADB:
          Maria_DB objMaria = new Maria_DB(codepage);
          objMaria.printTable(main.pSQL); // select version() as v
          objMaria.disconnectFromDB();
          break;
        case DbProducts.EXCEL:
          Excel_DB objExcel = new Excel_DB();
          objExcel.printTable(main.pSQL); // select TrackId, Name from Track limit 15
          objExcel.disconnectFromDB();
          break;
        default:
          System.err.println("Данное семейство СУБД программе неизвестно.");
          break;
        }
      } else {
        System.err.println("SQL-запрос не задан.");
      }
    }
  }

}
