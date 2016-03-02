# dropwitch-web

JSON API server using [Dropwizard](http://www.dropwizard.io/)

MessagePack (msgpack) request/response are also available.
You can check msgpack request using [sample request code](https://github.com/shun-tak/msgpack-io).

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
java -jar target/dropwitch-web-0.0.1-SNAPSHOT.jar db status config/local.yml
java -jar target/dropwitch-web-0.0.1-SNAPSHOT.jar db migrate config/local.yml
```

Execute jar:

```
java -jar target/dropwitch-web-0.0.1-SNAPSHOT.jar server config/local.yml
```

Then, open in browser: [localhost:8080](http://localhost:8080/)

## Sample API

| Method | Path           |                          |
|--------|----------------|--------------------------|
| GET    | /master        | Get master data          |
| DELETE | /master/cache  | Delete master data cache |
| POST   | /user/register | Create user              |
| GET    | /user          | Get user                 |
