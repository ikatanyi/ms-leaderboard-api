package com.castille.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Used to map <a href="https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/reactive/function/client/ClientResponse.html">ClientResponse</a> from WebFlux
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ExceptionResponseDTO extends Exception {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String description;
    private String type;

    public ExceptionResponseDTO(String message) {
         throw APIException.badRequest(message, null);
    }

    /**
     * Status has to be converted into {@link HttpStatus}
     */
    public void setStatus(String status) {
        this.status = HttpStatus.valueOf(Integer.parseInt(status));
    }

}
