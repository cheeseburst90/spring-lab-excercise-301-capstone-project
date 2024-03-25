# Start with a base image containing Java runtime
FROM java:openjdk-8

# Add Maintainer Info
LABEL maintainer="bharath.giri@me.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/restaurantsearchservice-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} restaurantsearchservice.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/restaurantsearchservice.jar"]