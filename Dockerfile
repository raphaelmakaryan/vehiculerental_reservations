FROM openjdk:26-ea-slim-trixie
WORKDIR /app
COPY ./target/vehicleRentalReservations-0.0.1-SNAPSHOT.jar /app/vehicleRentalReservations-0.0.1-SNAPSHOT.jar
EXPOSE 8083
CMD ["java", "-jar", "/app/vehicleRentalReservations-0.0.1-SNAPSHOT.jar"]