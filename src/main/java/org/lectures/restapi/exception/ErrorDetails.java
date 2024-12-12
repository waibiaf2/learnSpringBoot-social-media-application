package org.lectures.restapi.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String details) {
}
