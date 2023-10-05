FROM eclipse-temurin:17.0.1_12-jre-alpine
MAINTAINER technischeunie.com
ARG JAR_FILE=target/TU_campaign_management_tool_API-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /TU_campaign_management_tool_API-0.0.1.jar
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
LABEL com.jfrog.artifactory.retention.maxCount="10"