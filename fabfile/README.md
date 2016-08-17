# Schema and Entity generator

This generator is powered by [Python Fabric](http://www.fabfile.org/)

## Dependencies

- mysql client (mysql command)
- fabric
- jinja2 (Python template module)


## Usage

Edit `env` in fabfile/\__init\__.py:

```
env.mysql_user = "your_mysql_user_name"
env.mysql_database = "your_database_name"

env.entity_dir = "src/main/java/path/to/entity"
env.entity_dao_dir = "src/main/java/path/to/entity/dao"
env.java_package_entity = "path.to.entity"
env.java_package_entity_dao = "path.to.entity.dao"
```

Create tables (migrate database):

```
cd /path/to/workspace/dropwitch
java -jar target/dropwitch-0.0.1-SNAPSHOT.jar db migrate config/local.yml
```

Run schema file generator:

```
cd /path/to/workspace/dropwitch
fab generate.schema
# after this, enter mysql password
```

Run entity file generator:

```
cd /path/to/workspace/dropwitch
fab generate.entities
```
