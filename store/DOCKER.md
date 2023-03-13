https://hub.docker.com/_/postgres


```bash
$ docker run \
--name geocoder-postgres \
-e POSTGRES_PASSWORD=geocoderpass \
-d postgres:15
```

```bash
$ docker {run|stop}
$ docker exec -it {container-name} /bin/bash
$ docker {container|volume|image}  {ls|rm}
$ docker compose {up|down|start|stop}
$ docker compose -f docker-compose.yaml up -d
```

```bash
$  ./gradlew bootJar
$ docker build -f Dockerfile | docker build .
$ docker build  --build-arg JAR_FILE="./build/libs/geocoder-0.0.1-SNAPSHOT.jar" -t  geocoder:0.0.1 .
$ docker compose up -d
```


