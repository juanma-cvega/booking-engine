FROM java:8
VOLUME /tmp
ADD controller/target/booking-service-controller-0.0.1-SNAPSHOT.jar bookingservice.jar
EXPOSE 8080
RUN sh -c 'touch /bookingservice.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /bookingservice.jar" ]