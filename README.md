# Dropwitch

Dropwitch is a template project to construct a RESTful API server.
Dropwitch is powered by [Dropwizard](http://www.dropwizard.io/)


## Features

- JSON and MessagePack available on request and response
- Schema and Entity generator written by [Python Fabric](http://www.fabfile.org/) (see [Schema and Entity generator](https://github.com/dropwitch/dropwitch-code-generator/blob/master/README.md))


## Environments

- JDK 8
- Maven 3.3
- MySQL 5.6


## Quick Start

Build fat-jar:

```
mvn package
```

Migrate Database:

```
java -jar target/dropwitch-0.0.1-SNAPSHOT.jar db status config/local.yml
java -jar target/dropwitch-0.0.1-SNAPSHOT.jar db migrate config/local.yml
```

Execute jar:

```
java -jar target/dropwitch-0.0.1-SNAPSHOT.jar server config/local.yml
```

Then, open in browser: [localhost:8080](http://localhost:8080/)


## Sample API

| Method | Path           |                          |
|--------|----------------|--------------------------|
| GET    | /master        | Get master data          |
| DELETE | /master/cache  | Delete master data cache |
| POST   | /user/register | Create user              |
| GET    | /user          | Get user                 |
