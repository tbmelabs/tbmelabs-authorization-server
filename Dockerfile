FROM openjdk:8-jre
MAINTAINER TBME Labs <info@tbmelabs.ch>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/authorization-server/authorization-server.jar"]

# Maven dependencies
ADD target/lib           /usr/share/authorization-server/lib

# Java service
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/authorization-server/authorization-server.jar
