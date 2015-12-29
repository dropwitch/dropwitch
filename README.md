# dropwitch-web

# Environments

- maven 3.3.3
- mysql 5.6.5 (minimum)

# Quick Start

Build jar:

```
mvn package
```

Migrate:

```
java -jar target/dropwitch-web-0.0.1-SNAPSHOT.jar db status config/local.yml
java -jar target/dropwitch-web-0.0.1-SNAPSHOT.jar db migrate config/local.yml
```

Execute jar:

```
java -jar target/dropwitch-web-0.0.1-SNAPSHOT.jar server config/local.yml
```

Then, open in browser: [localhost:8080](http://localhost:8080/)
