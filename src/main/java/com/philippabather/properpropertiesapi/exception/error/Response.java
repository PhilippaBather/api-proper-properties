package com.philippabather.properpropertiesapi.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response - clase define la respuesta de un error
 *
 * @author Philippa Bather
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String code;
    private String type;
    private String message;
}
