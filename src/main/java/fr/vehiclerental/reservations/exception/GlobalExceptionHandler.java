package fr.vehiclerental.reservations.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationNotFindException.class)
    public ResponseEntity<ErrorEntity> reservationNotFoundHandler(ReservationNotFindException exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorEntity> badRequestHandler(BadRequestException exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorEntity> runtimeExceptionHandler(RuntimeException exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(ClientNotFindException.class)
    public ResponseEntity<ErrorEntity> clientNotFindException(ClientNotFindException exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(ClientNotLegalAgeOrLicense.class)
    public ResponseEntity<ErrorEntity> clientNotAgeLegalOrLicense(ClientNotLegalAgeOrLicense exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(ClientAlreadyReservation.class)
    public ResponseEntity<ErrorEntity> clientAlreadyReservation(ClientAlreadyReservation exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(VehiculeNotFInd.class)
    public ResponseEntity<ErrorEntity> vehiculeNotFind(VehiculeNotFInd exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(ClientAgeHorsepower.class)
    public ResponseEntity<ErrorEntity> clientAgeHorsepower(ClientAgeHorsepower exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(VehiculeAlreadyReservation.class)
    public ResponseEntity<ErrorEntity> vehiculeAlreadyReservation(VehiculeAlreadyReservation exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(VehicleTypeKnowName.class)
    public ResponseEntity<ErrorEntity> vehiculeNotType(VehicleTypeKnowName exception) {
        ErrorEntity error = new ErrorEntity(false, LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }
}
