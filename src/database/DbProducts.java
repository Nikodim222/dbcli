package database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class DbProducts {

  private DbProducts() {}
  public static final String POSTGRESQL = "postgresql";
  public static final String MYSQL      = "mysql";
  public static final String MARIADB    = "mariadb";
  public static final String ORACLE     = "oracle";
  public static final String MSSQL      = "sqlserver";
  public static final String SQLITE     = "sqlite";
  public static final String EXCEL      = "JDBCExcel";

  /**
   * Перечисление всех констант текущего класса через запятую
   * @return Строка значений константы, которые разделены запятыми
   */
  public static String allValuesJoined() {
    try {
      Field[] fields = DbProducts.class.getDeclaredFields();
      List<String> values = new ArrayList<>();
      for (Field f : fields) {
        int mods = f.getModifiers();
        if (Modifier.isStatic(mods) && Modifier.isPublic(mods) && f.getType().equals(String.class)) {
          Object v = f.get(null);
          if (v != null) values.add((String) v);
        }
      }
      return String.join(", ", values);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

}
