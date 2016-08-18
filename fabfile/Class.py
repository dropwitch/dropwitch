from util import *

import re


class Table:
    def __init__(self, name):
        self.name = name
        self.class_name = pascal_case(name)
        self.columns = []
        self.no_default_columns = []

    def get_name(self):
        return self.name

    def get_class_name(self):
        return self.class_name

    def get_columns(self):
        return self.columns

    def add_column(self, column):
        self.columns.append(column)
        if column.default == "" and not column.auto_increment:
            self.no_default_columns.append(column)


class Column:
    def __init__(self, _name, _type, _null=None, _default=None, _auto_increment=None):
        self.name = _name
        self.field_name = camel_case(_name)
        self.pascal_name = pascal_case(_name)
        self.type = _type
        self.field_type = _convert_to_java_type(_type)
        self.field_size = _filter_size(self.field_type, _type)

        self.null = ""
        if _null:
            self.null = _null

        self.default = ""
        if _default:
            self.default = _default

        self.auto_increment = ""
        if _auto_increment:
            self.auto_increment = _auto_increment

    def get_name(self):
        return self.name

    def get_type(self):
        return self.type

    def get_null(self):
        return self.null

    def get_default(self):
        return self.default

    def get_auto_increment(self):
        return self.auto_increment


def _convert_to_java_type(_type):
    _boolean = re.compile('^tinyint', re.IGNORECASE)
    _integer = re.compile('^int', re.IGNORECASE)
    _long = re.compile('^bigint', re.IGNORECASE)
    _string = re.compile('^varchar|^text', re.IGNORECASE)
    _datetime = re.compile('^datetime')

    if _boolean.match(_type):
        return "boolean"

    if _integer.match(_type):
        return "Integer"

    if _long.match(_type):
        return "Long"

    if _string.match(_type):
        return "String"

    if _datetime.match(_type):
        return "DateTime"


def _filter_size(_field_type, _type):
    if _field_type == "String":
        match = re.search('\((\d*?)\)', _type)
        if match:
            return match.group(1)

    return None
