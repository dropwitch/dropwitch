# coding:utf-8
from fabric.api import env
from fabric.api import local
from fabric.colors import *
from fabric.decorators import task
from jinja2 import Environment, FileSystemLoader

import os
import re

from Class import *

FAB_DIR = os.path.abspath(os.path.dirname(__file__))
PROJECT_DIR = os.path.normpath(FAB_DIR + "/../")
SCHEMA_SQL_PATH = os.path.join(PROJECT_DIR, "src/main/resources/db/schema.sql")


@task
def schema():
    opts = "-d --compact -Q --ignore-table {0}.DATABASECHANGELOG --ignore-table {0}.DATABASECHANGELOGLOCK".format(env.mysql_database)
    local("mysqldump " + opts + " -u" + env.mysql_user + " -p " + env.mysql_database + " -r " + SCHEMA_SQL_PATH)
    sed_inplace(SCHEMA_SQL_PATH, 'AUTO_INCREMENT=\d* ', '')
    print green("schema.sql has been generated!")


@task
def entities():
    tables = _parse_sql()
    _print_tables(tables)
    _tables_to_files(tables)
    print green("Java files have been generated!")


def _parse_sql():
    # Compiled regex patterns
    create_table = re.compile('^CREATE TABLE `(.*?)`', re.IGNORECASE)
    column = re.compile('^\s*?`(?P<name>.*?)` (?P<type>\w.*?)[,\s]((?P<null>.*?NULL)[,\s])?(DEFAULT \'(?P<default>.*)\'[,\s])?((?P<auto_increment>AUTO_INCREMENT)[,\s])?', re.IGNORECASE)
    primary_key = re.compile('^\s*?(?P<type>PRIMARY KEY) .*?\((?P<columns>(`\w.*?`,?)+.*?)\)[,]?$', re.IGNORECASE)
    unique_key = re.compile('^\s*?(?P<type>UNIQUE KEY) .*?\((?P<columns>(`\w.*?`,?)+.*?)\)[,]?$', re.IGNORECASE)
    key = re.compile('^\s*?(?P<type>KEY) .*?\((?P<columns>(`\w.*?`,?)+.*?)\)[,]?$', re.IGNORECASE)
    column_of_key = re.compile('`(\w.*?)`', re.IGNORECASE)
    create_table_end = re.compile('^\)')

    tables = []

    with open(SCHEMA_SQL_PATH) as f:
        for line in f:
            # parse table name
            matches = create_table.match(line)
            if matches:
                table_name = matches.group(1)
                tables.append(Table(table_name))
                continue

            # parse column name
            matches = column.match(line)
            if matches:
                table = tables.pop()
                table.add_column(Column(matches.group('name'), matches.group('type'), matches.group('null'), matches.group('default'), matches.group('auto_increment')))
                tables.append(table)

            # parse primary key
            matches = primary_key.match(line)
            if matches:
                table = tables.pop()
                i = Index(matches.group('type'))
                column_names = column_of_key.findall(matches.group('columns'))
                i.set_column_names(column_names)
                # find and add Column object
                for name in column_names:
                    i.add_column(table.find_column_by_name(name))
                table.add_index(i)
                tables.append(table)

            # parse unique keys
            matches = unique_key.match(line)
            if matches:
                table = tables.pop()
                i = Index(matches.group('type'))
                column_names = column_of_key.findall(matches.group('columns'))
                i.set_column_names(column_names)
                # find and add Column object
                for name in column_names:
                    i.add_column(table.find_column_by_name(name))
                table.add_index(i)
                tables.append(table)

            # parse keys
            matches = key.match(line)
            if matches:
                table = tables.pop()
                i = Index(matches.group('type'))
                column_names = column_of_key.findall(matches.group('columns'))
                i.set_column_names(column_names)
                # find and add Column object
                for name in column_names:
                    i.add_column(table.find_column_by_name(name))
                table.add_index(i)
                tables.append(table)

            matches = create_table_end.match(line)
            if matches:
                continue

    return tables


def _print_tables(tables):
    for table in tables:
        print "+", table.get_name()
        print "  + columns"
        for column in table.get_columns():
            print "    -",
            print column.get_name(),
            print column.get_type(),
            print column.get_null(),
            print column.get_default(),
            print column.get_auto_increment()

        print "  + indices"
        for index in table.get_indices():
            print "    *",
            print index.get_type(),
            print index.get_column_names()


def _tables_to_files(tables):
    jinja_env = Environment(loader=FileSystemLoader(os.path.join(FAB_DIR, 'templates')), trim_blocks=True, lstrip_blocks=True)
    entity_list = jinja_env.get_template("entity_list.j2")
    entity = jinja_env.get_template("entity.j2")
    master_entity = jinja_env.get_template("master_entity.j2")
    entity_dao = jinja_env.get_template("entity_dao_base.j2")
    master_entity_dao = jinja_env.get_template("master_entity_dao_base.j2")

    # Generate EntityList.java
    entity_list_path = os.path.join(PROJECT_DIR, env.entity_dir, 'EntityList.java')
    entity_list.stream(
        env=env,
        tables=tables
    ).dump(entity_list_path)

    # Generate Entity.java
    for table in tables:
        entity_path = os.path.join(PROJECT_DIR, env.entity_dir, table.get_class_name() + '.java')
        if re.search('^master_', table.get_name()):
            master_entity.stream(
                env=env,
                table=table
            ).dump(entity_path)
        else:
            entity.stream(
                env=env,
                table=table
            ).dump(entity_path)

    # Generate EntityDao.java
    for table in tables:
        entity_dao_path = os.path.join(PROJECT_DIR, env.entity_dao_base_dir, table.get_class_name() + 'BaseDao.java')
        if re.search('^master_', table.get_name()):
            master_entity_dao.stream(
                env=env,
                table=table
            ).dump(entity_dao_path)
        else:
            entity_dao.stream(
                env=env,
                table=table
            ).dump(entity_dao_path)
