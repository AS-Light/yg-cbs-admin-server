FROM java:8
VOLUME /tmp
ADD yg-cbs-admin-server-3.0.0-RELEASE.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
