FROM openjdk:11-slim
MAINTAINER TBME Labs <info@tbmelabs.ch>

ENTRYPOINT ["/usr/bin/java", "-jar", "/home/authorizationserver/authorization-server.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /home/authorizationserver/authorization-server.jar

RUN useradd -ms /bin/bash authorizationserver
RUN chown authorizationserver /home/authorizationserver/authorization-server.jar

USER authorizationserver
WORKDIR /home/authorizationserver
