# Cache Service

A simple cache service to cache HTTP requests

### Features ###

* REST cache endpoints using Jersey
* Integration testing using [REST Assured](http://rest-assured.io/)
* Builds a runnable Docker container

### Maven Commands ###

Compile and run all unit tests:

```
mvn install
```

Run the application:

```
mvn spring-boot:run
```

Run integration tests with REST Assured during build:

```
mvn integration-test
```

Run integration tests and fail if any tests fail:

```
mvn verify
```

### Running in Docker ###

Note: You will need to have VirtualBox (if you're not on Linux) and Docker to run this with Docker.

Build a Docker image:

```
mvn package docker:build
```

Run the Docker container

```
docker run -p 8080:8080 -t jgoetz/cacheservice
```




