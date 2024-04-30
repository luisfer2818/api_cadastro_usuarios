FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/usuario-0.0.1-SNAPSHOT.jar usuario.jar
ENTRYPOINT ["java","-jar","/usuario.jar"]
