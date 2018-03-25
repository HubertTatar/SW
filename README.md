Simple WebFlux app without annotations.

Opened jmx port for JVisualVM

Build and Run
    
    ./gradlew build
    docker build -t io.huta/webflux .
    docker run -p 8080:8080 -p 9010:9010 io.huta/webflux
    
GatlingTest needs to be copied to Gatling dir: 'user-files/simulations/'
        
