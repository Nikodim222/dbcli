@Echo Off
chcp 1251>nul
cd /D "%~dp0"

echo Примеры SQL-запросов:
echo select table_name from information_schema.tables where table_type = 'BASE TABLE' and table_schema = 'public' order by table_name asc;
echo select session_user;
echo select current_date;
echo select pid, usename, datname, now() - query_start as duration, query from pg_stat_activity where state = 'active' order by duration desc;

:l_start
set dbtype=
set db=
set query=
echo Выберите тип СУБД:
echo 1. postgresql
echo 2. mysql
echo 3. mariadb
echo 4. oracle
echo 5. sqlserver
echo 6. sqlite
echo 7. JDBCExcel
set /p dbtype="Номер типа СУБД: "

if "%dbtype%"=="1" set "db=postgresql"
if "%dbtype%"=="2" set "db=mysql"
if "%dbtype%"=="3" set "db=mariadb"
if "%dbtype%"=="4" set "db=oracle"
if "%dbtype%"=="5" set "db=sqlserver"
if "%dbtype%"=="6" set "db=sqlite"
if "%dbtype%"=="7" set "db=JDBCExcel"

if defined db (
  echo Вы выбрали: %db%
) else (
  echo Неверный номер.
  goto l_start
)

set /p query="SQL (без точки с запятой)> "

if not defined query (
  echo SQL-запрос не задан.
  goto l_start
)

echo.
call dbcli.bat -d "%db%" -q "%query%"
echo.
goto l_start
