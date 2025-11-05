package fr.vehiclerental.reservations.exception;

import java.time.LocalDateTime;

public class ErrorEntity {
    private LocalDateTime localDateTime;
    private String message;
    private int httpStatus;

    // Constructeur avec champs
    public ErrorEntity(LocalDateTime localDateTime, String message, int httpStatus) {
        this.localDateTime = localDateTime;
        this.message = message;
        this.httpStatus = httpStatus;
    }


    // Getters et Setters
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}