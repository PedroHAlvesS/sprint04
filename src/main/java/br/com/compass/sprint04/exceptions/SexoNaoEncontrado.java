package br.com.compass.sprint04.exceptions;

public class SexoNaoEncontrado extends RuntimeException{
    public SexoNaoEncontrado(String message) {
        super(message);
    }

    public SexoNaoEncontrado() {
    }
}
