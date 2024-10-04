package br.com.matteusmoreno.uol_backend_challenge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(GroupMaxPlayersReachedException.class)
    public ResponseEntity<String> handleGroupMaxPlayersReachedException(GroupMaxPlayersReachedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
