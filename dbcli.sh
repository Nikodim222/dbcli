#!/bin/sh

# полный путь до скрипта
export ABSOLUTE_FILENAME=`readlink -e "$0"`
# каталог, в котором лежит скрипт
export DIRECTORY=`dirname "$ABSOLUTE_FILENAME"`

echo "************"
echo "Просмотр таблиц БД в консоли"
echo "************"
echo

cd $DIRECTORY
java -cp "./dbcli.jar:ext_libs/*:ext_libs/ini4j-0.5.4/*" main.startIt "$@"
