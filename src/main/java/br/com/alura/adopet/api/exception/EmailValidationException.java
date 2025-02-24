package br.com.alura.adopet.api.exception;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class EmailValidationException extends RuntimeException {
    public void validar(MethodArgumentNotValidException ex) {
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if (error.getField().equals("email")) {
                throw new ValidationException("Email mal formado. Favor informe um email válido.");
            }
        }
    }
}
