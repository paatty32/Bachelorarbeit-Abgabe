FROM eclipse-temurin:18-jre
ARG WAR_FILE=./web-club-portal-ui/target/web-club-portal-ui-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} application.war
ENTRYPOINT ["java", "-jar", "application.war"]
