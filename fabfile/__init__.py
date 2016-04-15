#coding:utf-8
from fabric.api import env

import generate

env.mysql_user = "root"
env.mysql_database = "dropwitch"

env.entity_dir = "src/main/java/com/github/dropwitch/entity"
env.entity_dao_dir = "src/main/java/com/github/dropwitch/entity/dao"
env.java_package_entity = "com.github.dropwitch.entity"
env.java_package_entity_dao = "com.github.dropwitch.entity.dao"
