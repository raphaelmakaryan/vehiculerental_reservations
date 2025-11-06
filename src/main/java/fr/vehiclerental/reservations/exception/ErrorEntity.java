package fr.vehiclerental.reservations.exception;

import java.time.LocalDateTime;

public class ErrorEntity {
    private boolean success;
    private LocalDateTime localDateTime;
    private String message;
    private int httpStatus;

    public ErrorEntity(boolean success, LocalDateTime localDateTime, String message, int httpStatus) {
        this.success = success;
        this.localDateTime = localDateTime;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}