package com.clinica.odontologica.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp; // when the error occurred
    private Integer status; // http status code
    private String error; // Type of error, not found, duplicate, etc
    private String message; // Friendly description of the error
    private String path; // path where the error occurred
    private List<String> errorsValidacion; // only for validation errors
}
