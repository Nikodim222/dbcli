#!/bin/sh

cd "$(dirname "$0")" || exit 1

echo "Примеры SQL-запросов:"
echo "select table_name from information_schema.tables where table_type = 'BASE TABLE' and table_schema = 'public' order by table_name asc;"
echo "select session_user;"
echo "select current_date;"
echo "select pid, usename, datname, now() - query_start as duration, query from pg_stat_activity where state = 'active' order by duration desc;"
echo

while :; do
  dbtype=""
  db=""
  query=""

  echo "Выберите тип СУБД:"
  echo "1. postgresql"
  echo "2. mysql"
  echo "3. mariadb"
  echo "4. oracle"
  echo "5. sqlserver"
  echo "6. sqlite"
  echo "7. JDBCExcel"
  printf "Номер типа СУБД: "
  if ! IFS= read -r dbtype; then
    echo
    exit 1
  fi

  case "$dbtype" in
    1) db="postgresql" ;;
    2) db="mysql" ;;
    3) db="mariadb" ;;
    4) db="oracle" ;;
    5) db="sqlserver" ;;
    6) db="sqlite" ;;
    7) db="JDBCExcel" ;;
    *) db="" ;;
  esac

  if [ -n "$db" ]; then
    echo "Вы выбрали: $db"
  else
    echo "Неверный номер."
    echo
    continue
  fi

  printf "SQL (без точки с запятой)> "
  if ! IFS= read -r query; then
    echo
    exit 1
  fi

  if [ -z "$query" ]; then
    echo "SQL-запрос не задан."
    echo
    continue
  fi

  echo
  ./dbcli.sh -d "$db" -q "$query"
  echo
done
