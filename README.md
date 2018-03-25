Simple WebFlux app without annotations.



./gradlew build
docker build -t io.huta/webflux .
docker run -p 8080:8080 -p 9010:9010 io.huta/webflux
