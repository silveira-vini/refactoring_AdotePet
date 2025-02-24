package br.com.alura.adopet.api.exception;

public class AbrigoNaoEncontradoException extends RuntimeException {
    public AbrigoNaoEncontradoException(String message) {
        super(message);
    }
}
