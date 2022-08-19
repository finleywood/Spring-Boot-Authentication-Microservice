package com.rubric.service.auth.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
    private String path;
}
