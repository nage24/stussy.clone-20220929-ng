package com.stussy.stussy.clone20220929ng.handler;

import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import com.stussy.stussy.clone20220929ng.exception.CustomValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationErrorException(CustomValidationException e) {
        return ResponseEntity
                .badRequest()
                .body(new CMRespDto(-1, e.getMessage(), null));

        // return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Validation Error!", null));
    }

}
