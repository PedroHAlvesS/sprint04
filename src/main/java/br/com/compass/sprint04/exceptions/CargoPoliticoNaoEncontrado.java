package br.com.compass.sprint04.exceptions;

public class CargoPoliticoNaoEncontrado extends RuntimeException{
    public CargoPoliticoNaoEncontrado(String message) {
        super(message);
    }

    public CargoPoliticoNaoEncontrado() {
    }
}
