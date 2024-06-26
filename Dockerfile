FROM openjdk:17
# copy the source tree and the pom.xml to our new container
ADD /target/CryptoArchiveBE-1.0-SNAPSHOT.jar backend.jar
# set the startup command to execute the jar
EXPOSE 8080

CMD ["java", "-jar", "backend.jar"]