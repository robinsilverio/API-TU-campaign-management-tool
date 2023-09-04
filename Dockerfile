FROM eclipse-temurin:17.0.1_12-jre-alpine
MAINTAINER technischeunie.com
ARG JAR_FILE=target/TU_campaign_management_tool_API-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /TU_campaign_management_tool_API-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/TU_campaign_management_tool_API-0.0.1.jar"]
LABEL com.jfrog.artifactory.retention.maxCount="10"