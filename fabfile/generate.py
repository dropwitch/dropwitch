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


@task
def entities():
    tables = _parse_sql()
    _print_tables(tables)
    _tables_to_files(tables)


def _parse_sql():
    # Regex patterns
    create_table = re.compile('^CREATE TABLE `(.*?)`', re.IGNORECASE)
    column = re.compile('^\s*?`(?P<name>.*?)` (?P<type>\w.*?)[,\s]((?P<null>.*?NULL)[,\s])?(DEFAULT (?P<default>.*)[,\s])?((?P<auto_increment>AUTO_INCREMENT)[,\s])?', re.IGNORECASE)
    create_table_end = re.compile('^\)')

    tables = []

    with open(SCHEMA_SQL_PATH) as f:
        for line in f:
            matches = create_table.match(line)
            if matches:
                table_name = matches.group(1)
                tables.append(Table(table_name))
                continue

            matches = column.match(line)
            if matches:
                table = tables.pop()
                table.add_column(Column(matches.group('name'), matches.group('type'), matches.group('null'), matches.group('default'), matches.group('auto_increment')))
                tables.append(table)

            matches = create_table_end.match(line)
            if matches:
                continue

    return tables


def _print_tables(tables):
    for table in tables:
        print table.get_name()
        for column in table.get_columns():
            print "  ",
            print column.get_name(),
            print column.get_type(),
            print column.get_null(),
            print column.get_default(),
            print column.get_auto_increment()


def _tables_to_files(tables):
    jinja_env = Environment(loader=FileSystemLoader(os.path.join(FAB_DIR, 'templates')), trim_blocks=True, lstrip_blocks=True)
    entity = jinja_env.get_template("entity.j2")
    master_entity = jinja_env.get_template("master_entity.j2")

    for table in tables:
        entity_path = os.path.join(PROJECT_DIR, env.entity_dir, table.class_name + '.java')
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
