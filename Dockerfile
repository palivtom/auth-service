FROM alpine:latest

# add open JDK 11 and DNS tools
RUN apk update && apk upgrade && apk --update add openjdk11 tini && rm -rf /tmp/* /var/cache/apk/*

#add user for the application
RUN addgroup spring && adduser -D -G spring spring

# Copy jar and entrypoint shell script into docker image
ARG JAR_FILE=target/*.jar
COPY --chown=spring:spring ${JAR_FILE} /app.jar
COPY --chown=spring:spring entrypoint.sh /entrypoint.sh

USER spring:spring
CMD ["/sbin/tini", "/entrypoint.sh"]