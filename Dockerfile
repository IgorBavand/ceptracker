FROM openjdk:17-jdk-slim

WORKDIR /app

# Baixar o dd-java-agent.jar
COPY dd-java-agent.jar /app/dd-java-agent.jar

COPY target/ceptracker-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081

ENV DD_ENV=dev
ENV DD_SERVICE=ceptracker-api
ENV DD_VERSION=1.0.0
ENV DD_AGENT_HOST=datadog-agent
ENV DD_TRACE_AGENT_PORT=8126
ENV DD_LOGS_INJECTION=true
ENV com_datadoghq_tags_env=dev
ENV com_datadoghq_tags_service=ceptracker-api
ENV com_datadoghq_tags_version=1.0.0
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_container:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=igor
ENV SPRING_DATASOURCE_PASSWORD=12345678
ENV JWT_SECRET=mysecretkeyaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
ENV JWT_EXPIRATION=3600000

CMD ["java", "-javaagent:/app/dd-java-agent.jar", "-jar", "/app/app.jar"]
