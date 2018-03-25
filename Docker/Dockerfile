FROM dekstroza/openjdk9-alpine as packager

RUN /opt/jdk-9/bin/jlink \
    --module-path /opt/jdk-9/jmods \
    --verbose \
    --add-modules java.base,java.logging,java.xml,jdk.unsupported \
    --compress 2 \
    --no-header-files \
    --output /opt/jdk-9-minimal

FROM alpine:3.6
COPY --from=packager /opt/jdk-9-minimal /opt/jdk-9-minimal
#COPY github-twitter-search-1.0.1-SNAPSHOT-fat.jar /opt/
ADD build/libs/webflux-0.0.1-SNAPSHOT.jar /opt/app.jar


ENV JAVA_HOME=/opt/jdk-9-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app.jar"]
EXPOSE 8080
